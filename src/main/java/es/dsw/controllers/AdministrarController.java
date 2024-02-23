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
import org.springframework.web.bind.annotation.SessionAttributes;

import es.dsw.datos.consultasCarteles;
import es.dsw.datos.consultasFiltros;
import es.dsw.datos.consultasRoles;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.carteles;
import es.dsw.models.roles;
import es.dsw.models.usuario;

@Controller
@SessionAttributes({"usuarioRegistro","cartelDesaparicion", "cartelAdopcion"})
public class AdministrarController {
	
	//rutas de los repositorios donde se almacenan los carteles.
	//se utilizara para su eliminacion
	@Value("${ruta.cartelesDesapareciones}")
	private String rutaDirectorio;
	
	@Value("${ruta.cartelesAdopciones}")
	private String rutaDirectorio2;
	
	//ADMINISTRAR
	@GetMapping(value= {"/administrar"})
	public String administrar(Model modelo) {
		
		//[Comprueba quien es el usuario actual]: Se utiliza para el navegador, para acceder a su propio perfil
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		return "views/administrar";
	}
	
	//Controla la carga usuarios y la búsqueda usuario mediante con una subvista, a través de AJAX (cargarUsuarioRoles.js)
	@GetMapping(value= {"/masUsuarios"})
	public String masUsuarios(Model modelo,
							  @RequestParam(required = false) String terminoBusquedaUsuario) {
		
		//[Comprueba quien es el usuario actual]: Para que no puedas borrar su propio usuario que esta autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
		if(!nombreUsuario.equals("anonymousUser")) {
			consultasUsuarios consultasUsuarios = new consultasUsuarios();
			usuario usuario = consultasUsuarios.comprobarTuPerfil(nombreUsuario);
			modelo.addAttribute("usuarioActual",usuario);
		}
		
		consultasFiltros objConsultaFiltros = new consultasFiltros();
		consultasUsuarios consultasUsuarios = new consultasUsuarios();
		ArrayList<usuario> listaUsuarios = new ArrayList<usuario>();
		
		//Si el "terminoBusqueda" es diferente a nulo y es difente a vacio. Se considera que se esta haciendo uso del buscador
		if (terminoBusquedaUsuario != null && !terminoBusquedaUsuario.isEmpty()) {
			listaUsuarios = objConsultaFiltros.mostrarBusquedaPerfiles(terminoBusquedaUsuario);
	    	
		//Sino se esta haciendo uso del buscardor simplemente carga los usuarios		
	    }else {
	    	listaUsuarios = consultasUsuarios.mostrarUsuarios();
	    	
	    }
		
		modelo.addAttribute("listaUsuarios",listaUsuarios);
		
		//devuelve una subvista
		return "subview/listaUsuariosAdministrador";
	}
	
	@GetMapping(value= {"/borrarUsuarios"})
	public String borrarUsuarios(Model modelo,
			 					 @RequestParam(name="idUsuario", defaultValue="-1" ) int idUsuario) {

		//Mediante con AJAX: cuando el Administrador pulsa el boton, se elimina el usuario y en cascada todo lo relacionado con el. 
		
		consultasUsuarios objConsultasUsuarios = new consultasUsuarios();
		consultasCarteles consultasCarteles = new consultasCarteles();
		ArrayList<carteles> carteles = consultasCarteles.mostrarCartelesGeneralAdmin();
		
		//Bucle que Borra todas las Imagenes de carteles relacionado con este usuario
		for (int i=0;i<carteles.size();i++) {
			int indexCarteles = -1;
			//Se busca la posicion del cartel en el que se pretende borrar la imagen
			if(carteles.get(i).getIdUsuario() == idUsuario) {
				indexCarteles = i;
			}
			//Comprueba si el cartel es del usuario que se esta tratando de eliminar
			if(indexCarteles != -1) {
				
				//Una vez detectada la posicion de la imagen que se quiera borrar
				//Se divide la URL (getFoto) de la imagen para sacar su nombre
				String[] arrayURL = carteles.get(indexCarteles).getFoto().split("/");
			    String nombreArchivo = arrayURL[arrayURL.length - 1];
			    
			    String rutaImagen = "";
			    //Luego se averigua la ruta del directorio al que pertenece la imagen por su tipo (desaparicion/adopcion)
			    if(carteles.get(indexCarteles).getTipoCartel().equals("Desaparición")) {
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
			}
		}
		
		
		//Borra usuario por el administrador
		objConsultasUsuarios.borrarUsuario(idUsuario);

		//devuelve una subvista
		return "subview/listaUsuariosAdministrador";
	}
	
	//Controla la carga roles y la búsqueda roles mediante con una subvista, a través de AJAX (cargarUsuarioRoles.js)
	@GetMapping(value= {"/masRoles"})
	public String masRoles(Model modelo,
							  @RequestParam(required = false) String terminoBusquedaRoles) {
	
		consultasFiltros objConsultaFiltros = new consultasFiltros();
		consultasRoles objConsultasRoles = new consultasRoles();
		ArrayList<roles> listaRoles = new ArrayList<roles>();
		
		//Si el "terminoBusqueda" es diferente a nulo y es difente a vacio. Se considera que se esta haciendo uso del buscador
		if (terminoBusquedaRoles != null && !terminoBusquedaRoles.isEmpty()) {
			listaRoles = objConsultaFiltros.mostrarBusquedaRoles(terminoBusquedaRoles);
	    	

		//Sino se esta haciendo uso del buscardor simplemente carga los usuarios	
	    }else {
	    	listaRoles = objConsultasRoles.mostrarRoles();
	    	
	    }
		
		modelo.addAttribute("listaRoles",listaRoles);
		
		//devuelve una subvista
		return "subview/listaRolesAdministrador";
	}
	
	
	@GetMapping(value= {"/crearRol"})
	public String crearRol(Model modelo,
						   @RequestParam(name="nombreRol", defaultValue="") String nombreRol) {
	
		//Mediante con AJAX: al pulsar el boton crea un nuevo rol y se añade a la base de datos 
		consultasRoles objConsultasRoles = new consultasRoles();
		objConsultasRoles.crearRol(nombreRol);
		
		//devuelve una subvista
		return "subview/listaRolesAdministrador";
	}
	
	
	@GetMapping(value= {"/borrarRol"})
	public String borrarRol(Model modelo,
						   @RequestParam(name="idRol", defaultValue="-1") int idRol) {
	
		//Mediante con AJAX: al pulsar el boton crea borra ese rol
		consultasRoles objConsultasRoles = new consultasRoles();
		objConsultasRoles.borrarRol(idRol);
		
		//devuelve una subvista
		return "subview/listaRolesAdministrador";
	}
}
