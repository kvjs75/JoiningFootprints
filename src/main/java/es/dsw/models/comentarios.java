package es.dsw.models;

public class comentarios {
	private int idComentario=0;
	private int idUsuario=0;
	private int idCartel=0;
	private String textoComentario="";
	private int likes=0;
	private String fechaPublicacion="";
	private String nick="";
	
	public comentarios() {}

	public comentarios(int idComentario, int idUsuario, int idCartel, String textoComentario, int likes,
			String fechaPublicacion) {
		super();
		this.idComentario = idComentario;
		this.idUsuario = idUsuario;
		this.idCartel = idCartel;
		this.textoComentario = textoComentario;
		this.likes = likes;
		this.fechaPublicacion = fechaPublicacion;
	}

	public int getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
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

	public String getTextoComentario() {
		return textoComentario;
	}

	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	
	
	

}
