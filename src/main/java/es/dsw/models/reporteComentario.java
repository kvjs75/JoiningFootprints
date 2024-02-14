package es.dsw.models;

public class reporteComentario {
	int idReporte = 0;
	int idUsuario = 0;
	int idComentario = 0;
	int idCartel = 0;
	String motivo = "";
	String estado = "";
	String tipoCartel = "";
	String nombreAnimal = "";
	String fechaPublicacion = "";
	String denunciante = "";
	String denunciado = "";
	String comentario = "";
	
	public reporteComentario() {
	
	}
	
	public reporteComentario(int idReporte, int idUsuario, int idComentario, int idCartel, String motivo) {
		this.idReporte = idReporte;
		this.idUsuario = idUsuario;
		this.idComentario = idComentario;
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

	public int getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
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

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	

}
