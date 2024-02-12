package es.dsw.models;

import java.time.LocalDate;

public class controlErroresCartelAdopcion {
	private String numError="0";
	private boolean error=false;
	private String msgError="";
	
	public controlErroresCartelAdopcion() {}
	
	public controlErroresCartelAdopcion(cartelAdopcion objCartelAdopcion) {
		
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaNacimiento = null;
		
		if(!objCartelAdopcion.getFechaNacimiento().equals("")) {
			fechaNacimiento = LocalDate.parse(objCartelAdopcion.getFechaNacimiento());
		}
		
		
		if(objCartelAdopcion.getNombreAnimal().trim().equals("")) {
			this.numError="1-1";
			this.error=true;
			this.msgError="Nombre es obligatorio";
		}
		
		
		else if(objCartelAdopcion.getNombreAnimal().length() >=15) {
			this.numError="1-2";
			this.error=true;
			this.msgError="Nombre no puede superar los 30 caracteres";
		}
		
		else if(objCartelAdopcion.getEspecie().trim().equals("")) {
			this.numError="2-1";
			this.error=true;
			this.msgError="Especie es obligatorio";
		}
		
		else if(objCartelAdopcion.getSexo().equals("")) {
			this.numError="3-1";
			this.error=true;
			this.msgError="Sexo es obligatorio";
		}
		
		else if(!objCartelAdopcion.getFechaNacimiento().equals("") && fechaActual.isBefore(fechaNacimiento)) {
			this.numError="4-1";
			this.error=true;
			this.msgError="La fecha no puede ser mayor que la actual";
		}
		
		else if(objCartelAdopcion.getDescripcion().trim().equals("")) {
			this.numError="5-1";
			this.error=true;
			this.msgError="La descripción es obligatorio";
		}
		
		else if(objCartelAdopcion.getRequisitos().trim().equals("")) {
			this.numError="6-1";
			this.error=true;
			this.msgError="el requisito es obligatorio";
		}
		
		else if(objCartelAdopcion.getTelefono1().trim().equals("")) {
			this.numError="7-1";
			this.error=true;
			this.msgError="Télefono1 es obligatorio";
		}
		
		else if(objCartelAdopcion.getTelefono1().trim().length() != 9) {
			this.numError="7-2";
			this.error=true;
			this.msgError="Télefono1 tiene que tener 9 digitos";
		}
		
		else if(!objCartelAdopcion.getTelefono2().trim().equals("") && objCartelAdopcion.getTelefono2().length() != 9) {
			this.numError="8-1";
			this.error=true;
			this.msgError="Télefono2 tiene que tener 9 digitos";
		}
		
		
	}
	
	public controlErroresCartelAdopcion(String codigoError) {
		if(codigoError.equals("1-1")) {
			this.error=true;
			this.msgError="Nombre es obligatorio";
		}
		
		if(codigoError.equals("1-2")) {
			this.error=true;
			this.msgError="Nombre no puede superar los 30 caracteres";
		}
		
		if(codigoError.equals("2-1")) {
			this.error=true;
			this.msgError="Especie es obligatorio";
		}
		
		if(codigoError.equals("3-1")) {
			this.error=true;
			this.msgError="Sexo es obligatorio";
		}
		
		if(codigoError.equals("4-1")) {
			this.error=true;
			this.msgError="La fecha no puede ser mayor que la actual";
		}
		
		if(codigoError.equals("5-1")) {
			this.error=true;
			this.msgError="La descripción es obligatorio";
		}
		
		if(codigoError.equals("6-1")) {
			this.error=true;
			this.msgError="el requisitos es obligatorio";
		}
		
		else if(codigoError.equals("7-1")) {
			this.error=true;
			this.msgError="Télefono1 es obligatorio";
		}
		
		else if(codigoError.equals("7-2")) {
			this.error=true;
			this.msgError="Télefono1 tiene que tener 9 digitos";
		}
		
		else if(codigoError.equals("8-1")) {
			this.error=true;
			this.msgError="Télefono2 tiene que tener 9 digitos";
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
