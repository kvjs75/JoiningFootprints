package es.dsw.datos;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import es.dsw.connector.MySqlConnection;
import es.dsw.models.comentarios;
import es.dsw.models.reporteCartel;
import es.dsw.models.reporteComentario;



public class consultasReportes {
	
	private MySqlConnection miConeccion;
	
	public consultasReportes() {
		this.miConeccion = new MySqlConnection(false);
	}
	
	//Se utiliza en publicacion para guardar los datos de reportes de cartel
	public void reportarCartel(reporteCartel reporteCartel) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				String SQL = "INSERT INTO db_jnf.reporte_cartel (ID_Usuario, ID_Cartel, motivo,estado)\r\n"
						+ "VALUES ("+reporteCartel.getIdUsuario()+","+reporteCartel.getIdCartel()+",'"+reporteCartel.getMotivo()+"','no resuelto');";
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
	
	//1) Se utiliza para mostrar al moderador los reportes de carteles
	//2) Se utiliza para mostrar al moderador un contador del numero de solicitudes que debe atender sobre los reportes de carteles
	public ArrayList<reporteCartel> mostrarReportarCartel() {
		ArrayList<reporteCartel> cartelesReportados = new ArrayList<reporteCartel>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				
				//consulta [Usuario(Denunciante)-cartel-Usuario(Denunciado)]
				//Muestra los datos necesario que debe ver el moderador (usuarios denunciante, cartel en cuestion, usuario denunciado... etc)
				String SQL = "SELECT ID_reporte,c.ID_Cartel,c.TipoCartel,c.Nombre_Animal,c.Fecha_Publicacion,b.Nick as denunciante,d.Nick as denunciado, motivo\r\n"
						+ "FROM db_jnf.reporte_cartel a\r\n"
						+ "left join db_jnf.usuario b\r\n"
						+ "on a.ID_usuario = b.ID_Usuario\r\n"
						+ "left join db_jnf.cartel c\r\n"
						+ "on a.ID_Cartel = c.ID_Cartel\r\n"
						+ "left join db_jnf.usuario d\r\n"
						+ "on d.ID_Usuario = c.ID_Usuario where estado = 'no resuelto'\r\n"
						+ "ORDER BY ID_reporte DESC";
				ResultSet resultado = miConeccion.executeSelect(SQL);
				
				while(resultado.next()){
					reporteCartel cartel = new reporteCartel();
					cartel.setIdReporte(resultado.getInt("ID_reporte"));
					cartel.setIdCartel(resultado.getInt("ID_Cartel"));
					cartel.setTipoCartel(resultado.getNString("TipoCartel"));
					cartel.setNombreAnimal(resultado.getNString("Nombre_Animal"));
					
					//formato fecha
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaPulicacion = LocalDate.parse(resultado.getDate("Fecha_Publicacion").toString());
					String fechaPublicacionFormateada = fechaPulicacion.format(formatoFecha);
					
					cartel.setFechaPublicacion(fechaPublicacionFormateada);
					cartel.setDenunciante(resultado.getNString("denunciante"));
					cartel.setDenunciado(resultado.getNString("denunciado"));
					cartel.setMotivo(resultado.getNString("motivo"));
					
					cartelesReportados.add(cartel);
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
		return cartelesReportados;
	}
	
	//Se utiliza en publicacion para guardar los datos de reportes de comentarios
	public void reportarComentario(reporteComentario reporteComentario) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				String SQL = "INSERT INTO db_jnf.reporte_comentario (ID_Usuario, ID_Comentario,motivo,estado)\r\n"
						+ "VALUES ("+reporteComentario.getIdUsuario()+","+reporteComentario.getIdComentario()+",'"+reporteComentario.getMotivo()+"','no resuelto');";
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
	
	//1) Se utiliza para mostrar al moderador los reportes de comentarios
	//2) Se utiliza para mostrar al moderador un contador del numero de solicitudes que debe atender sobre los reportes de comentarios
	public ArrayList<reporteComentario> mostrarReportarComentario() {
		ArrayList<reporteComentario> comentariosReportados = new ArrayList<reporteComentario>();
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				//consulta [Usuario(Denunciante)-Comentario-Usuario(Denunciado)-Cartel]
				//Muestra los datos necesario que debe ver el moderador (usuarios denunciante, comentario en cuestion, usuario denunciado, cartel en cuestion... etc)
				String SQL = "SELECT ID_reporte,e.ID_Cartel,e.TipoCartel,e.Nombre_Animal,e.Fecha_Publicacion,b.Nick as denunciante,d.Nick as denunciado, motivo, c.TextoComentario FROM db_jnf.reporte_comentario a\r\n"
						+ "left join db_jnf.usuario b\r\n"
						+ "on a.ID_usuario = b.ID_Usuario\r\n"
						+ "left join db_jnf.comentario c\r\n"
						+ "on a.ID_comentario = c.ID_Comentario\r\n"
						+ "left join db_jnf.usuario d\r\n"
						+ "on d.ID_Usuario = c.ID_Usuario\r\n"
						+ "left join db_jnf.cartel e\r\n"
						+ "on c.ID_Cartel = e.ID_Cartel where estado = 'no resuelto'\r\n"
						+ "ORDER BY ID_reporte DESC;";
				ResultSet resultado = miConeccion.executeSelect(SQL);
				
				while(resultado.next()){
					reporteComentario cartel = new reporteComentario();
					cartel.setIdReporte(resultado.getInt("ID_reporte"));
					cartel.setIdCartel(resultado.getInt("ID_Cartel"));
					cartel.setTipoCartel(resultado.getNString("TipoCartel"));
					cartel.setNombreAnimal(resultado.getNString("Nombre_Animal"));
					
					//formato fecha
					DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate fechaPulicacion = LocalDate.parse(resultado.getDate("Fecha_Publicacion").toString());
					String fechaPublicacionFormateada = fechaPulicacion.format(formatoFecha);
					
					cartel.setFechaPublicacion(fechaPublicacionFormateada);
					cartel.setDenunciante(resultado.getNString("denunciante"));
					cartel.setDenunciado(resultado.getNString("denunciado"));
					cartel.setMotivo(resultado.getNString("motivo"));
					cartel.setComentario(resultado.getNString("TextoComentario"));
					
					comentariosReportados.add(cartel);
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
		return comentariosReportados;
	}
	
	
	//Se utiliza para que el moderador pueda marcar la solicitud de denuncia a cartel como resuelta
	public void marcarReporteResueltoCartel(int idReporteCartel) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				//Actualiza el estado del reporte a resulto
				String SQL = "UPDATE db_jnf.reporte_cartel\r\n"
							  + "SET estado = 'resuelto'\r\n"
						      + "WHERE ID_Reporte = "+idReporteCartel+"; ";
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
	
	//Se utiliza para que el moderador pueda marcar la solicitud de denuncia a comentario como resuelta
	public void marcarReporteResueltoComentario(int idReporteComentario) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			
			try {
				//Actualiza el estado del reporte a resulto
				String SQL = "UPDATE db_jnf.reporte_comentario\r\n"
							  + "SET estado = 'resuelto'\r\n"
						      + "WHERE ID_Reporte = "+idReporteComentario+"; ";
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
