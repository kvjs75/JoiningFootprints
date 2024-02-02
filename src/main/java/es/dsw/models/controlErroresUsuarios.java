package es.dsw.models;

import java.time.LocalDate;
import java.util.ArrayList;

import es.dsw.datos.consultasUsuarios;

public class controlErroresUsuarios {
	private String numError="0";
	private boolean error=false;
	private String msgError="";
	
	public controlErroresUsuarios() {
		
	}
//contructor iniciador (aqui se evalua el error)	
	public controlErroresUsuarios(usuario usuario, String contrasenaRe, String correoRe) {
		
		consultasUsuarios consulta = new consultasUsuarios();
		ArrayList<usuario> usuarios = consulta.mostrarUsuarios();
		
		boolean repetidoNombreUsuario = false;
		boolean repetidoNick = false;
		boolean repetidoCorreo = false;
		
		//los for son para comprobar si alguno de estos datos esta registrado en la base de datos
		for(int i=0;i<usuarios.size();i++) {
			if(usuarios.get(i).getNombreUsuario().equals(usuario.getNombreUsuario())) {
				repetidoNombreUsuario = true;
			}
		}
		
		for(int i=0;i<usuarios.size();i++) {
			if(usuarios.get(i).getNick().equals(usuario.getNick())) {
				repetidoNick = true;
			}
		}
		
		for(int i=0;i<usuarios.size();i++) {
			if(usuarios.get(i).getCorreo().equals(usuario.getCorreo())) {
				repetidoCorreo = true;
			}
		}
		
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaNacimiento = null;
		//para comprobar anticipadamente si el usuario dejo el campo vacio (y asi evitar errores sobretodo el error de que un campo vacio no se puede añadir en el LocalDate)
		if(!usuario.getFechaNacimiento().equals("")) {
			fechaNacimiento = LocalDate.parse(usuario.getFechaNacimiento());
		}
		
		
		
		
		
		if(usuario.getNombreUsuario().equals("")) {
			this.numError="1-1";
			this.msgError="El nombre de usuario no puede estar vacio";
			this.error=true;
		}
		
		else if(repetidoNombreUsuario == true) {
			this.numError="1-2";
			this.msgError="El nombre de usuario ya esta en uso";
			this.error=true;
		}
		
		else if(usuario.getNick().equals("")) {
			this.numError="2-1";
			this.msgError="El nick no puede estar vacio";
			this.error=true;
		}
		
		else if(repetidoNick == true) {
			this.numError="2-2";
			this.msgError="El nick ya esta en uso";
			this.error=true;
		}
		
		else if(usuario.getCorreo().equals("")) {
			this.numError="3-1";
			this.msgError="El correo no puede estar vacio";
			this.error=true;
		}
		
		else if(!usuario.getCorreo().equals(correoRe)) {
			this.numError="3-2";
			this.msgError="El campo Correo no coincide con el campo Repetir Correo";
			this.error=true;
		}
		
		else if(repetidoCorreo == true) {
			this.numError="3-3";
			this.msgError="El correo ya esta en uso";
			this.error=true;
		}
		
		else if(usuario.getContrasena().equals("")) {
			this.numError="4-1";
			this.msgError="El contraseña no puede estar vacio";
			this.error=true;
		}
		
		else if(!usuario.getContrasena().equals(contrasenaRe)) {
			this.numError="4-2";
			this.msgError="El campo Contraseña no coincide con el campo Repetir Contraseña";
			this.error=true;
		}
		
		else if(usuario.getFechaNacimiento().equals("")) {
			this.numError="5-1";
			this.msgError="El fecha nacimiento no puede estar vacio";
			this.error=true;
			
		}
		
		else if(fechaActual.isBefore(fechaNacimiento)) {
			this.numError="5-2";
			this.msgError="La fecha de nacimiento es mayor que la actual";
			this.error=true;
		}
		
	}

//Constructor resultado (aqui se aplica el mensage)
public controlErroresUsuarios(String nError) {
		
		if(nError.equals("1-1")) {
			
			this.msgError="El nombre de usuario no puede estar vacio";
			this.error=true;
		}
		
		else if(nError.equals("1-2")) {
			this.msgError="El nombre de usuario ya esta en uso";
			this.error=true;
		}
		
		else if(nError.equals("2-1")) {
			
			this.msgError="El nick no puede estar vacio";
			this.error=true;
		}
		
		else if(nError.equals("2-2")) {
			this.msgError="El nick ya esta en uso";
			this.error=true;
		}
		
		else if(nError.equals("3-1")) {
			this.msgError="El correo no puede estar vacio";
			this.error=true;
		}
		
		else if(nError.equals("3-2")) {
			this.msgError="El campo Correo no coincide con el campo Repetir Correo";
			this.error=true;
		}
		
		else if(nError.equals("3-3")) {
			this.msgError="El correo ya esta en uso";
			this.error=true;
		}
		
		else if(nError.equals("4-1")) {
			this.msgError="El contraseña no puede estar vacio";
			this.error=true;
		}
		
		else if(nError.equals("4-2")) {
			this.msgError="El campo Contraseña no coincide con el campo Repetir Contraseña";
			this.error=true;
		}
		
		else if(nError.equals("5-1")) {
			
			this.msgError="El fecha nacimiento no puede estar vacio";
			this.error=true;
		}
		
		else if(nError.equals("5-2")) {
			
			this.msgError="La fecha de nacimiento es mayor que la actual";
			this.error=true;
		}
		
		
	}

	public String getNumError() {
		return numError;
	}

	public void setNumError(String numError) {
		this.numError = numError;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	
	
}
