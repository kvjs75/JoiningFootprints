package es.dsw.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import es.dsw.config.SecurityConfiguration;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.controlErroresUsuarios;
import es.dsw.models.usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class RegistroController {
	
	//REGISTRO
	@GetMapping(value= {"/registro"})
	public String registrarse(
			Model modelo,
			@RequestParam(name="numError", defaultValue="0" ) String numError
								) {
		//[Comprueba quien es el usuario actual]
		//Explicado en IndexController
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			//Si ya esta autenticado quiere decir que ya esta registrado asi que lo redirige al index
			return "redirect:/index";
		}
		
		//Inicializacion de la variable de sesion
		if(modelo.getAttribute("usuarioRegistro") == null) {
			usuario usuario = new usuario();
			modelo.addAttribute("usuarioRegistro",usuario);
		}
		//Recibe el codigo de error y da el mensaje de error asociado, si llega a dar dicho error.
		controlErroresUsuarios controlErroresUsuarios = new controlErroresUsuarios(numError);
		modelo.addAttribute("msgError", controlErroresUsuarios.getMsgError());
		
		return "views/registro";
	}
	
	//crear usuario
	@PostMapping(value= {"/crearUsuario"})
	public String CrearUsuario(
			Model modelo,
			
			//Se utiliza para el autologin
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
		modelo.addAttribute("usuarioRegistro",usuario);
		controlErroresUsuarios controlErroresUsuarios = new controlErroresUsuarios(usuario,contrasenaRe,correoRe);
			
		if (controlErroresUsuarios.isError() == true) {
			//fallo el registro de usuario
			//Si hay un error en el objeto "usuario" envia el numero de error a la controladora registro
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
			//Se hace una consulta para registrar el usuario en la base de datos	
			consultasUsuarios.crearUsuario(usuario);
			estado.setComplete();
			return "redirect:/index?_csrf="+csrf;
		}
	}
}
