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
	
	public ArrayList<carteles> mostrarCartelesGeneral(int cantidad){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where not Fecha_Publicacion is null AND Aprobacion = 'Aprobado' ORDER BY Fecha_Publicacion DESC LIMIT "+cantidad+";";
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
	
	public ArrayList<carteles> mostrarCartelesGeneral(int cantidad, int omitir){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where not Fecha_Publicacion is null AND Aprobacion = 'Aprobado' ORDER BY Fecha_Publicacion DESC LIMIT "+cantidad+" OFFSET "+omitir+";";
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
				
				String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,a.ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel,Vacunado,Esterilizado,Desparasitado,Requisitos,Entrevista,Fecha_Nacimiento\r\n"
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
					
					cartelAdopcion.setFechaNacimiento(resultado.getDate("Fecha_Nacimiento").toString());
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
				SQL = "INSERT INTO db_jnf.Cartel_Desaparicion (ID_Cartel, FechaDesaparicion, LugarDesaparicion, Recompensa)\r\n"
							+ "VALUES\r\n"
							+ "("+idCartel+", '"+objCartelDesaparicion.getFechaDesaparicion()+"', '"+objCartelDesaparicion.getLugarDesaparicion()+"', "+objCartelDesaparicion.getRecompensa()+");";
				
				
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
	
	
	
	public void insertarAdopcion (String nombre, cartelAdopcion objCartelAdopcion) {
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
						+ "("+idUsuario+", '"+objCartelAdopcion.getFoto()+"', '"+objCartelAdopcion.getNombreAnimal()+"', '"+objCartelAdopcion.getEspecie()+"', '"+objCartelAdopcion.getRaza()+"', '"+objCartelAdopcion.getSexo()+"', '"+objCartelAdopcion.getTelefono1()+"', '"+objCartelAdopcion.getTelefono2()+"', '"+objCartelAdopcion.getCorreo()+"', '"+objCartelAdopcion.getDescripcion()+"', 'Adopción', 'Pendiente', 0)";
				miConeccion.executeInsert(SQL);
				
				SQL = "SELECT ID_Cartel FROM db_JNF.cartel ORDER BY Fecha_Publicacion DESC LIMIT 1;";
				resultado = miConeccion.executeSelect(SQL);
				
				while(resultado.next()) {
					idCartel=resultado.getInt("ID_Cartel");
				}
				
				SQL = "INSERT INTO Cartel_Adopcion (ID_Cartel, Fecha_Nacimiento, Vacunado, Esterilizado, Desparasitado, Requisitos, Entrevista)\r\n"
					   + "VALUES ("+idCartel+", '"+objCartelAdopcion.getFechaNacimiento()+"', "+objCartelAdopcion.getVacunado()+", "+objCartelAdopcion.getEsterilizado()+", "+objCartelAdopcion.getDesparasitado()+", '"+objCartelAdopcion.getRequisitos()+"', "+objCartelAdopcion.getEntrevista()+");";
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
	
	
	public ArrayList<carteles> mostrarCartelesPerfilNickActuales(String nick){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where nick='"+nick+"' and not Fecha_Publicacion is null and Aprobacion = 'Aprobado' and Estado_Cartel = 0  ORDER BY Fecha_Publicacion DESC;";
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
	
	public ArrayList<carteles> mostrarCartelesPerfilNickPendientes(String nick){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where nick='"+nick+"' and not Fecha_Publicacion is null and Aprobacion = 'Pendiente' ORDER BY Fecha_Publicacion DESC;";
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
	
	//Consultas del moderador
	
	public ArrayList<carteles> mostrarCartelesGeneralModerador(){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel\r\n"
					+ "FROM db_JNF.cartel a\r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where not Fecha_Publicacion is null AND Aprobacion = 'Pendiente' ORDER BY Fecha_Publicacion DESC";
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
	
	public void aprobarCartel (int idCartel) {
		miConeccion.open();

		if(!miConeccion.isError()) {
			try {
				String SQL="UPDATE db_jnf.cartel\r\n"
						+ "SET Aprobacion = 'Aprobado',\r\n"
						+ "Fecha_Publicacion = CURRENT_TIMESTAMP\r\n"
						+ "WHERE ID_Cartel = "+idCartel+";";
				miConeccion.executeUpdateOrDelete(SQL);
				
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
	
	public void rechazarCartel(int idCartel) {
		miConeccion.open();

		if(!miConeccion.isError()) {
			try {
				String SQL="DELETE FROM db_jnf.cartel WHERE ID_Cartel="+idCartel+";";
				miConeccion.executeUpdateOrDelete(SQL);
				
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
	
	//Perfil
	
	public void resolverCartel(int idCartel) {
		miConeccion.open();

		if(!miConeccion.isError()) {
			try {
				String SQL="UPDATE db_jnf.cartel\r\n"
							+ "SET Estado_Cartel = 1\r\n"
							+ "WHERE ID_Cartel = "+idCartel+";";
				miConeccion.executeUpdateOrDelete(SQL);
				
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
	
	public void noResolverCartel(int idCartel) {
		miConeccion.open();

		if(!miConeccion.isError()) {
			try {
				String SQL="UPDATE db_jnf.cartel\r\n"
							+ "SET Estado_Cartel = 0\r\n"
							+ "WHERE ID_Cartel = "+idCartel+";";
				miConeccion.executeUpdateOrDelete(SQL);
				
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
	
	public ArrayList<carteles> mostrarCartelesPerfilNickResueltos(String nick){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel,ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel \r\n"
					+ "FROM db_JNF.cartel a \r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario where nick='"+nick+"' and not Fecha_Publicacion is null and Aprobacion = 'Aprobado' and Estado_Cartel = 1  ORDER BY Fecha_Publicacion DESC;";
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
