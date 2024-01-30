package es.dsw.models;

public class cartelDesaparicion extends carteles {
	
	private String fechaDesaparicion;
	private String lugarDesaparicion;
	private String llevaChip;
	
	public cartelDesaparicion() {
		super();
		
	}

	public cartelDesaparicion(int id, String foto, String nombreAnimal, String especie, String raza, String sexo,
			String telefono1, String telefono2, String correo, String descripcion, String tipoCartel, String fechaDesaparicion, String lugarDesaparicion, String llevaChip) {
		
		super(id, foto, nombreAnimal, especie, raza, sexo, telefono1, telefono2, correo, descripcion, tipoCartel);
		
		this.fechaDesaparicion = fechaDesaparicion;
		this.lugarDesaparicion = lugarDesaparicion;
		this.llevaChip = llevaChip;
		
	}

	public String getFechaDesaparicion() {
		return fechaDesaparicion;
	}

	public void setFechaDesaparicion(String fechaDesaparicion) {
		this.fechaDesaparicion = fechaDesaparicion;
	}

	public String getLugarDesaparicion() {
		return lugarDesaparicion;
	}

	public void setLugarDesaparicion(String lucgarDesaparicion) {
		this.lugarDesaparicion = lucgarDesaparicion;
	}

	public String getLlevaChip() {
		return llevaChip;
	}

	public void setLlevaChip(String llevaChip) {
		this.llevaChip = llevaChip;
	}
	
	
	
	
	

	
	
}
