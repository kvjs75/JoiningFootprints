package es.dsw.controllers;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.dsw.datos.consultasCarteles;
import es.dsw.datos.consultasFiltros;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.cartelAdopcion;
import es.dsw.models.cartelDesaparicion;
import es.dsw.models.carteles;
import es.dsw.models.usuario;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class IndexController {
	
	//INDEX
	@GetMapping(value= {"/","/index"})
	public String index(Model modelo, 
						@RequestParam(name="confirmacionBorrado", defaultValue="false" ) boolean confirmacionBorrado) {
		
		//[Comprueba quien es el usuario actual]: Se utiliza para el navegador, para acceder a su propio perfil
		//Cuando el usuario se autentica coge el nombre de ese usuario, sino se autentica, el valor de "nombreUsuario" es "anonymousUser"
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        
        //Si no es un usuario "anonymousUser"
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			//con el nombre del usuario, se identifica quien es el que esta autenticado y se saca todos sus datos.
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
	
		//Resetea los datos de la variable de sesion de publicar. Para que el formulario de publicar se quede vacio al finalizar.
		cartelDesaparicion cartelDesaparicion = new cartelDesaparicion();
		modelo.addAttribute("cartelDesaparicion",cartelDesaparicion);
		cartelAdopcion cartelAdopcion = new cartelAdopcion();
		modelo.addAttribute("cartelAdopcion",cartelAdopcion);
		
		//Confirmacion para que muestre una alerta en el index cuando el moderador borra una publicacion (ModeradorController, linea 202).
		modelo.addAttribute("confirmacionBorrado", confirmacionBorrado);
		return "index";
	}
	
	//Controla la carga de carteles de 5 en 5 con paginación y la búsqueda de carteles en el index mediante con una subvista, a través de AJAX (cargarCarteles.js)
	@GetMapping("/masCarteles")
	public String moreCarteles(Model modelo,
								//cantidad que se va a mostrar (Simpre mostrara 5)
								@RequestParam(defaultValue = "5") int cantidad,
								//cantidad que se va a ignorar u omite
        						@RequestParam(defaultValue = "5") int offset,
        						//el buscador, que inicialmente esta desactivado hasta que se haga uso
        						@RequestParam(required = false) String terminoBusqueda) {
		
	    consultasCarteles consultasCarteles = new consultasCarteles();
	    consultasFiltros objConsultasFiltrosCarteles = new consultasFiltros();
	    ArrayList<carteles> carteles  = new ArrayList<carteles>();
	   
	    //Si el "terminoBusqueda" diferente a nulo y es difente a vacio. Se considera que se esta haciendo uso el buscador
	    if (terminoBusqueda != null && !terminoBusqueda.isEmpty()) {
	    	carteles = objConsultasFiltrosCarteles.mostrarBusquedaCarteles(cantidad, offset, terminoBusqueda);
	    
	    //Sino se esta haciendo uso del buscardor simplemente carga los carteles	
	    }else {
	    	carteles = consultasCarteles.mostrarCartelesGeneral(cantidad, offset);
	    }
	    
	    modelo.addAttribute("carteles",carteles);
	    
	    return "subview/listaCarteles";
	}
}
