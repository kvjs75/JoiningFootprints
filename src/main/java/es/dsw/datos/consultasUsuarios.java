package es.dsw.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.dsw.connector.MySqlConnection;
import es.dsw.models.usuario;

public class consultasUsuarios {
	private MySqlConnection miConeccion;

	public consultasUsuarios() {
		this.miConeccion = new MySqlConnection(false);
	}
	
	
	public void crearUsuario(usuario usuario) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			
			
			//esta es toda la transaccion
			try {
				//Insertar al usuario a la base de datos
				String SQLinsertUsuario = "INSERT INTO db_JNF.usuario (ID_Usuario,NombreUsuario,Nick,CorreoElectronico,Contrasena,FechaNacimiento,ZonaGeografica,FechaRegistro,PuntuacionHonor,NumLikes,NumCompartir,NumPenalizacion) \r\n"
						+ "VALUES (null,'"+usuario.getNombreUsuario()+"','"+usuario.getNick()+"','"+usuario.getCorreo()+"','"+usuario.getContrasena()+"','"+usuario.getFechaNacimiento()+"','"+usuario.getZonaGeografica()+"',current_timestamp(),0,0,0,0);";
				miConeccion.executeInsert(SQLinsertUsuario);
				
				//Encontrar la id del anterior usuario insertado
				String SQLselectId = "SELECT ID_Usuario FROM db_JNF.usuario ORDER BY FechaRegistro DESC LIMIT 1;";
				ResultSet usuarioInsertado = miConeccion.executeSelect(SQLselectId);
				int idUsuario=-1;
				
				try {
					while(usuarioInsertado.next()) {
						idUsuario=usuarioInsertado.getInt("ID_Usuario");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Insertar la relacion entre el usuario y su rol basico llamod "usuario"
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
	
	//asignar a los usuarios con sus respectivos roles (se usa principalmente para inicializar a los usuarios en el modulo de seguridad)
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
			SQL = "SELECT ID_Usuario,NombreRol FROM db_JNF.rol a\r\n"
					+ "right JOIN db_JNF.usuariorol b\r\n"
					+ "ON a.RolID = b.RolID;";
			resultado = miConeccion.executeSelect(SQL);
			
			try {
				while(resultado.next()) {
					int encontrado = -1;
					for(int i=0;i<usuarios.size();i++) {
						if(usuarios.get(i).getIdusuario() == resultado.getInt("ID_Usuario")) {
							encontrado = i;
						}
					}
					if(encontrado != -1) {
						if(usuarios.get(encontrado).getRoles().equals("")) {
							usuarios.get(encontrado).setRoles(resultado.getNString("NombreRol"));;
						}else {
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
	
}
