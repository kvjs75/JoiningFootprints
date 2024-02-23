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
	
	//Se utiliza para insertar los datos de comentarios y relacionarlo con el usuario quien haya realizado dicho comentario en la publicación
	public void insertarComentario(String nombreUsuario, int idCartel, comentarios objComentarios) {
		int idUsuario = -1;
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			//[Tranccion]
			
			try {
				//Con el nombre del usuario pasado por parametro se hace una consulta para detectar cual es su id para luego poder relacionarlo
				String SQL = "SELECT ID_Usuario from db_jnf.Usuario where nombreUsuario = '"+nombreUsuario+"'"; 
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()){
					//Se guarda dicha ID en una variable
					idUsuario = resultado.getInt("ID_Usuario");
				}
				
				//Se inserta los datos de comentarios en su respectiva tabla y se utiliza la ID del usuario para relacionar quien ha hecho ese comentario
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
	
	//Se utiliza para mostrar todos los comentarios asociado a un cartel mediante con una subvista en publicacion
	public ArrayList<comentarios> mostrarComentario(int idCartel) {
		ArrayList<comentarios> listaComentarios = new ArrayList<comentarios>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			try {
				//con la idCartel pasado por parametro se obtiene una lista de todos los comentarios asociados a dicho a cartel y ordenado descendentemente
				String SQL = "SELECT ID_Comentario,TextoComentario,FechaComentario,nick,Likes FROM db_jnf.comentario a\r\n"
							  + "LEFT JOIN db_jnf.usuario b\r\n"
							  + "ON a.ID_Usuario=b.ID_Usuario WHERE ID_Cartel="+idCartel+" ORDER BY FechaComentario DESC;";
				
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()){
					comentarios objComentario = new comentarios();
					objComentario.setIdComentario(resultado.getInt("ID_Comentario"));
					objComentario.setTextoComentario(resultado.getNString("TextoComentario"));
					
					//formato fecha
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
	
	//Se utiliza para borrar los comentarios en las publicaciones de los carteles
	public void borrarComentario(int idComentario, String nick) {
		int likes = 0;
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			//[Tranccion]
			
			try {
				//Primero, se averigua el numero de "me gusta" que tenia ese comentario
				String SQL="SELECT Likes FROM db_jnf.comentario where ID_Comentario="+idComentario+";";
				
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()) {
					//se almacena ese numero en la variable
					likes = resultado.getInt("Likes");
				}
				
				//luego, se actualiza el NumLikes para quitarle los "me gustan" al propietario del comentario
				SQL = "UPDATE db_jnf.usuario\r\n"
						+ "SET NumLikes = NumLikes - "+likes+"\r\n"
						+ "WHERE nick = '"+nick+"';";
				miConeccion.executeUpdateOrDelete(SQL);
				
				//Por ultimo, se elimina el comentario
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
	
	//Se utiliza para dar o quitar "me gusta" en los comentarios de publicacion
	public void likesComentarios(int idComentario, String nombreUsuario, String nick) {
		Boolean existe = false;
		int idUsuario = -1;
		
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			//[Tranccion]
			
			try {
				//consulta [LikeComentario-usuario] 
				//Primero mediante esta consulta se comprueba si este usuario ya ha dado a "me gusta" a este comentario. 
				//Si sus datos existen en esta tabla asociado a dicha ID de comentario, se entiende de que ya le ha dado un "me gusta", y sino, no. 
				String SQL = "SELECT NombreUsuario, B.ID_Usuario, ID_Comentario, ID_LikeComentario \r\n"
							  + "FROM db_jnf.LikeComentario as A \r\n"
							  + "LEFT JOIN db_jnf.usuario as B \r\n"
							  + "ON A.ID_Usuario = B.ID_Usuario \r\n"
							  + "WHERE NombreUsuario = '"+nombreUsuario+"' AND ID_Comentario = "+idComentario+"; "; 
				
				ResultSet resultado = miConeccion.executeSelect(SQL);
				while(resultado.next()) {
					//Si resuelta de que existe, significa que si le ha dado un "me gusta" a ese comentario
					existe = true;
					//Si existe se guarda su ID en la variable
					idUsuario = resultado.getInt("ID_Usuario");
				}
				
				//Pero si no existe en la tabla, sigfica que es la primera vez que le dio un "me gusta"
				if(existe == false) {
					//Entonces, mediante el nombreUsuario pasado por parametro se localiza la ID del usuario
					SQL = "SELECT ID_Usuario FROM db_jnf.usuario WHERE NombreUsuario='"+nombreUsuario+"';";
					
					resultado = miConeccion.executeSelect(SQL);
					
					while(resultado.next()) {
						//Se guarda dicha ID en una variable
						idUsuario = resultado.getInt("ID_Usuario");
					}
					
					//Lo inserta en la tabla "LikeComentario" para saber que le dio "me gusta" al comentario
					SQL = "INSERT INTO db_jnf.LikeComentario (ID_Usuario, ID_Comentario) \r\n"
						   + "VALUES ("+idUsuario+", "+idComentario+");";
					miConeccion.executeInsert(SQL);
					
					//Luego se incrementa el contador Likes del comentario
					SQL = "UPDATE db_jnf.Comentario\r\n"
						   + "SET Likes = Likes + 1\r\n"
						   + "WHERE ID_Comentario = "+idComentario+";";
					miConeccion.executeUpdateOrDelete(SQL);
					
					//Y tambien de incrementa el contador de NumLikes del propio usuario quien recibio el "me gusta"
					SQL = "UPDATE db_jnf.usuario\r\n"
						   + "SET NumLikes = NumLikes + 1,\r\n"
						   + "PuntuacionHonor = PuntuacionHonor + 1\r\n"
						   + "WHERE nick = '"+nick+"';";
					miConeccion.executeUpdateOrDelete(SQL);
				
				//Pero si resulta que ese usuario ya le habia dado antes "me gusta" a ese comentario. Al entrar de nuevo lo que hara es deshacerlo todo.
				}else {
					//Se elimina el usuario quien ha dado "me gusta" a ese comentario en la tabla de "LikeComentario"
					SQL = "DELETE FROM db_jnf.LikeComentario WHERE ID_Usuario = "+idUsuario+" AND ID_Comentario = "+idComentario+";";
					miConeccion.executeUpdateOrDelete(SQL);
					
					//Luego se descrementa al contador Likes del comentario
					SQL = "UPDATE db_jnf.Comentario\r\n"
						   + "SET Likes = Likes - 1\r\n"
						   + "WHERE ID_Comentario = "+idComentario+";";
					miConeccion.executeUpdateOrDelete(SQL);
					
					//Y tambien se descrementa al contador de NumLikes del propio usuario quien habia recibido ese "me gusta"
					SQL = "UPDATE db_jnf.usuario\r\n"
						   + "SET NumLikes = NumLikes - 1,\r\n"
						   + "PuntuacionHonor = PuntuacionHonor - 1\r\n"
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
