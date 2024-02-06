package es.dsw.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import es.dsw.connector.MySqlConnection;
import es.dsw.models.cartelAdopcion;
import es.dsw.models.cartelDesaparicion;
import es.dsw.models.carteles;




public class consultasCarteles {
	
	private MySqlConnection miConeccion;

	public consultasCarteles() {
		this.miConeccion = new MySqlConnection(false);
	}
	
	public ArrayList<carteles> mostrarCartelesGeneral(){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where not Fecha_Publicacion is null ORDER BY Fecha_Publicacion DESC;";
			ResultSet resultado = miConeccion.executeSelect(SQL);
			
			try {
				while(resultado.next()) {
					carteles cartel = new carteles();
					cartel.setId(resultado.getInt("ID_Cartel"));
					cartel.setFoto(resultado.getNString("Fotografia"));
					cartel.setNombreAnimal(resultado.getNString("Nombre_Animal"));
					cartel.setEspecie(resultado.getNString("Especie"));
					cartel.setRaza(resultado.getNString("Raza"));
					cartel.setSexo(resultado.getNString("Sexo"));
					cartel.setTelefono1(resultado.getNString("Telefono_Contacto1"));
					cartel.setTelefono2(resultado.getNString("Telefono_Contacto2"));
					cartel.setCorreo(resultado.getNString("Email_Contacto"));
					cartel.setDescripcion(resultado.getNString("Descripcion"));
					cartel.setTipoCartel(resultado.getNString("TipoCartel"));
					
					if(resultado.getBoolean("Estado_Cartel")==true) {
						cartel.setEstadoCartel("resuelto");
					}
					else {
						cartel.setEstadoCartel("no resuelto");
					}
					
					cartel.setNick(resultado.getNString("nick"));
					
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaPulicacion = LocalDate.parse(resultado.getDate("Fecha_Publicacion").toString());
					String fechaPublicacionFormateada = fechaPulicacion.format(formatoFecha);
					
					cartel.setFechaPublicacion(fechaPublicacionFormateada);
					carteles.add(cartel);
					miConeccion.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		miConeccion.close();
		return carteles;
	}
	
	public ArrayList<carteles> mostrarCartelesDesaparicion(){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where TipoCartel='Desaparición' and not Fecha_Publicacion is null  ORDER BY Fecha_Publicacion DESC;";
			ResultSet resultado = miConeccion.executeSelect(SQL);
			
			try {
				while(resultado.next()) {
					carteles cartel = new carteles();
					cartel.setId(resultado.getInt("ID_Cartel"));
					cartel.setFoto(resultado.getNString("Fotografia"));
					cartel.setNombreAnimal(resultado.getNString("Nombre_Animal"));
					cartel.setEspecie(resultado.getNString("Especie"));
					cartel.setRaza(resultado.getNString("Raza"));
					cartel.setSexo(resultado.getNString("Sexo"));
					cartel.setTelefono1(resultado.getNString("Telefono_Contacto1"));
					cartel.setTelefono2(resultado.getNString("Telefono_Contacto2"));
					cartel.setCorreo(resultado.getNString("Email_Contacto"));
					cartel.setDescripcion(resultado.getNString("Descripcion"));
					cartel.setTipoCartel(resultado.getNString("TipoCartel"));
					
					if(resultado.getBoolean("Estado_Cartel")==true) {
						cartel.setEstadoCartel("resuelto");
					}
					else {
						cartel.setEstadoCartel("no resuelto");
					}
					
					cartel.setNick(resultado.getNString("nick"));
					
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaPulicacion = LocalDate.parse(resultado.getDate("Fecha_Publicacion").toString());
					String fechaPublicacionFormateada = fechaPulicacion.format(formatoFecha);
					
					cartel.setFechaPublicacion(fechaPublicacionFormateada);
					carteles.add(cartel);
					miConeccion.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		miConeccion.close();
		return carteles;
	}
	
	public ArrayList<carteles> mostrarCartelesAdopcion(){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where TipoCartel='Adopcion' and not Fecha_Publicacion is null  ORDER BY Fecha_Publicacion DESC;";
			ResultSet resultado = miConeccion.executeSelect(SQL);
			
			try {
				while(resultado.next()) {
					carteles cartel = new carteles();
					cartel.setId(resultado.getInt("ID_Cartel"));
					cartel.setFoto(resultado.getNString("Fotografia"));
					cartel.setNombreAnimal(resultado.getNString("Nombre_Animal"));
					cartel.setEspecie(resultado.getNString("Especie"));
					cartel.setRaza(resultado.getNString("Raza"));
					cartel.setSexo(resultado.getNString("Sexo"));
					cartel.setTelefono1(resultado.getNString("Telefono_Contacto1"));
					cartel.setTelefono2(resultado.getNString("Telefono_Contacto2"));
					cartel.setCorreo(resultado.getNString("Email_Contacto"));
					cartel.setDescripcion(resultado.getNString("Descripcion"));
					cartel.setTipoCartel(resultado.getNString("TipoCartel"));
					
					if(resultado.getBoolean("Estado_Cartel")==true) {
						cartel.setEstadoCartel("resuelto");
					}
					else {
						cartel.setEstadoCartel("no resuelto");
					}
					
					cartel.setNick(resultado.getNString("nick"));
					
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaPulicacion = LocalDate.parse(resultado.getDate("Fecha_Publicacion").toString());
					String fechaPublicacionFormateada = fechaPulicacion.format(formatoFecha);
					
					cartel.setFechaPublicacion(fechaPublicacionFormateada);
					carteles.add(cartel);
					miConeccion.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		miConeccion.close();
		return carteles;
	}
	
	public String detectarTipoCartel(int ID_cartel){
		miConeccion.open();
		String tipoCartel="";
		if(!miConeccion.isError()) {
			try {
				
				String SQL = "SELECT TipoCartel FROM db_JNF.cartel where ID_Cartel="+ID_cartel+";";
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()) {
					tipoCartel=resultado.getNString("TipoCartel");
				}
				
				miConeccion.commit();
				
			}catch (Exception e){
				miConeccion.rollback();
			}finally {
				miConeccion.close();
			}
			
		}
		
		return tipoCartel;
	}
	
	public cartelDesaparicion mostrarDatosDesaparicion(int ID_cartel){
		miConeccion.open();
		cartelDesaparicion cartelDesaparicion = null;
		if(!miConeccion.isError()) {
			try {
				
				String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,a.ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel,FechaDesaparicion,LugarDesaparicion,Recompensa\r\n"
						+ "FROM db_JNF.cartel_desaparicion a\r\n"
						+ "left JOIN db_JNF.cartel b\r\n"
						+ "ON a.ID_Cartel = b.ID_Cartel\r\n"
						+ "right JOIN db_JNF.usuario c\r\n"
						+ "ON b.ID_Usuario = c.ID_Usuario\r\n"
						+ "where a.ID_Cartel="+ID_cartel+";";
				
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()) {
					cartelDesaparicion = new cartelDesaparicion();
					cartelDesaparicion.setId(resultado.getInt("ID_Cartel"));
					cartelDesaparicion.setFoto(resultado.getNString("Fotografia"));
					cartelDesaparicion.setNombreAnimal(resultado.getNString("Nombre_Animal"));
					cartelDesaparicion.setEspecie(resultado.getNString("Especie"));
					cartelDesaparicion.setRaza(resultado.getNString("Raza"));
					cartelDesaparicion.setSexo(resultado.getNString("Sexo"));
					cartelDesaparicion.setTelefono1(resultado.getNString("Telefono_Contacto1"));
					cartelDesaparicion.setTelefono2(resultado.getNString("Telefono_Contacto2"));
					cartelDesaparicion.setCorreo(resultado.getNString("Email_Contacto"));
					cartelDesaparicion.setDescripcion(resultado.getNString("Descripcion"));
					cartelDesaparicion.setTipoCartel(resultado.getNString("TipoCartel"));
					if(resultado.getBoolean("Estado_Cartel")==true) {
						cartelDesaparicion.setEstadoCartel("resuelto");
					}
					else {
						cartelDesaparicion.setEstadoCartel("no resuelto");
					}
					
					cartelDesaparicion.setNick(resultado.getNString("nick"));
					
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaPulicacion = LocalDate.parse(resultado.getDate("Fecha_Publicacion").toString());
					String fechaPublicacionFormateada = fechaPulicacion.format(formatoFecha);
					
					cartelDesaparicion.setFechaPublicacion(fechaPublicacionFormateada);
					cartelDesaparicion.setFechaDesaparicion(resultado.getDate("FechaDesaparicion").toString());
					cartelDesaparicion.setLugarDesaparicion(resultado.getNString("LugarDesaparicion"));
					if(resultado.getBoolean("Recompensa")==true) {
						cartelDesaparicion.setRecompensa("si");
					}
					else {
						cartelDesaparicion.setRecompensa("no");
					}
				}
				
				miConeccion.commit();
				
			}catch (Exception e){
				miConeccion.rollback();
			}finally {
				miConeccion.close();
			}
			
		}
		
		return cartelDesaparicion;
	}
	
	public cartelAdopcion mostrarDatosAdopcion(int ID_cartel){
		miConeccion.open();
		cartelAdopcion cartelAdopcion = null;
		if(!miConeccion.isError()) {
			try {
				
				String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,a.ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel,Vacunado,Esterilizado,Desparasitado,Requisitos,Entrevista\r\n"
						+ "FROM db_JNF.cartel_adopcion a\r\n"
						+ "left JOIN db_JNF.cartel b\r\n"
						+ "ON a.ID_Cartel = b.ID_Cartel\r\n"
						+ "right JOIN db_JNF.usuario c\r\n"
						+ "ON b.ID_Usuario = c.ID_Usuario\r\n"
						+ "where a.ID_Cartel="+ID_cartel+";";
				
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()) {
					cartelAdopcion = new cartelAdopcion();
					cartelAdopcion.setId(resultado.getInt("ID_Cartel"));
					cartelAdopcion.setFoto(resultado.getNString("Fotografia"));
					cartelAdopcion.setNombreAnimal(resultado.getNString("Nombre_Animal"));
					cartelAdopcion.setEspecie(resultado.getNString("Especie"));
					cartelAdopcion.setRaza(resultado.getNString("Raza"));
					cartelAdopcion.setSexo(resultado.getNString("Sexo"));
					cartelAdopcion.setTelefono1(resultado.getNString("Telefono_Contacto1"));
					cartelAdopcion.setTelefono2(resultado.getNString("Telefono_Contacto2"));
					cartelAdopcion.setCorreo(resultado.getNString("Email_Contacto"));
					cartelAdopcion.setDescripcion(resultado.getNString("Descripcion"));
					cartelAdopcion.setTipoCartel(resultado.getNString("TipoCartel"));
					if(resultado.getBoolean("Estado_Cartel")==true) {
						cartelAdopcion.setEstadoCartel("resuelto");
					}
					else {
						cartelAdopcion.setEstadoCartel("no resuelto");
					}
					
					cartelAdopcion.setNick(resultado.getNString("nick"));
					
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaPulicacion = LocalDate.parse(resultado.getDate("Fecha_Publicacion").toString());
					String fechaPublicacionFormateada = fechaPulicacion.format(formatoFecha);
					
					cartelAdopcion.setFechaPublicacion(fechaPublicacionFormateada);
					if(resultado.getBoolean("Vacunado") == true) {
						cartelAdopcion.setVacunado("si");
					}else {
						cartelAdopcion.setVacunado("no");
					}
					
					if(resultado.getBoolean("Esterilizado") == true) {
						cartelAdopcion.setEsterilizado("si");
					}else {
						cartelAdopcion.setEsterilizado("no");
					}
					
					if(resultado.getBoolean("Desparasitado") == true) {
						cartelAdopcion.setDesparasitado("si");
					}else {
						cartelAdopcion.setDesparasitado("no");
					}
					
					cartelAdopcion.setRequisitos(resultado.getNString("Requisitos"));
					
					if(resultado.getBoolean("Entrevista") == true) {
						cartelAdopcion.setEntrevista("si");
					}else {
						cartelAdopcion.setEntrevista("no");
					}
					
					
				}
				
				miConeccion.commit();
				
			}catch (Exception e){
				miConeccion.rollback();
			}finally {
				miConeccion.close();
			}
			
		}
		
		return cartelAdopcion;
	}
	
	
	public void insertarDesaparicion (String nombre, cartelDesaparicion objCartelDesaparicion) {
		miConeccion.open();
		int idUsuario= -1;
		int idCartel = -1;
		if(!miConeccion.isError()) {
			
			try {
				String SQL = "SELECT ID_Usuario FROM db_JNF.usuario WHERE NombreUsuario='"+nombre+"'";
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()) {
					idUsuario=resultado.getInt("ID_Usuario");
				}
				
				SQL = "INSERT INTO db_jnf.Cartel (ID_Usuario, Fotografia, Nombre_Animal, Especie, Raza, Sexo, Telefono_Contacto1, Telefono_Contacto2, Email_Contacto, Descripcion, TipoCartel, Aprobacion, Estado_Cartel)\r\n"
						+ "VALUES\r\n"
						+ "("+idUsuario+", '"+objCartelDesaparicion.getFoto()+"', '"+objCartelDesaparicion.getNombreAnimal()+"', '"+objCartelDesaparicion.getEspecie()+"', '"+objCartelDesaparicion.getRaza()+"', '"+objCartelDesaparicion.getSexo()+"', '"+objCartelDesaparicion.getTelefono1()+"', '"+objCartelDesaparicion.getTelefono2()+"', '"+objCartelDesaparicion.getCorreo()+"', '"+objCartelDesaparicion.getDescripcion()+"', 'Desaparición', 'Pendiente', 0)";
				miConeccion.executeInsert(SQL);
				
				SQL = "SELECT ID_Cartel FROM db_JNF.cartel ORDER BY Fecha_Publicacion DESC LIMIT 1;";
				resultado = miConeccion.executeSelect(SQL);
				
				while(resultado.next()) {
					idCartel=resultado.getInt("ID_Cartel");
				}
				
				if(objCartelDesaparicion.getRecompensa().equals("si")) {
					SQL = "INSERT INTO db_jnf.Cartel_Desaparicion (ID_Cartel, FechaDesaparicion, LugarDesaparicion, Recompensa)\r\n"
							+ "VALUES\r\n"
							+ "("+idCartel+", '"+objCartelDesaparicion.getFechaDesaparicion()+"', '"+objCartelDesaparicion.getLugarDesaparicion()+"', 1);";
				}else {
					SQL = "INSERT INTO db_jnf.Cartel_Desaparicion (ID_Cartel, FechaDesaparicion, LugarDesaparicion, Recompensa)\r\n"
							+ "VALUES\r\n"
							+ "("+idCartel+", '"+objCartelDesaparicion.getFechaDesaparicion()+"', '"+objCartelDesaparicion.getLugarDesaparicion()+"', 0);";
				}
				
				miConeccion.executeInsert(SQL);
				
				miConeccion.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				miConeccion.rollback();
			}
			finally {
				miConeccion.close();
			}
		}
	}
	
	public ArrayList<carteles> mostrarCartelesPerfilNick(String nick){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where nick='"+nick+"' and not Fecha_Publicacion is null ORDER BY Fecha_Publicacion DESC;";
			ResultSet resultado = miConeccion.executeSelect(SQL);
			
			try {
				while(resultado.next()) {
					carteles cartel = new carteles();
					cartel.setId(resultado.getInt("ID_Cartel"));
					cartel.setFoto(resultado.getNString("Fotografia"));
					cartel.setNombreAnimal(resultado.getNString("Nombre_Animal"));
					cartel.setEspecie(resultado.getNString("Especie"));
					cartel.setRaza(resultado.getNString("Raza"));
					cartel.setSexo(resultado.getNString("Sexo"));
					cartel.setTelefono1(resultado.getNString("Telefono_Contacto1"));
					cartel.setTelefono2(resultado.getNString("Telefono_Contacto2"));
					cartel.setCorreo(resultado.getNString("Email_Contacto"));
					cartel.setDescripcion(resultado.getNString("Descripcion"));
					cartel.setTipoCartel(resultado.getNString("TipoCartel"));
					
					if(resultado.getBoolean("Estado_Cartel")==true) {
						cartel.setEstadoCartel("resuelto");
					}
					else {
						cartel.setEstadoCartel("no resuelto");
					}
					
					cartel.setNick(resultado.getNString("nick"));
					
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaPulicacion = LocalDate.parse(resultado.getDate("Fecha_Publicacion").toString());
					String fechaPublicacionFormateada = fechaPulicacion.format(formatoFecha);
					
					cartel.setFechaPublicacion(fechaPublicacionFormateada);
					carteles.add(cartel);
					miConeccion.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		miConeccion.close();
		return carteles;
	}
}
