package es.dsw.controllers;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.dsw.datos.consultasFiltros;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.usuario;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class ComunidadController {
	
	//COMUNIDAD
	@GetMapping(value= {"/comunidad"})
	public String comunidad(Model modelo) {
		
		//[Comprueba quien es el usuario actual]: Se utiliza para el navegador, para acceder a su propio perfil
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		
		return "views/comunidad";
	}
	
	//Controla la carga usuarios y la búsqueda usuario mediante con una subvista, a través de AJAX (cargarPerfiles.js)
	@GetMapping(value= {"/masPerfiles"})
	public String masPerfiles(Model modelo,
							  @RequestParam(required = false) String terminoBusqueda
								) {
		consultasFiltros objConsultaFiltros = new consultasFiltros();
		consultasUsuarios consultasUsuarios = new consultasUsuarios();
		ArrayList<usuario> usuarios = new ArrayList<usuario>();
		
		//Si el "terminoBusqueda" es diferente a nulo y es difente a vacio. Se considera que se esta haciendo uso del buscador
		if (terminoBusqueda != null && !terminoBusqueda.isEmpty()) {
	    	usuarios = objConsultaFiltros.mostrarBusquedaPerfiles(terminoBusqueda);
	    	
	    //Sino se esta haciendo uso del buscardor simplemente carga los usuarios		
	    }else {
	    	usuarios = consultasUsuarios.mostrarUsuarios();
	    	
	    }
		
		modelo.addAttribute("usuarios",usuarios);
		//devuelve una subvista
		return "subview/listaPerfiles";
	}
}
