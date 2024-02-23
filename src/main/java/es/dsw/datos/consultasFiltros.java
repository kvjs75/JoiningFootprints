package es.dsw.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import es.dsw.connector.MySqlConnection;
import es.dsw.models.carteles;
import es.dsw.models.roles;
import es.dsw.models.usuario;

public class consultasFiltros {
	
	private MySqlConnection miConeccion;

	public consultasFiltros() {
		this.miConeccion = new MySqlConnection(false);
	}
	
	//NO SE LLEGO A USAR. PERO FUNCIONABA
	//pensado para un filtro avanzado.
	public ArrayList<carteles> mostrarFiltroCartelesDesaparicion(int cantidad, int omitir){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel, c.ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel,Recompensa,FechaDesaparicion\r\n"
					+ "FROM db_JNF.cartel a\r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario\r\n"
					+ "left JOIN db_JNF.cartel_desaparicion c\r\n"
					+ "ON a.ID_Cartel = c.ID_Cartel\r\n"
					+ "where TipoCartel='Desaparición' \r\n"
					+ "and not Fecha_Publicacion \r\n"
					+ "is null \r\n"
					+ "ORDER BY Fecha_Publicacion DESC \r\n" 
					+ "LIMIT "+cantidad+" OFFSET "+omitir+";";
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
	
	//1) Se utiliza para buscar carteles publicados en "últimos" mediante el nombre del animal
	public ArrayList<carteles> mostrarBusquedaCarteles(int cantidad, int omitir, String busqueda){
		ArrayList<carteles> carteles =new ArrayList<carteles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			//Consulta [Cartel-usuario]: Buscador de carteles 
			//Utiliza un comodin que muestra los datos de todos de los carteles que empiecen por lo que hayas escrito 
			String SQL = "SELECT Fecha_Publicacion,nick,Estado_Cartel, ID_Cartel,Fotografia,Nombre_Animal,Especie,Raza,Sexo,Telefono_Contacto1,Telefono_Contacto2,Email_Contacto,Descripcion,TipoCartel\r\n"
					+ "FROM db_JNF.cartel a\r\n"
					+ "right JOIN db_JNF.usuario b\r\n"
					+ "ON a.ID_Usuario = b.ID_Usuario\r\n"
					+ "where\r\n"
					+ "not Fecha_Publicacion is null\r\n"
					+ "and LOWER(Nombre_Animal) LIKE LOWER('"+busqueda+"%')\r\n"
					+ "ORDER BY Fecha_Publicacion DESC\r\n"
					+ "LIMIT "+cantidad+" OFFSET "+omitir+";";
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
					
					//formato fecha
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
	
	//Se utiliza para buscar los usuarios en administrador y comunidad mediante el nick del usuario
	public ArrayList<usuario> mostrarBusquedaPerfiles(String busqueda){
		ArrayList<usuario> usuarios =new ArrayList<usuario>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			//Buscador de usuarios mediante por el nick, utiliza un comodin que muestra los datos de todos los usuarios que empiecen por lo que hayas escrito 
			String SQL = "SELECT ID_Usuario,NombreUsuario,Nick,CorreoElectronico,Contrasena,FechaNacimiento,ZonaGeografica,PuntuacionHonor,NumLikes,NumCompartir,NumPenalizacion FROM db_JNF.usuario\r\n"
					+ "where LOWER(Nick) LIKE LOWER('"+busqueda+"%')";
			ResultSet resultado = miConeccion.executeSelect(SQL);
			
			try {
				while(resultado.next()) {
					usuario usuario =new usuario();
							usuario.setIdusuario(resultado.getInt("ID_Usuario"));
							usuario.setNombreUsuario(resultado.getNString("NombreUsuario"));
							usuario.setNick(resultado.getNString("Nick"));
							usuario.setCorreo(resultado.getNString("CorreoElectronico"));
							usuario.setContrasena(resultado.getNString("Contrasena"));
							usuario.setFechaNacimiento(resultado.getDate("FechaNacimiento").toString());
							usuario.setZonaGeografica(resultado.getNString("ZonaGeografica"));
							usuario.setPuntuacionHonor(resultado.getInt("PuntuacionHonor"));
							usuario.setNumLikes(resultado.getInt("NumLikes"));
							usuario.setNumCompartir(resultado.getInt("NumCompartir"));
							usuario.setNumPenalizaciones(resultado.getInt("NumPenalizacion"));
							usuarios.add(usuario);
							miConeccion.commit();
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//Consulta [Usuario-Rol]
			//Devuelve una lista de ID de usuario y el nombre de rol correspondiente para cada usuario que esté asociado con un rol. Tambien mostraria a los usuarios que tenga rol NULL.
			SQL = "SELECT ID_Usuario,NombreRol FROM db_JNF.rol a\r\n"
					+ "right JOIN db_JNF.usuariorol b\r\n"
					+ "ON a.RolID = b.RolID;";
			resultado = miConeccion.executeSelect(SQL);
			
			try {
				while(resultado.next()) {
					//Combrueba si el usuario existe en la consulta [Usuario-Rol]
					int encontrado = -1;
					for(int i=0;i<usuarios.size();i++) {
						if(usuarios.get(i).getIdusuario() == resultado.getInt("ID_Usuario")) {
							encontrado = i;
						}
					}
				
					//Si el usuario no existe en [Usuario-Rol]
					if(encontrado != -1) {
						//Si el usuario no tiene un rol.
						if(usuarios.get(encontrado).getRoles().equals("")) {
							//Se le asigna el primer ROL que hay en la tabla de ROL. Que seria "usuario".
							usuarios.get(encontrado).setRoles(resultado.getNString("NombreRol"));;
						//Si el usuario ya tenia un rol.
						}else {
							//Se le asigna los roles que tenia posteriormente y luego añadería el nuevo rol despues de una coma
							usuarios.get(encontrado).setRoles(usuarios.get(encontrado).getRoles()+","+resultado.getNString("NombreRol"));
						}
					}
					miConeccion.commit();
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
		miConeccion.close();
		return usuarios;
	}
	
	//Se utiliza para buscar los roles en administrador mediante el nombre del rol
	public ArrayList<roles> mostrarBusquedaRoles(String busqueda){
		ArrayList<roles> listaRoles =new ArrayList<roles>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			try {
				//Buscador de roles mediante por el nombre del rol, utiliza un comodin que muestra los datos de todos los roles que empiecen por lo que hayas escrito 
				String SQL = "SELECT RolID, NombreRol FROM db_jnf.rol where LOWER(NombreRol) LIKE LOWER('"+busqueda+"%');";
				ResultSet resultado = miConeccion.executeSelect(SQL);
				
				while(resultado.next()) {
					roles objRol = new roles();
					objRol.setIdRol(resultado.getInt("RolID"));
					objRol.setNombreRol(resultado.getNString("NombreRol"));
					
					listaRoles.add(objRol);
				}
				
				miConeccion.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				miConeccion.rollback();
			}
			finally {
				miConeccion.close();
			}
		}
		return listaRoles;
	}

}
