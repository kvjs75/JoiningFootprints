package es.dsw.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import es.dsw.datos.consultasCarteles;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.cartelAdopcion;
import es.dsw.models.cartelDesaparicion;
import es.dsw.models.controlErroresCartelAdopcion;
import es.dsw.models.controlErroresCartelDesaparicion;
import es.dsw.models.solicitudImagen;
import es.dsw.models.usuario;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class PublicarController {
	
	//PUBLICAR1_1
	@GetMapping(value= {"/publicar1_1"})
	public String publicar1_1(Model modelo,
			 				  @RequestParam(name="codError", defaultValue="" ) String codError) {
		
		//[Comprueba quien es el usuario actual]: Se utiliza para el navegador, para acceder a su propio perfil
		//funcionamiento explicado en IndexController
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		
		//Se inicializa la variable de sesión
		if(modelo.getAttribute("cartelDesaparicion") == null) {	
			cartelDesaparicion cartelDesaparicion = new cartelDesaparicion();
			modelo.addAttribute("cartelDesaparicion",cartelDesaparicion);
		}
		
		//Recibe el codigo de error y da el mensaje de error asociado, si llega a dar dicho error.
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
						 @RequestParam(name="correo", defaultValue="" ) String emailContacto,
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
		
		//Se guarda los datos del formulario en el objeto
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
			objCartelDesaparicion.setRecompensa("1");
		}else {
			objCartelDesaparicion.setRecompensa("0");
		}
		
		//Se evalua si hay un error entre los datos del formulario
		controlErroresCartelDesaparicion objControlErroresCartelDesaparicion = new controlErroresCartelDesaparicion(objCartelDesaparicion);
		
		//Se guarda los datos colocados en el formulario, para estos no se pierdan si da un error y no volver a reescribirlo
		modelo.addAttribute("cartelDesaparicion",objCartelDesaparicion);
		
		//Si resulta que ha un error, redirige a la controladora publicar1_1, con dicho codigo de error para sacar el mensaje
		if(objControlErroresCartelDesaparicion.isError() == true) {
			return "redirect:/publicar1_1?codError="+objControlErroresCartelDesaparicion.getNumError();
		//Sino te redirige al siguiente paso de la publicación
		}else {
			return "redirect:/publicar1_2";
		}
	}
	
	//PUBLICAR1_2
	@GetMapping(value= {"/publicar1_2"})
	public String publicar1_2(Model modelo) {
		
		//[Comprueba quien es el usuario actual]: Se utiliza para el navegador, para acceder a su propio perfil
		//funcionamiento explicado en IndexController
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		
		//Se utilizar para evitar que el usuario pueda entrar al paso2, sin haber rellenado el formulario.
		//En este caso si la variable de sesion esta vacia.
		if(modelo.getAttribute("cartelDesaparicion") == null) {
			return "redirect:/publicar1_1";
		}
		
		//Se utilizar para evitar que el usuario pueda entrar al paso2, evitando que el usuario pueda acceder sin haber rellenado el formulario correctamente.
		//En este caso utilizando la variable de sesion junto al control de errores
		cartelDesaparicion sesionCartelDesaparicion = (cartelDesaparicion) modelo.getAttribute("cartelDesaparicion");
		controlErroresCartelDesaparicion objControlErroresCartelDesaparicion = new controlErroresCartelDesaparicion(sesionCartelDesaparicion);
		if(objControlErroresCartelDesaparicion.isError() == true) {
			return "redirect:/publicar1_1";
		}
		
		return "views/publicar1_2";
	}
	
	//Desde el fichero "application.properties" se guarda la ruta en donde se van alojar los carteles de desaparicion
	//Se guarda en la variable "rutaDirectorio" para su uso en la controladora paso1_2
	@Value("${ruta.cartelesDesapareciones}")
	private String rutaDirectorio;
	
	@PostMapping("/paso1_2")
    @ResponseBody
    public String guardarImagen(Model modelo,
    							SessionStatus estado,
    							@RequestBody solicitudImagen imagenRequest) {
		
		//Decodifica la imagen recibida en la solicitud, codificada en Base64, 
		//Se utilizando la coma como separador, y se toma la segunda parte para la decodificación
        byte[] decodedImage = Base64.getDecoder().decode(imagenRequest.getEstaImagen().split(",")[1]);

        try {
        	//Se genera la imagen con un nombre unico el cual se guardara en la ruta de imagenes del servidor.
            String nombreImagen = System.currentTimeMillis() + ".png";
            File archivoImagen = new File(rutaDirectorio + nombreImagen);
            try (FileOutputStream fos = new FileOutputStream(archivoImagen)) {
            	//decodifica la imagen
                fos.write(decodedImage);
            }
            //Desde la variable de sesion se guarda como string la ruta donde se aloja la imagen. para que posteriormente se guarda en la base de datos
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
	
	
	
	//PUBLICAR2_1
	//Mismo procedimiento pero para carteles de adopción
	@GetMapping(value= {"/publicar2_1"})
	public String publicar2_1(Model modelo,
							  @RequestParam(name="codError", defaultValue="" ) String codError) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		
		if(modelo.getAttribute("cartelAdopcion") == null) {
			cartelAdopcion cartelAdopcion = new cartelAdopcion();
			modelo.addAttribute("cartelAdopcion",cartelAdopcion);
		}
		
		controlErroresCartelAdopcion objControlErroresCartelAdopcion = new controlErroresCartelAdopcion(codError);
		
		modelo.addAttribute("msgError", objControlErroresCartelAdopcion.getMsgError());
		
		return "views/publicar2_1";
	}
	
	//Mismo procedimiento pero para carteles de adopción
	@PostMapping(value= {"/paso2_1"})
	public String paso2_1(Model modelo, 
						 @RequestParam(name="nombreAnimal", defaultValue="" ) String nombreAnimal,
						 @RequestParam(name="raza", defaultValue="" ) String raza,
						 @RequestParam(name="especie", defaultValue="" ) String especie,
						 @RequestParam(name="sexo", defaultValue="" ) String sexo,
						 @RequestParam(name="fechaNacimiento", defaultValue="" ) String fechaNacimiento,
						 @RequestParam(name="descripcion", defaultValue="" ) String descripcion,
						 @RequestParam(name="requisitos", defaultValue="" ) String requisitos,
						 @RequestParam(name="telefono1", defaultValue="" ) String telefono1,
						 @RequestParam(name="telefono2", defaultValue="" ) String telefono2,
						 @RequestParam(name="correo", defaultValue="" ) String emailContacto,
						 @RequestParam(name="vacunado", defaultValue="" ) String vacunado,
						 @RequestParam(name="esterilizado", defaultValue="" ) String esterilizado,
						 @RequestParam(name="desparasitado", defaultValue="" ) String desparasitado,
						 @RequestParam(name="entrevista", defaultValue="" ) String entrevista
 							) {
		
		cartelAdopcion objCartelAdopcion = new cartelAdopcion();
		
		//Condicion para poner las primeras letras del nombre en mayuscula
		if (nombreAnimal != null && !nombreAnimal.isEmpty()) {
			String[] palabras = nombreAnimal.split("\\s+");
		    for (int i = 0; i < palabras.length; i++) {
		    	palabras[i] = palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1).toLowerCase();
		    }
		    nombreAnimal = String.join(" ", palabras);
		}		
		
		objCartelAdopcion.setNombreAnimal(nombreAnimal);
		objCartelAdopcion.setRaza(raza);
		objCartelAdopcion.setEspecie(especie);
		objCartelAdopcion.setSexo(sexo);
		objCartelAdopcion.setFechaNacimiento(fechaNacimiento);
		objCartelAdopcion.setDescripcion(descripcion);
		objCartelAdopcion.setRequisitos(requisitos);
		objCartelAdopcion.setTelefono1(telefono1);
		objCartelAdopcion.setTelefono2(telefono2);
		objCartelAdopcion.setCorreo(emailContacto);
		if(vacunado.equals("on")) {
			objCartelAdopcion.setVacunado("1");
		}else {
			objCartelAdopcion.setVacunado("0");
		}
		if(esterilizado.equals("on")) {
			objCartelAdopcion.setEsterilizado("1");
		}else {
			objCartelAdopcion.setEsterilizado("0");
		}
		if(desparasitado.equals("on")) {
			objCartelAdopcion.setDesparasitado("1");
		}else {
			objCartelAdopcion.setDesparasitado("0");
		}
		if(entrevista.equals("on")) {
			objCartelAdopcion.setEntrevista("1");
		}else {
			objCartelAdopcion.setEntrevista("0");
		}
		
		controlErroresCartelAdopcion objControlErroresCartelAdopcion = new controlErroresCartelAdopcion(objCartelAdopcion);
		
		modelo.addAttribute("cartelAdopcion",objCartelAdopcion);
		
		if(objControlErroresCartelAdopcion.isError() == true) {
			return "redirect:/publicar2_1?codError="+objControlErroresCartelAdopcion.getNumError();
		}else {
			return "redirect:/publicar2_2";
		}
	
	}
	
	//PUBLICAR2_2
	//Mismo procedimiento pero para carteles de adopción
	@GetMapping(value= {"/publicar2_2"})
	public String publicar2_2(Model modelo) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		
		if(modelo.getAttribute("cartelAdopcion") == null) {
			return "redirect:/publicar2_1";
		}
		
		cartelAdopcion sesionCartelAdopcion  = (cartelAdopcion ) modelo.getAttribute("cartelAdopcion");
		
		controlErroresCartelAdopcion  objControlErroresCartelAdopcion  = new controlErroresCartelAdopcion (sesionCartelAdopcion);
		
		if(objControlErroresCartelAdopcion .isError() == true) {
			return "redirect:/publicar2_1";
		}
		
		
		return "views/publicar2_2";
	}
	
	//Mismo procedimiento pero para carteles de adopción
	@Value("${ruta.cartelesAdopciones}")
	private String rutaDirectorio2;
	
	@PostMapping("/paso2_2")
    @ResponseBody
    public String guardarImagen2(Model modelo,
    							SessionStatus estado,
    							@RequestBody solicitudImagen imagenRequest) {
				
        byte[] decodedImage = Base64.getDecoder().decode(imagenRequest.getEstaImagen().split(",")[1]);

        try {
            String nombreImagen = System.currentTimeMillis() + ".png";
            File archivoImagen = new File(rutaDirectorio2 + nombreImagen);
            try (FileOutputStream fos = new FileOutputStream(archivoImagen)) {
                fos.write(decodedImage);
            }
            cartelAdopcion SesionCartelAdopcion = (cartelAdopcion) modelo.getAttribute("cartelAdopcion");
            SesionCartelAdopcion.setFoto("/img/carteles/adopcion/"+nombreImagen);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Obtiene el nombre del usuario autenticado
            String nombreUsuario = authentication.getName();
            consultasCarteles consultasCarteles = new consultasCarteles();
            
            consultasCarteles.insertarAdopcion(nombreUsuario, SesionCartelAdopcion);
            // Devuelve el nombre de la imagen guardada
            
            return nombreImagen;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al guardar la imagen.";
        }
    }
	
	
	//CONFIRMACION
	@GetMapping(value= {"/publicar_confirmacion"})
	public String publicar_confirmacion(Model modelo,
			 							@RequestParam(name="confirmacion", defaultValue="false" ) boolean confirmacion
										) {
		//Se utilizar para evitar que el usuario pueda entrar a la controladora, sin haber completado todos los pasos de una publicacion de desaparicion/adopcion
		if(confirmacion == false) {
			return "redirect:/index";
		}
		
		//[Comprueba quien es el usuario actual]: Se utiliza para el navegador y para el uso del enlace de perfil de la confirmacion de la solicitud (para ver la solicitud)
		//funcionamiento explicado en IndexController
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        usuario usuario = new usuario();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
		}
		
		//Se utiliza para evitar que el usuario pueda entrar a la controladora, si el usuario no tiene un cartel pendiente
		consultasCarteles objConsultasCarteles = new consultasCarteles();
		if(objConsultasCarteles.mostrarCartelesPerfilNickPendientes(usuario.getNick()).size() == 0) {
			return "redirect:/index";
		}
		
		//Se utiliza para el uso del enlace de cartel de la confirmacion de la solicitud (para ver el cartel ya creado)
		int idCartel = objConsultasCarteles.mostrarCartelesPerfilNickPendientes(usuario.getNick()).get(0).getId();
		
		modelo.addAttribute("usuarioActual",usuario);
		modelo.addAttribute("idCartel",idCartel);
		
		//Reseta los datos de la variable de sesion de publicar. Para que el formulario de publicar se quede vacio al finalizar.
		cartelDesaparicion cartelDesaparicion = new cartelDesaparicion();
		modelo.addAttribute("cartelDesaparicion",cartelDesaparicion);
		cartelAdopcion cartelAdopcion = new cartelAdopcion();
		modelo.addAttribute("cartelAdopcion",cartelAdopcion);
		
		return "views/publicar_confirmacion";
	}
}
