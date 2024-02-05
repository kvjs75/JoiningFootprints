package es.dsw.models;

import java.util.ArrayList;

public class usuario {
	private int idusuario;
	private String nombreUsuario;
	private String nick;
	private String contrasena;
	private String correo;
	private String fechaNacimiento;
	private String zonaGeografica;
	private int puntuacionHonor;
	private int numLikes;
	private int numCompartir;
	private int numPenalizaciones;
	private String roles;
	
	//Contructor vacio
	public usuario() {
		this.nombreUsuario="";
		this.nick="";
		this.contrasena="";
		this.correo="";
		this.fechaNacimiento="";
		this.zonaGeografica="";
		this.puntuacionHonor=0;
		this.numLikes=0;
		this.numCompartir=0;
		this.numPenalizaciones=0;
		this.roles="";
	}
	
	//contructor basico (sin roles ni sistemas de honor en general)
	public usuario(String nombreUsuario, String nick, String contrasena, String correo, String fechaNacimiento,
			String zonaGeografica) {
	
		this.nombreUsuario = nombreUsuario;
		this.nick = nick;
		this.contrasena = contrasena;
		this.correo = correo;
		this.fechaNacimiento = fechaNacimiento;
		this.zonaGeografica = zonaGeografica;
	}
	
	
	//constructor con roles y id (sin sistema de honor)
	public usuario(int idUsuario, String nombreUsuario, String nick, String contrasena, String correo, String fechaNacimiento,
			String zonaGeografica, String roles) {
		
		this.idusuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.nick = nick;
		this.contrasena = contrasena;
		this.correo = correo;
		this.fechaNacimiento = fechaNacimiento;
		this.zonaGeografica = zonaGeografica;
		this.roles = roles;
	}

	
	//contructor complejo (lo trae todo)
	public usuario(int idUsuario, String nombreUsuario, String nick, String contrasena, String correo, String fechaNacimiento,
			String zonaGeografica, int puntuacionHonor, int numLikes, int numCompartir, int numPenalizaciones,
			String roles) {
		
		this.idusuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.nick = nick;
		this.contrasena = contrasena;
		this.correo = correo;
		this.fechaNacimiento = fechaNacimiento;
		this.zonaGeografica = zonaGeografica;
		this.puntuacionHonor = puntuacionHonor;
		this.numLikes = numLikes;
		this.numCompartir = numCompartir;
		this.numPenalizaciones = numPenalizaciones;
		this.roles = roles;
	}
	

	
	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getZonaGeografica() {
		return zonaGeografica;
	}

	public void setZonaGeografica(String zonaGeografica) {
		this.zonaGeografica = zonaGeografica;
	}

	public int getPuntuacionHonor() {
		return puntuacionHonor;
	}

	public void setPuntuacionHonor(int puntuacionHonor) {
		this.puntuacionHonor = puntuacionHonor;
	}

	public int getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}

	public int getNumCompartir() {
		return numCompartir;
	}

	public void setNumCompartir(int numCompartir) {
		this.numCompartir = numCompartir;
	}

	public int getNumPenalizaciones() {
		return numPenalizaciones;
	}

	public void setNumPenalizaciones(int numPenalizaciones) {
		this.numPenalizaciones = numPenalizaciones;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
	
}
