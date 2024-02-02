package es.dsw.controllers;

import java.util.ArrayList;

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
import es.dsw.datos.consultasCarteles;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.cartelDesaparicion;
import es.dsw.models.cartelAdopcion;
import es.dsw.models.carteles;
import es.dsw.models.controlErroresUsuarios;
import es.dsw.models.usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"usuario"})
public class MainController {
	@GetMapping(value= {"/","/carteles"})
	public String index(Model modelo) {
		
		return "views/carteles";
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
				return "redirect:/carteles?_csrf="+csrf;
			}
			
			
		}
}


