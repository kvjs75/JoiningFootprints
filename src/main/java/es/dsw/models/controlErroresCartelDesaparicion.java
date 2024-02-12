package es.dsw.models;

import java.time.LocalDate;

public class controlErroresCartelDesaparicion {
	private String numError="0";
	private boolean error=false;
	private String msgError="";
	
	
	public controlErroresCartelDesaparicion() {
		
	}

	public controlErroresCartelDesaparicion(cartelDesaparicion objCartelDesaparicion) {
		
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaDesaparecion = null;
		
		if(!objCartelDesaparicion.getFechaDesaparicion().equals("")) {
			fechaDesaparecion = LocalDate.parse(objCartelDesaparicion.getFechaDesaparicion());
		}
		
		
		if(objCartelDesaparicion.getNombreAnimal().trim().equals("")) {
			this.numError="1-1";
			this.error=true;
			this.msgError="Nombre es obligatorio";
		}
		
		
		else if(objCartelDesaparicion.getNombreAnimal().length() >=15) {
			this.numError="1-2";
			this.error=true;
			this.msgError="Nombre no puede superar los 30 caracteres";
		}
		
		else if(objCartelDesaparicion.getEspecie().equals("")) {
			this.numError="2-1";
			this.error=true;
			this.msgError="Especie es obligatorio";
		}
		
		else if(objCartelDesaparicion.getSexo().equals("")) {
			this.numError="3-1";
			this.error=true;
			this.msgError="Sexo es obligatorio";
		}
		
		else if(objCartelDesaparicion.getFechaDesaparicion().equals("")) {
			this.numError="4-1";
			this.error=true;
			this.msgError="La fecha es obligatorio";
		}
		
		else if(!objCartelDesaparicion.getFechaDesaparicion().equals("") && fechaActual.isBefore(fechaDesaparecion)) {
				this.numError="4-2";
				this.error=true;
				this.msgError="La fecha no puede ser mayor que la actual";
		}
		
		else if(objCartelDesaparicion.getDescripcion().trim().equals("")) {
			this.numError="5-1";
			this.error=true;
			this.msgError="La descripción es obligatorio";
		}
		
		else if(objCartelDesaparicion.getLugarDesaparicion().trim().equals("")) {
			this.numError="6-1";
			this.error=true;
			this.msgError="el lugar de la desaparición es obligatorio";
		}
		
		else if(objCartelDesaparicion.getTelefono1().trim().equals("")) {
			this.numError="7-1";
			this.error=true;
			this.msgError="Télefono1 es obligatorio";
		}
		
		else if(objCartelDesaparicion.getTelefono1().trim().length() != 9) {
			this.numError="7-2";
			this.error=true;
			this.msgError="Télefono1 tiene que tener 9 digitos";
		}
		
		else if(!objCartelDesaparicion.getTelefono2().trim().equals("") && objCartelDesaparicion.getTelefono2().length() != 9) {
			this.numError="8-1";
			this.error=true;
			this.msgError="Télefono2 tiene que tener 9 digitos";
		}
		
		
	}
	
	public controlErroresCartelDesaparicion(String codigoError) {
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
			this.msgError="La fecha es obligatorio";
		}
		
		if(codigoError.equals("4-2")) {
			this.error=true;
			this.msgError="La fecha no puede ser mayor que la actual";
		}
		
		if(codigoError.equals("5-1")) {
			this.error=true;
			this.msgError="La descripción es obligatorio";
		}
		
		if(codigoError.equals("6-1")) {
			this.error=true;
			this.msgError="el lugar de la desaparición es obligatorio";
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
