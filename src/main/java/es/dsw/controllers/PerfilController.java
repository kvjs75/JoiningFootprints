package es.dsw.controllers;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.dsw.datos.consultasCarteles;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.carteles;
import es.dsw.models.usuario;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class PerfilController {
	
	//rutas de los repositorios donde se almacenan los carteles.
	//se utilizara para su eliminacion
	@Value("${ruta.cartelesDesapareciones}")
	private String rutaDirectorio;
	
	@Value("${ruta.cartelesAdopciones}")
	private String rutaDirectorio2;
	
	//PERFIL
	@GetMapping(value= {"/perfil"})
	public String perfil(Model modelo,
						 @RequestParam(name="nick", defaultValue="" ) String nick) {
	
		boolean dueno = false; 
		
		consultasUsuarios consultasUsuarios = new consultasUsuarios();
		usuario usuario = consultasUsuarios.mostrarPerfilNick(nick);
		
		//[Comprueba quien es el usuario actual]: 
		//Se utiliza para el navegador, para acceder a su propio perfil y ver los carteles pendientes del perfil mediante la variable dueno
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			usuario usuarioActual = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			if(usuarioActual.getNick().equals(usuario.getNick())) {
				dueno = true;
			}
			modelo.addAttribute("usuarioActual",usuarioActual);
		}
		
		modelo.addAttribute("usuario",usuario);
		modelo.addAttribute("dueno",dueno);
		return "views/perfil";
	}
	
	//botones
	@GetMapping(value= {"/resolverCartel"})
	public String resolverCartel(Model modelo,
						 @RequestParam(name="idCartel", defaultValue="-1" ) int idCartel) {
		
		//Mediante con AJAX: Cuando el propietario del perfil le da a un cartel resolver, este desaparecera de la lisa de actuales en vivo  
		consultasCarteles objConsultasCarteles = new consultasCarteles();
		objConsultasCarteles.resolverCartel(idCartel);
	
		//retorna una subvista
		return "subview/cartelesActuales";
	}
	
	
	@GetMapping(value= {"/noResolverCartel"})
	public String noResolverCartel(Model modelo,
						 @RequestParam(name="idCartel", defaultValue="-1" ) int idCartel) {
		
		//Mediante con AJAX: Cuando el propietario del perfil le da a un cartel no resolver, este desaparecera de la lisa de resueltos en vivo  
		consultasCarteles objConsultasCarteles = new consultasCarteles();
		objConsultasCarteles.noResolverCartel(idCartel);
	
		//retorna una subvista
		return "subview/cartelesResueltos";
	}
	
	@GetMapping(value= {"/borrarCartelesActuales"})
	public String borrarCartelActuales(Model modelo,
									   @RequestParam(name="idCartel", defaultValue="-1" ) int idCartel,
									   @RequestParam(name="nick", defaultValue="" ) String nick) {
		
		//Mediante con AJAX: Cuando el propietario del perfil le da a un cartel no resolver, este desaparecera de la lisa de resueltos en vivo  
		
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesPerfilNickActuales(nick);
		
		int indexCarteles = -1;
		
		for (int i=0;i<carteles.size();i++) {
			//Se busca la psosicion del cartel en el que se pretende borrar la imagen
			if(carteles.get(i).getId() == idCartel) {
				indexCarteles = i;
			}
		}
		
		//Una vez detectada la posicion de la imagen que se quiera borrar
		//Se divide la URL (getFoto) de la imagen para sacar su nombre
		String[] arrayURL = carteles.get(indexCarteles).getFoto().split("/");
	    String nombreArchivo = arrayURL[arrayURL.length - 1];
	    
	    //Luego se averigua la ruta del directorio al que pertenece la imagen por su tipo (desaparicion/adopcion)
	    String rutaImagen = "";
	    if(carteles.get(indexCarteles).getTipoCartel().equals("Desaparición")) {
	    	rutaImagen = rutaDirectorio+nombreArchivo;
	    }else {
	    	rutaImagen = rutaDirectorio2+nombreArchivo;
	    }
	    
	    //Finalmente se elimina la imagen
	    File archivo = new File(rutaImagen);
	    if (archivo.exists()) {
	        if (archivo.delete()) {
	            System.out.println("Imagen eliminada exitosamente.");
	        } else {
	            System.out.println("Error al eliminar la imagen.");
	        }
	    } else {
	        System.out.println("La imagen no existe en el servidor.");
	    }
	    
	    //Para ahorrar codigo, utiliza la misma consulta de rechazar carteles de la solicitudes pendiente del moderador
		consultasCarteles.rechazarCartel(idCartel);
		
		//devuelve una subvista
		return "subview/cartelesActuales";
	}
	
	@GetMapping(value= {"/borrarCartelesResueltos"})
	public String borrarCartelPendientes(Model modelo,
									   @RequestParam(name="idCartel", defaultValue="-1" ) int idCartel,
									   @RequestParam(name="nick", defaultValue="" ) String nick) {
		
		//Mediante con AJAX: Cuando el propietario del perfil le da a un cartel a borrar, este desaparecera de la lisa de resuelto en vivo y se bora todo lo relacionado en la base de datos 
		
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesPerfilNickResueltos(nick);
		int indexCarteles = -1;
		
		for (int i=0;i<carteles.size();i++) {
			//Se busca la psosicion del cartel en el que se pretende borrar la imagen
			if(carteles.get(i).getId() == idCartel) {
				indexCarteles = i;
			}
		}
		
		//Una vez detectada la posicion de la imagen que se quiera borrar
		//Se divide la URL (getFoto) de la imagen para sacar su nombre
		String[] arrayURL = carteles.get(indexCarteles).getFoto().split("/");
	    String nombreArchivo = arrayURL[arrayURL.length - 1];
	    
	    //Luego se averigua la ruta del directorio al que pertenece la imagen por su tipo (desaparicion/adopcion)
	    String rutaImagen = "";
	    if(carteles.get(indexCarteles).getTipoCartel().equals("Desaparición")) {
	    	rutaImagen = rutaDirectorio+nombreArchivo;
	    }else {
	    	rutaImagen = rutaDirectorio2+nombreArchivo;
	    }
	    
	    //Finalmente se elimina la imagen
	    File archivo = new File(rutaImagen);
	    if (archivo.exists()) {
	        if (archivo.delete()) {
	            System.out.println("Imagen eliminada exitosamente.");
	        } else {
	            System.out.println("Error al eliminar la imagen.");
	        }
	    } else {
	        System.out.println("La imagen no existe en el servidor.");
	    }
	    
	    //Para ahorrar codigo, utiliza la misma consulta de rechazar carteles de la solicitudes pendiente del moderador
		consultasCarteles.rechazarCartel(idCartel);
		
		//devuelve una subvista
		return "subview/cartelesResueltos";
	}
	
	//contador de carteles
	
	@ResponseBody
	@GetMapping(value= {"/contadorCartelesActuales"})
	public int contadorCartelesActuales(Model modelo,
						 		   		   @RequestParam(name="nick", defaultValue="" ) String nick) {

		//Mediante con AJAX: Se ve el numero de carteles actuales sin resolver en el perfil de usuario
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesPerfilNickActuales(nick);
		
						
		return carteles.size();
	}
	
	@ResponseBody
	@GetMapping(value= {"/contadorCartelesResueltos"})
	public int contadorCartelesResueltos(Model modelo,
						 		   		   @RequestParam(name="nick", defaultValue="" ) String nick) {

		//Mediante con AJAX: Se ve el numero de carteles resueltos en el perfil de usuario
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesPerfilNickResueltos(nick);
		
						
		return carteles.size();
	}
	
	@ResponseBody
	@GetMapping(value= {"/contadorCartelesPendientes"})
	public int contadorCartelesPendientes(Model modelo,
						 		   		   @RequestParam(name="nick", defaultValue="" ) String nick) {

		//Mediante con AJAX: Se ve el numero de carteles pendientes a la espera de ser aprobados por el moderador en el perfil de usuario
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesPerfilNickPendientes(nick);
		
						
		return carteles.size();
	}
	
	//[CONTENIDO DE LAS PESTAÑAS]
	
	@GetMapping(value= {"/cartelesActuales"})
	public String cartelesActuales(Model modelo,
						 		   @RequestParam(name="nick", defaultValue="" ) String nick) {
		
		usuario usuario = new usuario();
		//[Comprueba quien es el usuario actual]
		//Dependiendo de que si es tu perfil o no podras personalizar las pestañas (Borrar, Resolver o No Resolver)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
		}
		
		//Mediante con AJAX: muestra los carteles actuales no resueltos de forma dinamica
		
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesPerfilNickActuales(nick);
		modelo.addAttribute("carteles",carteles);
		modelo.addAttribute("usuarioActual",usuario);
					
		//devuelve una subvista
		return "subview/cartelesActuales";
	}
	
	
	@GetMapping(value= {"/cartelesResueltos"})
	public String cartelesResueltos(Model modelo,
						 		   @RequestParam(name="nick", defaultValue="" ) String nick) {
		
		usuario usuario = new usuario();
		//[Comprueba quien es el usuario actual]
		//Dependiendo de que si es tu perfil o no podras personalizar las pestañas (Borrar, Resolver o No Resolver)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
		}
		
		//Mediante con AJAX: muestra los carteles resueltos de forma dinamica
		
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesPerfilNickResueltos(nick);
		modelo.addAttribute("carteles",carteles);
		modelo.addAttribute("usuarioActual",usuario);
		
		//devuelve una subvista
		return "subview/cartelesResueltos";
	}
	
	
	
	@GetMapping(value= {"/cartelesPendientes"})
	public String cartelesPendientes(Model modelo,
						 		   @RequestParam(name="nick", defaultValue="" ) String nick) {

		//Mediante con AJAX: muestra los carteles pendientes a la espera de ser aprobado de forma dinamica
		
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesPerfilNickPendientes(nick);
		modelo.addAttribute("carteles",carteles);
			
		//devuelve una subvista
		return "subview/cartelesPendientes";
	}
}
