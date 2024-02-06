package es.dsw.models;

public class carteles {
	private int id=0;
	private String foto="";
	private String nombreAnimal="";
	private String especie="";
	private String raza="";
	private String sexo="";
	private String telefono1="";
	private String telefono2="";
	private String correo="";
	private String descripcion="";
	private String tipoCartel="";
	private String estadoCartel="";
	private String nick="";
	private String fechaPublicacion="";
	
	
	public carteles() {
		
	}
	
	//insertar datos
	public carteles(int id, String foto, String nombreAnimal, String especie, String raza, String sexo,
			String telefono1, String telefono2, String correo, String descripcion,
			String tipoCartel) {
		
		this.id = id;
		this.foto = foto;
		this.nombreAnimal = nombreAnimal;
		this.especie = especie;
		this.raza = raza;
		this.sexo = sexo;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.correo = correo;
		this.descripcion = descripcion;
		this.tipoCartel = tipoCartel;
	}
	
	//mostrar datos
	public carteles(int id, String foto, String nombreAnimal, String especie, String raza, String sexo,
			String telefono1, String telefono2, String correo, String descripcion, String tipoCartel,
			String estadoCartel, String nick, String fechaPublicacion) {
		super();
		this.id = id;
		this.foto = foto;
		this.nombreAnimal = nombreAnimal;
		this.especie = especie;
		this.raza = raza;
		this.sexo = sexo;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.correo = correo;
		this.descripcion = descripcion;
		this.tipoCartel = tipoCartel;
		this.estadoCartel = estadoCartel;
		this.nick = nick;
		this.fechaPublicacion=fechaPublicacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getNombreAnimal() {
		return nombreAnimal;
	}

	public void setNombreAnimal(String nombreAnimal) {
		this.nombreAnimal = nombreAnimal;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoCartel() {
		return tipoCartel;
	}

	public void setTipoCartel(String tipoCartel) {
		this.tipoCartel = tipoCartel;
	}

	public String getEstadoCartel() {
		return estadoCartel;
	}

	public void setEstadoCartel(String estadoCartel) {
		this.estadoCartel = estadoCartel;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	
	
	
	
}
