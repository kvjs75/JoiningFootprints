package es.dsw.controllers;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.dsw.datos.consultasCarteles;
import es.dsw.datos.consultasComentarios;
import es.dsw.datos.consultasReportes;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.cartelAdopcion;
import es.dsw.models.cartelDesaparicion;
import es.dsw.models.carteles;
import es.dsw.models.comentarios;
import es.dsw.models.reporteCartel;
import es.dsw.models.reporteComentario;
import es.dsw.models.usuario;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class PublicacionController {
	
	//PUBLICACION
	@GetMapping(value= {"/publicacion"})
	public String publicacion(Model modelo,
							  @RequestParam(name="idCartel", defaultValue="-1" ) int idCartel,
							  @RequestParam(name="reporteExitoso", defaultValue="false" ) boolean reporteExitoso) {
		
		//[Comprueba quien es el usuario actual]: Se utiliza para el navegador, para acceder a su propio perfil
		//funcionamiento explicado en IndexController
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		
		//Se utiliza para mostrar los datos de un tipo de cartel u otro. 
		//Su uso inicialmente iba ser ese, pero al final solo se utilizo para mostrar datos generales de los carteles
		consultasCarteles consultasCarteles = new consultasCarteles();
		carteles cartel = null;
		
		if(consultasCarteles.detectarTipoCartel(idCartel).equals("Desaparición")) {
			cartel = (cartelDesaparicion) consultasCarteles.mostrarDatosDesaparicion(idCartel);
			modelo.addAttribute("cartel",cartel);
		}else if(consultasCarteles.detectarTipoCartel(idCartel).equals("Adopción")){
			cartel = (cartelAdopcion) consultasCarteles.mostrarDatosAdopcion(idCartel);;
			modelo.addAttribute("cartel",cartel);
		}
		
		//Se utiliza para mostrar la alerta de que el usuario haya hecho un reporte
		modelo.addAttribute("reporteExitoso",reporteExitoso);
		return "views/publicacion";
	}
	
	@PostMapping(value= {"/reportarCartel"},produces="application/json")
	@ResponseBody
	public reporteCartel reportarCartel(Model modelo,
									  @RequestParam(name="idUsuario", defaultValue="-1" ) int idUsuario,
									  @RequestParam(name="idCartel", defaultValue="-1" ) int idCartel,
									  @RequestParam(name="motivoCartel", defaultValue="" ) String motivo) {
		
		//Mediante con AJAX, recoge los datos del formulario y lo inserta los datos en la base de datos
		reporteCartel reporteCartel = new reporteCartel();
		reporteCartel.setIdUsuario(idUsuario);
		reporteCartel.setIdCartel(idCartel);
		reporteCartel.setMotivo(motivo);
		consultasReportes consultasReportes = new consultasReportes();
		consultasReportes.reportarCartel(reporteCartel);
		
		//devuelve un json NSU
		return reporteCartel;
	}
	
	//COMENTARIOS
	@PostMapping(value= {"/comentarios"},produces="application/json")
	@ResponseBody
	public comentarios comentarios(Model modelo,
							  	   @RequestParam(name="esteComentario", defaultValue="" ) String comentario,
							  	   @RequestParam(name="idCartel", defaultValue="-1" ) int idCartel) {
		
		//[Comprueba quien es el usuario actual]: Se utiliza para saber quien es el usuario quien ha realizado el comentario
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = authentication.getName();
		
		//Mediante con AJAX, recoge los datos del formulario y lo inserta los datos en la base de datos
		consultasComentarios objConsultaComentarios = new consultasComentarios();
		
		comentarios objComentarios = new comentarios();
		objComentarios.setTextoComentario(comentario);
		
		objConsultaComentarios.insertarComentario(nombreUsuario, idCartel, objComentarios);
		
		//devuelve un json NSU
		return objComentarios;
	}
	
	
	@GetMapping(value= {"/cajaComentarios"})
	public String cajaComentarios(Model modelo,
								  @RequestParam(name="idCartel", defaultValue="-1" ) int idCartel) {
		
		usuario usuario = new usuario();
		boolean moderador = false;
		
		//[Comprueba quien es el usuario actual]
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			
			//Comprueba recorre los roles del usuario actual para ver si se trata de un moderador (para que este pueda borrar todos los comentarios)
			String[] roles = usuario.getRoles().split(",");
			for(int i = 0;i<roles.length;i++) {
				if(roles[i].equals("moderador")) {
					moderador = true;
				}
			}
		}
		
		
		//Mediante con AJAX, muestra todos los comentarios asociado al cartel con una subvista
		consultasComentarios objConsultasComentarios = new consultasComentarios();
		
		ArrayList<comentarios> listaComentarios = objConsultasComentarios.mostrarComentario(idCartel);
		
		modelo.addAttribute("listaComentarios", listaComentarios);
		modelo.addAttribute("usuarioActual",usuario);
		modelo.addAttribute("moderador",moderador);
		
		//retorna dicha subvista
		return "subview/cajaComentarios";
	}
	
	
	
	@GetMapping(value= {"/likeComentarios"})
	public String likeComentarios(Model modelo,
								  @RequestParam(name="idComentario", defaultValue="-1" ) int idComentario,
								  @RequestParam(name="nick", defaultValue="" ) String nick) {
		
		//[Comprueba quien es el usuario actual]: para saber quien ha dado "me gusta" a dicho comentario 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //Obtiene el nombre del usuario autenticado
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasComentarios objConsultasComentarios = new consultasComentarios();
			//Se necesita saber quien ha dado el "me gusta" y quien es el dueño del comentario para recibirlo
			objConsultasComentarios.likesComentarios(idComentario, nombreUsuario, nick);
		}
		
		//retorna dicha subvista
		return "redirect:/cajaComentarios";
	}
	
	@PostMapping(value= {"/reportarComentarios"},produces="application/json")
	@ResponseBody
	public reporteComentario reportarComentarios(Model modelo,
									  @RequestParam(name="idUsuario", defaultValue="-1" ) int idUsuario,
									  @RequestParam(name="idComentario", defaultValue="-1" ) int idComentario,
									  @RequestParam(name="motivoComentario", defaultValue="" ) String motivo) {
		
		//Mediante con AJAX, recoge los datos del formulario y lo inserta los datos en la base de datos
		reporteComentario reporteComentario = new reporteComentario();
		reporteComentario.setIdUsuario(idUsuario);
		reporteComentario.setIdComentario(idComentario);
		reporteComentario.setMotivo(motivo);
		consultasReportes consultasReportes = new consultasReportes();
		consultasReportes.reportarComentario(reporteComentario);
		
		//devuelve un json NSU
		return reporteComentario;
	}
	
	
	@GetMapping(value= {"/borrarComentarios"})
	public String borrarComentarios(Model modelo,
								  @RequestParam(name="idComentario", defaultValue="-1" ) int idComentario,
								  @RequestParam(name="nick", defaultValue="" ) String nick) {
		
		//Mediante con AJAX, elimina el comentario
		consultasComentarios objConsultasComentarios = new consultasComentarios();
		
		objConsultasComentarios.borrarComentario(idComentario,nick);
		
		//retorna una subvista
		return "redirect:/cajaComentarios";
	}
}
