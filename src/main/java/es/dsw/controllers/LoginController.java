package es.dsw.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class LoginController {
	
	//LOGIN
	@GetMapping(value= {"/login"})
	public String login(Model modelo) {
		//[Comprueba quien es el usuario actual]
		//Explicado en IndexController
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			//Si ya esta autenticado quiere decir que no hara hace falta volver autenticarse asi que lo redirige al index
			return "redirect:/index";
		}
		return "views/autenticar";
	}
}
