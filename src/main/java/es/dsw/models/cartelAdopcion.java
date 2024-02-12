package es.dsw.models;

public class cartelAdopcion extends carteles{
	private String vacunado="";
	private String esterilizado="";
	private String desparasitado="";
	private String requisitos="";
	private String entrevista="";
	private String fechaNacimiento="";
	
	
	public cartelAdopcion() {
		super();
	
	}

	public cartelAdopcion(int id, String foto, String nombreAnimal, String especie, String raza, String sexo,
			String telefono1, String telefono2, String correo, String descripcion, String tipoCartel, String vacunado, String esterilizado, String desparasitado, String requisitos,
			String entrevista, String fechaNacimiento) {
		
		super(id, foto, nombreAnimal, especie, raza, sexo, telefono1, telefono2, correo, descripcion, tipoCartel);
		this.vacunado = vacunado;
		this.esterilizado = esterilizado;
		this.desparasitado = desparasitado;
		this.requisitos = requisitos;
		this.entrevista = entrevista;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getVacunado() {
		return vacunado;
	}

	public void setVacunado(String vacunado) {
		this.vacunado = vacunado;
	}

	public String getEsterilizado() {
		return esterilizado;
	}

	public void setEsterilizado(String esterilizado) {
		this.esterilizado = esterilizado;
	}

	public String getDesparasitado() {
		return desparasitado;
	}

	public void setDesparasitado(String desparasitado) {
		this.desparasitado = desparasitado;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public String getEntrevista() {
		return entrevista;
	}

	public void setEntrevista(String entrevista) {
		this.entrevista = entrevista;
	}

	public String getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
	
	

}
