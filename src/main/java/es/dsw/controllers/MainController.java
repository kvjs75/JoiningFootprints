package es.dsw.controllers;

import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import es.dsw.config.SecurityConfiguration;
import es.dsw.datos.consultasCarteles;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.cartelDesaparicion;
import es.dsw.models.cartelAdopcion;
import es.dsw.models.carteles;
import es.dsw.models.controlErroresCartelDesaparicion;
import es.dsw.models.controlErroresUsuarios;
import es.dsw.models.solicitudImagen;
import es.dsw.models.usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"usuario","cartelDesaparicion"})

public class MainController {
	@GetMapping(value= {"/","/index"})
	public String index(Model modelo) {
		return "index";
	}
	
	@GetMapping(value= {"/login"})
	public String login(Model modelo) {
		return "views/autenticar";
	}
	
	@GetMapping(value= {"/registro"})
	public String registrarse(
			Model modelo,
			@RequestParam(name="numError", defaultValue="0" ) String numError
								) {
		if(modelo.getAttribute("usuario") == null) {
			usuario usuario = new usuario();
			modelo.addAttribute("usuario",usuario);
		}
		controlErroresUsuarios controlErroresUsuarios = new controlErroresUsuarios(numError);
		modelo.addAttribute("msgError", controlErroresUsuarios.getMsgError());
		
		return "views/registro";
	}
	
	//crear usuario
	@PostMapping(value= {"/crearUsuario"})
	public String CrearUsuario(
			Model modelo,
			HttpServletRequest pedido,
			SessionStatus estado,
			@RequestParam(name="nombreUsuario", defaultValue="" ) String nombreUsuario,
			@RequestParam(name="nick", defaultValue="" ) String nick,
			@RequestParam(name="contrasena", defaultValue="" ) String contrasena,
			@RequestParam(name="reContrasena", defaultValue="" ) String contrasenaRe,
			@RequestParam(name="correoElectronico", defaultValue="" ) String correo,
			@RequestParam(name="ReCorreoElectronico", defaultValue="" ) String correoRe,
			@RequestParam(name="fechaNacimiento", defaultValue="" ) String Fnacimiento,
			@RequestParam(name="zonaGeografica", defaultValue="" ) String zonaGeografica,
			CsrfToken csrfToken ) {
			
		consultasUsuarios consultasUsuarios = new consultasUsuarios();
		String csrf = csrfToken.getToken();
		usuario usuario = new usuario(nombreUsuario,nick,contrasena,correo,Fnacimiento,zonaGeografica);
		modelo.addAttribute("usuario",usuario);
		controlErroresUsuarios controlErroresUsuarios = new controlErroresUsuarios(usuario,contrasenaRe,correoRe);
			
		if (controlErroresUsuarios.isError() == true) {
			//fallo el registro de usuario
			return "redirect:/registro?errorcreado&numError="+controlErroresUsuarios.getNumError()+"&_csrf="+csrf;
		}else {
				
			//registrar usuario
			UserDetails nuevoUsuario = User.withDefaultPasswordEncoder()
					.username(nombreUsuario)
					.password(contrasena)
					.roles("usuario")
					.build();
			SecurityConfiguration.InMemory.createUser(nuevoUsuario);
			//autologin
			try {
				pedido.login(nombreUsuario, contrasena);
			} catch (ServletException e) {
				e.printStackTrace();
			}
				
			consultasUsuarios.crearUsuario(usuario);
			estado.setComplete();
			return "redirect:/index?_csrf="+csrf;
		}
	}
	
	
	
	
	@GetMapping(value= {"/publicar1_1"})
	public String publicar1_1(Model modelo,
			 				  @RequestParam(name="codError", defaultValue="" ) String codError) {
		if(modelo.getAttribute("cartelDesaparicion") == null) {
			cartelDesaparicion cartelDesaparicion = new cartelDesaparicion();
			modelo.addAttribute("cartelDesaparicion",cartelDesaparicion);
		}
		
		controlErroresCartelDesaparicion objControlErroresCartelDesaparicion = new controlErroresCartelDesaparicion(codError);
		
		modelo.addAttribute("msgError", objControlErroresCartelDesaparicion.getMsgError());
		
		return "views/publicar1_1";
	}
	
	
	@PostMapping(value= {"/paso1_1"})
	public String paso1_1(Model modelo, 
						 @RequestParam(name="nombreAnimal", defaultValue="" ) String nombreAnimal,
						 @RequestParam(name="raza", defaultValue="" ) String raza,
						 @RequestParam(name="especie", defaultValue="" ) String especie,
						 @RequestParam(name="sexo", defaultValue="" ) String sexo,
						 @RequestParam(name="fechaDesaparicion", defaultValue="" ) String fechaDesaparicion,
						 @RequestParam(name="descripcion", defaultValue="" ) String descripcion,
						 @RequestParam(name="lugarDesaparicion", defaultValue="" ) String lugarDesaparicion,
						 @RequestParam(name="telefono1", defaultValue="" ) String telefono1,
						 @RequestParam(name="telefono2", defaultValue="" ) String telefono2,
						 @RequestParam(name="emailContacto", defaultValue="" ) String emailContacto,
						 @RequestParam(name="recompensa", defaultValue="" ) String recompensa
			  			) {
		
		cartelDesaparicion objCartelDesaparicion = new cartelDesaparicion();
		
		
		//Condicion para poner las primeras letras del nombre en mayuscula
		if (nombreAnimal != null && !nombreAnimal.isEmpty()) {
            String[] palabras = nombreAnimal.split("\\s+");
            for (int i = 0; i < palabras.length; i++) {
                palabras[i] = palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1).toLowerCase();
            }
            nombreAnimal = String.join(" ", palabras);
        }
		
		
		objCartelDesaparicion.setNombreAnimal(nombreAnimal);
		objCartelDesaparicion.setRaza(raza);
		objCartelDesaparicion.setEspecie(especie);
		objCartelDesaparicion.setSexo(sexo);
		objCartelDesaparicion.setFechaDesaparicion(fechaDesaparicion);
		objCartelDesaparicion.setDescripcion(descripcion);
		objCartelDesaparicion.setLugarDesaparicion(lugarDesaparicion);
		objCartelDesaparicion.setTelefono1(telefono1);
		objCartelDesaparicion.setTelefono2(telefono2);
		objCartelDesaparicion.setCorreo(emailContacto);
		if(recompensa.equals("on")) {
			objCartelDesaparicion.setRecompensa("si");
		}else {
			objCartelDesaparicion.setRecompensa("no");
		}
		
