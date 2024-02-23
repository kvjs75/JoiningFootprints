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
import es.dsw.datos.consultasReportes;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.carteles;
import es.dsw.models.reporteCartel;
import es.dsw.models.reporteComentario;
import es.dsw.models.usuario;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class ModerarController {
	
	//rutas de los repositorios donde se almacenan los carteles.
	//se utilizara para su eliminacion
	@Value("${ruta.cartelesDesapareciones}")
	private String rutaDirectorio;
	
	@Value("${ruta.cartelesAdopciones}")
	private String rutaDirectorio2;
	
	//MODERAR
	@GetMapping(value= {"/moderar"})
	public String moderar(Model modelo) {
		
		//[Comprueba quien es el usuario actual]: Se utiliza para el navegador, para acceder a su propio perfil
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		
		return "views/moderar";
	}
	
	@ResponseBody
	@GetMapping(value= {"/contadorCartelesPendienteModerador"})
	public int contadorCartelesPendienteModerador(Model modelo) {

		//Mediante con AJAX: Se ve el numeros de carteles de solicitudes pendientes a tiempo real
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesGeneralModerador();
						
		return carteles.size();
	}
	
	@GetMapping(value= {"/mostrarCartelesPendientes"})
	public String mostarCartelesPendientes(Model modelo,
										   @RequestParam(name="nick", defaultValue="" ) String nick) {
		
		//Mediante con AJAX: Se va mostrando todos los carteles pendientes
		//para que pueda ir actualizando la lista en vivo si se aprueba o se rechazan y que estos vayan desapariciendo de la lista
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesGeneralModerador();
		modelo.addAttribute("carteles",carteles);
		
		//devuelve una subvista
		return "subview/listaCartelesModerar";
	}
	
	@GetMapping(value= {"/aprobarCartel"})
	public String aprobarCartel(Model modelo,
								@RequestParam(name="idCartel", defaultValue="-1" ) int idCartel) {
		
		//Mediante con AJAX: Al pulsar el boton se van aprobando los carteles pendiente para que estos se vayan desapariciendo en vivo de la lista 
		consultasCarteles consultasCarteles = new consultasCarteles();
		consultasCarteles.aprobarCartel(idCartel);
		
		//devuelve una subvista
		return "subview/listaCartelesModerar";
	}
	
	@GetMapping(value= {"/rechazarCartel"})
	public String rechazarCartel(Model modelo,
								@RequestParam(name="idCartel", defaultValue="-1" ) int idCartel) {
		
		//Mediante con AJAX: Se van rechazando los carteles pendiente para que estos se vayan desapariciendo en vivo de la lista 
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesGeneralModerador();
		modelo.addAttribute("carteles",carteles);
		
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
		return "subview/listaCartelesModerar";
	}
	
	
	@ResponseBody
	@GetMapping(value= {"/contadorCartelesDenunciadoModerador"})
	public int contadorCartelesDenunciadoModerador(Model modelo) {
		
		//Mediante con AJAX: Se ve el numeros de reportes pendientes de las denuncias de los carteles a tiempo real
		consultasReportes consultasReportes = new consultasReportes();
		ArrayList<reporteCartel> carteles = consultasReportes.mostrarReportarCartel();
						
		return carteles.size();
	}
	
	@GetMapping(value= {"/mostrarCartelesDenunciado"})
	public String mostrarCartelesDenunciado(Model modelo) {
		
		//Mediante con AJAX: Se va mostrando todos las denuncias de los carteles
		//para que pueda ir actualizando la lista en vivo si se marca como resuelto y desaparezca de la lista
		consultasReportes consultasReportes = new consultasReportes();
		ArrayList<reporteCartel> carteles = consultasReportes.mostrarReportarCartel();
		modelo.addAttribute("carteles",carteles);
		
		//retorna una subvista
		return "subview/cartelesDenunciadosModerador";
	}
	
	
	@GetMapping(value= {"/marcarReporteResueltoCartel"})
	public String marcarReporteResueltoCartel(Model modelo,
											@RequestParam(name="idReporteCartel", defaultValue="-1" ) int idReporteCartel) {
		
		//Mediante con AJAX: al pulsar el boton se va a marcar la denuncia a resueltas para que estas se vayan desapariciendo en vivo de la lista 
		consultasReportes consultasReportes = new consultasReportes();
		consultasReportes.marcarReporteResueltoCartel(idReporteCartel);
		
		//retorna una subvista
		return "subview/cartelesDenunciadosModerador";
	}
	
	@ResponseBody
	@GetMapping(value= {"/contadorComentarioDenunciadoModerador"})
	public int contadorComentarioDenunciadoModerador(Model modelo) {

		//Mediante con AJAX: Se ve el numeros de reportes pendientes de las denuncias de los carteles a tiempo real
		consultasReportes consultasReportes = new consultasReportes();
		ArrayList<reporteComentario> carteles = consultasReportes.mostrarReportarComentario();
						
		return carteles.size();
	}
	
	@GetMapping(value= {"/mostrarComentarioDenunciado"})
	public String mostrarComentarioDenunciado(Model modelo) {
		
		//Mediante con AJAX: Se va mostrando todos las denuncias de los comentarios
		//para que pueda ir actualizando la lista en vivo si se marca como resuelto y desaparezca de la lista
		consultasReportes consultasReportes = new consultasReportes();
		ArrayList<reporteComentario> comentarios = consultasReportes.mostrarReportarComentario();
		modelo.addAttribute("comentarios",comentarios);
		
		//retorna una subvista
		return "subview/comentariosDenunciadosModerador";
	}
	
	
	@GetMapping(value= {"/marcarReporteResueltoComentario"})
	public String marcarReporteResueltoComentario(Model modelo,
											@RequestParam(name="idReporteComentario", defaultValue="-1" ) int idReporteComentario) {
		
		//Mediante con AJAX: al pulsar el boton se va a marcar la denuncia a resueltas para que estas se vayan desapariciendo en vivo de la lista 
		consultasReportes consultasReportes = new consultasReportes();
		consultasReportes.marcarReporteResueltoComentario(idReporteComentario);
		
		//retorna una subvista
		return "subview/comentariosDenunciadosModerador";
	}
	
	
	@GetMapping(value= {"/borrarPublicacionModerador"})
	public String borrarPublicacionModerador(Model modelo,
											@RequestParam(name="idCartel", defaultValue="-1" ) int idCartel) {
		
		//Mediante con redirección: cuando el moderador pulsa el boton, se elimina la publicación entera y la imagen, y luego lo redirige a l index activando una alerta del suceso 
		
		consultasCarteles objConsultasCarteles = new consultasCarteles();
		ArrayList<carteles> listaCarteles = objConsultasCarteles.mostrarCartelesGeneral();
		
		int indexCarteles = -1;
		
		for (int i=0;i<listaCarteles.size();i++) {
			//Se busca la posicion del cartel en el que se pretende borrar la imagen
			if(listaCarteles.get(i).getId() == idCartel) {
				indexCarteles = i;
			}
		}
		
		
		//Una vez detectada la posicion de la imagen que se quiera borrar
		//Se divide la URL (getFoto) de la imagen para sacar su nombre
		String[] arrayURL = listaCarteles.get(indexCarteles).getFoto().split("/");
	    String nombreArchivo = arrayURL[arrayURL.length - 1];
	    
	    String rutaImagen = "";
	    //Luego se averigua la ruta del directorio al que pertenece la imagen por su tipo (desaparicion/adopcion)
	    if(listaCarteles.get(indexCarteles).getTipoCartel().equals("Desaparición")) {
	    	rutaImagen = rutaDirectorio+nombreArchivo;
	    }else {
	    	rutaImagen = rutaDirectorio2+nombreArchivo;
	    }
	    
	    //finalmente se borra la imagen
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
	    objConsultasCarteles.rechazarCartel(idCartel);
	    
		return "redirect:/index?confirmacionBorrado=true";
	}
}
