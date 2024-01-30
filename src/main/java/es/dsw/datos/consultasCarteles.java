package es.dsw.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
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
			String SQL = "\r\n"
					+ "SELECT ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel FROM db_JNF.cartel;";
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
			String SQL = "\r\n"
					+ "SELECT ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel FROM db_JNF.cartel where TipoCartel='Desaparicion';";
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
			String SQL = "\r\n"
					+ "SELECT ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel FROM db_JNF.cartel where TipoCartel='Adopcion';";
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
				
				String SQL = "SELECT a.ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel,FechaDesaparicion,LugarDesaparicion,Recompensa\r\n"
						+ "FROM db_JNF.cartel_desaparicion a\r\n"
						+ "left JOIN db_JNF.cartel b\r\n"
						+ "ON a.ID_Cartel = b.ID_Cartel\r\n"
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
					cartelDesaparicion.setFechaDesaparicion(resultado.getDate("FechaDesaparicion").toString());
					cartelDesaparicion.setLugarDesaparicion(resultado.getNString("LugarDesaparicion"));
					if(resultado.getBoolean("LlevaChip")==true) {
						cartelDesaparicion.setLlevaChip("si");
					}
					else {
						cartelDesaparicion.setLlevaChip("no");
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
				
				String SQL = "SELECT a.ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel,Vacunado,Esterilizado,Desparasitado,Requisitos,Entrevista\r\n"
						+ "FROM db_JNF.cartel_adopcion a\r\n"
						+ "left JOIN db_JNF.cartel b\r\n"
						+ "ON a.ID_Cartel = b.ID_Cartel\r\n"
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
	
	
}
