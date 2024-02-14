package es.dsw.models;

public class reporteCartel {
	int idReporte = 0;
	int idUsuario = 0;
	int idCartel = 0;
	String motivo = "";
	String estado = "";
	String tipoCartel = "";
	String nombreAnimal = "";
	String fechaPublicacion = "";
	String denunciante = "";
	String denunciado = "";
	
	public reporteCartel() {

	}
	
	public reporteCartel(int idReporte, int idUsuario, int idCartel, String motivo) {
		
		this.idReporte = idReporte;
		this.idUsuario = idUsuario;
		this.idCartel = idCartel;
		this.motivo = motivo;

	}

	public int getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(int idReporte) {
		this.idReporte = idReporte;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdCartel() {
		return idCartel;
	}

	public void setIdCartel(int idCartel) {
		this.idCartel = idCartel;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoCartel() {
		return tipoCartel;
	}

	public void setTipoCartel(String tipoCartel) {
		this.tipoCartel = tipoCartel;
	}

	public String getNombreAnimal() {
		return nombreAnimal;
	}

	public void setNombreAnimal(String nombreAnimal) {
		this.nombreAnimal = nombreAnimal;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getDenunciante() {
		return denunciante;
	}

	public void setDenunciante(String denunciante) {
		this.denunciante = denunciante;
	}

	public String getDenunciado() {
		return denunciado;
	}

	public void setDenunciado(String denunciado) {
		this.denunciado = denunciado;
	}
	
	

	
}
