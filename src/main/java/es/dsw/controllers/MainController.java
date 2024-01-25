package es.dsw.controllers;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.dsw.config.SecurityConfiguration;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.controlErroresUsuarios;
import es.dsw.models.usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	@GetMapping(value= {"/","/index"})
	public String index(Model modelo) {
		
		return "index";
	}
	
	@GetMapping(value= {"/login"})
	public String login(Model modelo) {
		
		return "login";
	}
	
	//Probar control de errores    
	@GetMapping(value= {"/registrarse"})
	public String registrarse(
			Model modelo,
			@RequestParam(name="numError", defaultValue="0" ) String numError
								) {
		controlErroresUsuarios controlErroresUsuarios = new controlErroresUsuarios(numError);
		modelo.addAttribute("msgError", controlErroresUsuarios.getMsgError());
		
		return "registrarse";
	}
	
	//crear usuario
	@PostMapping(value= {"/crearUsuario"})
	public String CrearUsuario(
			Model modelo,
			HttpServletRequest pedido,
			@RequestParam(name="nombreUsuario", defaultValue="" ) String nombreUsuario,
			@RequestParam(name="nick", defaultValue="" ) String nick,
			@RequestParam(name="contrasena", defaultValue="" ) String contrasena,
			@RequestParam(name="contrasenaRe", defaultValue="" ) String contrasenaRe,
			@RequestParam(name="correo", defaultValue="" ) String correo,
			@RequestParam(name="correoRe", defaultValue="" ) String correoRe,
			@RequestParam(name="Fnacimiento", defaultValue="" ) String Fnacimiento,
			@RequestParam(name="zonaGeografica", defaultValue="" ) String zonaGeografica,
			CsrfToken csrfToken ) {
		
		consultasUsuarios consultasUsuarios = new consultasUsuarios();
		String csrf = csrfToken.getToken();
		usuario usuario = new usuario(nombreUsuario,nick,contrasena,correo,Fnacimiento,zonaGeografica);
		controlErroresUsuarios controlErroresUsuarios = new controlErroresUsuarios(usuario,contrasenaRe,correoRe);
		
		if (controlErroresUsuarios.isError() == true) {
			//fallo el registro de usuario
			return "redirect:/registrarse?errorcreado&numError="+controlErroresUsuarios.getNumError()+"&_csrf="+csrf;
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
			return "redirect:/index?_csrf="+csrf;
		}
		
		
	}
	
}


