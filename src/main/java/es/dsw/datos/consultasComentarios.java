package es.dsw.datos;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import es.dsw.connector.MySqlConnection;
import es.dsw.models.comentarios;

public class consultasComentarios {
	
	private MySqlConnection miConeccion;

	public consultasComentarios() {
		this.miConeccion = new MySqlConnection(false);
	}
	
	
	public void insertarComentario(String nombreUsuario, int idCartel, comentarios objComentarios) {
		int idUsuario = -1;
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				String SQL = "SELECT ID_Usuario from db_jnf.Usuario where nombreUsuario = '"+nombreUsuario+"'"; 
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()){
					idUsuario = resultado.getInt("ID_Usuario");
				}
				
				SQL = "INSERT INTO db_jnf.Comentario (ID_Usuario, ID_Cartel, TextoComentario)\r\n"
						+ "VALUES ("+idUsuario+","+idCartel+",'"+objComentarios.getTextoComentario()+"');";
				
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
	
	public ArrayList<comentarios> mostrarComentario(int idCartel) {
		ArrayList<comentarios> listaComentarios = new ArrayList<comentarios>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			try {
				String SQL = "SELECT ID_Comentario,TextoComentario,FechaComentario,nick,Likes FROM db_jnf.comentario a\r\n"
							  + "LEFT JOIN db_jnf.usuario b\r\n"
							  + "ON a.ID_Usuario=b.ID_Usuario WHERE ID_Cartel="+idCartel+" ORDER BY FechaComentario DESC;";
				
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()){
					comentarios objComentario = new comentarios();
					objComentario.setIdComentario(resultado.getInt("ID_Comentario"));
					objComentario.setTextoComentario(resultado.getNString("TextoComentario"));
					
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaComentario = LocalDate.parse(resultado.getDate("FechaComentario").toString());
					String fechaPublicacionFormateada = fechaComentario.format(formatoFecha);
					
					objComentario.setFechaPublicacion(fechaPublicacionFormateada);
					objComentario.setNick(resultado.getNString("nick"));
					objComentario.setLikes(resultado.getInt("Likes"));
					
					listaComentarios.add(objComentario);
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
		return listaComentarios;
	}
	
	
	public void borrarComentario(int idComentario, String nick) {
		int likes = 0;
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				String SQL="SELECT Likes FROM db_jnf.comentario where ID_Comentario="+idComentario+";";
				
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()) {
					likes = resultado.getInt("Likes");
				}
				
				SQL = "UPDATE db_jnf.usuario\r\n"
						+ "SET NumLikes = NumLikes - "+likes+"\r\n"
						+ "WHERE nick = '"+nick+"';";
				miConeccion.executeUpdateOrDelete(SQL);
				
				SQL = "DELETE FROM db_jnf.comentario WHERE ID_Comentario = "+idComentario+";"; 
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
	
	
	public void likesComentarios(int idComentario, String nombreUsuario, String nick) {
		Boolean existe = false;
		int idUsuario = -1;
		
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				String SQL = "SELECT NombreUsuario, B.ID_Usuario, ID_Comentario, ID_LikeComentario \r\n"
							  + "FROM db_jnf.LikeComentario as A \r\n"
							  + "LEFT JOIN db_jnf.usuario as B \r\n"
							  + "ON A.ID_Usuario = B.ID_Usuario \r\n"
							  + "WHERE NombreUsuario = '"+nombreUsuario+"' AND ID_Comentario = "+idComentario+"; "; 
				
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()) {
					existe = true;
					idUsuario = resultado.getInt("ID_Usuario");
				}
				
				if(existe == false) {
					SQL = "SELECT ID_Usuario FROM db_jnf.usuario WHERE NombreUsuario='"+nombreUsuario+"';";
					
					resultado = miConeccion.executeSelect(SQL);
					
					while(resultado.next()) {
						idUsuario = resultado.getInt("ID_Usuario");
					}
					
					
					
					SQL = "INSERT INTO db_jnf.LikeComentario (ID_Usuario, ID_Comentario) \r\n"
						   + "VALUES ("+idUsuario+", "+idComentario+");";
					miConeccion.executeInsert(SQL);
					
					SQL = "UPDATE db_jnf.Comentario\r\n"
						   + "SET Likes = Likes + 1\r\n"
						   + "WHERE ID_Comentario = "+idComentario+";";
					miConeccion.executeUpdateOrDelete(SQL);
					
					SQL = "UPDATE db_jnf.usuario\r\n"
						   + "SET NumLikes = NumLikes + 1\r\n"
						   + "WHERE nick = '"+nick+"';";
					miConeccion.executeUpdateOrDelete(SQL);
					
				}else {
					SQL = "DELETE FROM db_jnf.LikeComentario WHERE ID_Usuario = "+idUsuario+" AND ID_Comentario = "+idComentario+";";
					miConeccion.executeUpdateOrDelete(SQL);
					
					SQL = "UPDATE db_jnf.Comentario\r\n"
						   + "SET Likes = Likes - 1\r\n"
						   + "WHERE ID_Comentario = "+idComentario+";";
					miConeccion.executeUpdateOrDelete(SQL);
					
					SQL = "UPDATE db_jnf.usuario\r\n"
						   + "SET NumLikes = NumLikes - 1\r\n"
						   + "WHERE nick = '"+nick+"';";
					miConeccion.executeUpdateOrDelete(SQL);
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
		
	}
}
