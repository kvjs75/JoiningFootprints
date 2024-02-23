package es.dsw.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import es.dsw.connector.MySqlConnection;
import es.dsw.models.usuario;

public class consultasUsuarios {
	private MySqlConnection miConeccion;

	public consultasUsuarios() {
		this.miConeccion = new MySqlConnection(false);
	}
	
	//1)Se uiliza en registo. Para registrar al usuario para la proxima vez que haga login.
	public void crearUsuario(usuario usuario) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			//[Transaccion]
			
			try {
				//Insertar al usuario a la base de datos
				String SQLinsertUsuario = "INSERT INTO db_JNF.usuario (ID_Usuario,NombreUsuario,Nick,CorreoElectronico,Contrasena,FechaNacimiento,ZonaGeografica,FechaRegistro,PuntuacionHonor,NumLikes,NumCompartir,NumPenalizacion) \r\n"
						+ "VALUES (null,'"+usuario.getNombreUsuario()+"','"+usuario.getNick()+"','"+usuario.getCorreo()+"','"+usuario.getContrasena()+"','"+usuario.getFechaNacimiento()+"','"+usuario.getZonaGeografica()+"',current_timestamp(),0,0,0,0);";
				miConeccion.executeInsert(SQLinsertUsuario);
				
				//Encontrar la id del anterior usuario insertado
				//Coge la ID del ultimo usuario de la tabla de usuarios
				String SQLselectId = "SELECT ID_Usuario FROM db_JNF.usuario ORDER BY FechaRegistro DESC LIMIT 1;";
				ResultSet usuarioInsertado = miConeccion.executeSelect(SQLselectId);
				
				int idUsuario=-1;
				
				try {
					while(usuarioInsertado.next()) {
						//Se guarda ID del usuario en una variable
						idUsuario=usuarioInsertado.getInt("ID_Usuario");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Insertar la relacion entre el usuario y su rol basico llamodo "usuario"
				String SQLinsertRol = "INSERT INTO db_JNF.usuariorol (ID_Usuario,RolID) \r\n"
							+ "VALUES ("+idUsuario+",1);";
				miConeccion.executeInsert(SQLinsertRol);
				
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
	
	//1) Se utiliza para asignar a los usuarios con sus respectivos roles (se usa principalmente para inicializar a los usuarios en el modulo de seguridad)
	//2) Se utiliza para ver los usuarios en comunidad y administrador
	//3) Se utiliza para el control de errores de usuario. Para evaluar si algun dato esta repetido
	public ArrayList<usuario> mostrarUsuarios(){
		ArrayList<usuario> usuarios =new ArrayList<usuario>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "\r\n"
					+ "SELECT ID_Usuario,NombreUsuario,Nick,CorreoElectronico,Contrasena,FechaNacimiento,ZonaGeografica,PuntuacionHonor,NumLikes,NumCompartir,NumPenalizacion FROM db_JNF.usuario;";
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
							usuarios.get(encontrado).setRoles(resultado.getNString("NombreRol"));
						//Si el usuario ya tenua un rol.
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
	
	
	//1) Se utiliza para saber quien es el usuario que se esta haciendo uso actualmente. Se emplea por ejemplo el uso de poder borrar sus propios carteles
	public usuario comprobarTuPerfil(String nombreUsuario){
		usuario usuario = null;
		miConeccion.open();
		if(!miConeccion.isError()) {
			String SQL = "SELECT ID_Usuario,NombreUsuario,Nick,CorreoElectronico,Contrasena,FechaNacimiento,ZonaGeografica,FechaRegistro,PuntuacionHonor,NumLikes,NumCompartir,NumPenalizacion\r\n"
					+ "FROM db_jnf.usuario\r\n"
					+ "where NombreUsuario='"+nombreUsuario+"';";
			ResultSet resultado = miConeccion.executeSelect(SQL);
			
			try {
				//Esta pensado para solo usar el nick. Lo de mas esta pensado por si se haria un uso en el futuro
				while(resultado.next()) {
							usuario = new usuario();
							usuario.setIdusuario(resultado.getInt("ID_Usuario"));
							usuario.setNombreUsuario(resultado.getNString("NombreUsuario"));
							usuario.setNick(resultado.getNString("Nick"));
							usuario.setCorreo(resultado.getNString("CorreoElectronico"));
							usuario.setContrasena(resultado.getNString("Contrasena"));
							usuario.setFechaNacimiento(resultado.getDate("FechaNacimiento").toString());
							usuario.setZonaGeografica(resultado.getNString("ZonaGeografica"));
							
							//formato fecha
							DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							LocalDate fechaRegistro = LocalDate.parse(resultado.getDate("FechaRegistro").toString());
							String fechaRegistroFormateada = fechaRegistro.format(formatoFecha);
							
							usuario.setFechaRegistro(fechaRegistroFormateada);
							usuario.setPuntuacionHonor(resultado.getInt("PuntuacionHonor"));
							usuario.setNumLikes(resultado.getInt("NumLikes"));
							usuario.setNumCompartir(resultado.getInt("NumCompartir"));
							usuario.setNumPenalizaciones(resultado.getInt("NumPenalizacion"));
							
							miConeccion.commit();
				}
				
				//Consulta [Usuario-Rol]
				//Devuelve una lista de ID de usuario y el nombre de rol correspondiente para cada usuario que esté asociado con un rol. Tambien mostraria a los usuarios que tenga rol NULL.
				SQL = "SELECT ID_Usuario,NombreRol FROM db_JNF.rol a\r\n"
						+ "right JOIN db_JNF.usuariorol b\r\n"
						+ "ON a.RolID = b.RolID;";
				
				resultado = miConeccion.executeSelect(SQL);
	
				//Si el usuario existe
				if(usuario != null) {
					
					while(resultado.next()) {
						//Si la ID del usuario es la misma que la ID usuario de la consulta [Usuario-Rol].
						if(resultado.getInt("ID_Usuario") == usuario.getIdusuario()) {
							//Si el usuario no tiene un rol.
							if(usuario.getRoles().equals("")) {
								//Se le asigna el primer ROL que hay en la tabla de ROL. Que seria "usuario".
								usuario.setRoles(resultado.getNString("NombreRol"));;
							}else {
								//Se le asigna los roles que tenia posteriormente y luego añadería el nuevo rol despues de una coma.
								usuario.setRoles(usuario.getRoles()+","+resultado.getNString("NombreRol"));
							}
						}
					}
				}
				
			miConeccion.commit();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return usuario;
	}
	
	//Se utiliza para mostrar los datos en los perfieles de usuarios
	public usuario mostrarPerfilNick(String nick){
		usuario usuario =new usuario();
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			//Mediante por el nick pasado por paramentro se obtiene todos los datos de ese usuario para mostrarlo en el perfil
			String SQL = "SELECT ID_Usuario,NombreUsuario,Nick,CorreoElectronico,Contrasena,FechaNacimiento,ZonaGeografica,FechaRegistro,PuntuacionHonor,NumLikes,NumCompartir,NumPenalizacion\r\n"
					+ "FROM db_jnf.usuario\r\n"
					+ "where nick='"+nick+"';";
			ResultSet resultado = miConeccion.executeSelect(SQL);
			
			try {
				while(resultado.next()) {
							usuario.setIdusuario(resultado.getInt("ID_Usuario"));
							usuario.setNombreUsuario(resultado.getNString("NombreUsuario"));
							usuario.setNick(resultado.getNString("Nick"));
							usuario.setCorreo(resultado.getNString("CorreoElectronico"));
							usuario.setContrasena(resultado.getNString("Contrasena"));
							usuario.setFechaNacimiento(resultado.getDate("FechaNacimiento").toString());
							usuario.setZonaGeografica(resultado.getNString("ZonaGeografica"));
							
							//formato fecha
							DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							LocalDate fechaRegistro = LocalDate.parse(resultado.getDate("FechaRegistro").toString());
							String fechaRegistroFormateada = fechaRegistro.format(formatoFecha);
							
							usuario.setFechaRegistro(fechaRegistroFormateada);
							usuario.setPuntuacionHonor(resultado.getInt("PuntuacionHonor"));
							usuario.setNumLikes(resultado.getInt("NumLikes"));
							usuario.setNumCompartir(resultado.getInt("NumCompartir"));
							usuario.setNumPenalizaciones(resultado.getInt("NumPenalizacion"));
							miConeccion.commit();
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return usuario;
	}
	
	//Lo utiliza el administrador para borrar a los usuarios
	public void borrarUsuario(int id) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			try {
				
				String SQL = "delete from db_jnf.usuario where ID_Usuario = "+id+";";
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
	
}