		controlErroresCartelDesaparicion objControlErroresCartelDesaparicion = new controlErroresCartelDesaparicion(objCartelDesaparicion);
		
		modelo.addAttribute("cartelDesaparicion",objCartelDesaparicion);
		
		if(objControlErroresCartelDesaparicion.isError() == true) {
			return "redirect:/publicar1_1?codError="+objControlErroresCartelDesaparicion.getNumError();
		}else {
			
			return "redirect:/publicar1_2";
		}
	}
	
	
	@GetMapping(value= {"/publicar1_2"})
	public String publicar1_2(Model modelo) {
		
		if(modelo.getAttribute("cartelDesaparicion") == null) {
			return "redirect:/publicar1_1";
		}
		
		cartelDesaparicion sesionCartelDesaparicion = (cartelDesaparicion) modelo.getAttribute("cartelDesaparicion");
		
		controlErroresCartelDesaparicion objControlErroresCartelDesaparicion = new controlErroresCartelDesaparicion(sesionCartelDesaparicion);
		
		if(objControlErroresCartelDesaparicion.isError() == true) {
			return "redirect:/publicar1_1";
		}
		
		return "views/publicar1_2";
	}
	
	
	@Value("${ruta.cartelesDesapareciones}")
	private String rutaDirectorio;
	
	@PostMapping("/paso1_2")
    @ResponseBody
    public String guardarImagen(Model modelo,
    							@RequestBody solicitudImagen imagenRequest) {

        byte[] decodedImage = Base64.getDecoder().decode(imagenRequest.getEstaImagen().split(",")[1]);

        try {
            String nombreImagen = System.currentTimeMillis() + ".png";
            File archivoImagen = new File(rutaDirectorio + nombreImagen);
            try (FileOutputStream fos = new FileOutputStream(archivoImagen)) {
                fos.write(decodedImage);
            }
            cartelDesaparicion SesionCartelDesaparicion = (cartelDesaparicion) modelo.getAttribute("cartelDesaparicion");
            SesionCartelDesaparicion.setFoto("/img/carteles/desaparicion/"+nombreImagen);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Obtiene el nombre del usuario autenticado
            String nombreUsuario = authentication.getName();
            consultasCarteles consultasCarteles = new consultasCarteles();
            
            consultasCarteles.insertarDesaparicion(nombreUsuario, SesionCartelDesaparicion);
            
            // Devuelve el nombre de la imagen guardada
            return nombreImagen;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al guardar la imagen.";
        }
    }
}


