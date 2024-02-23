package es.dsw.datos;

import java.sql.ResultSet;
import java.util.ArrayList;

import es.dsw.connector.MySqlConnection;
import es.dsw.models.roles;

public class consultasRoles {
	
	private MySqlConnection miConeccion;

	public consultasRoles() {
		this.miConeccion = new MySqlConnection(false);
	}
	
	//Muestra todos roles existentes al administrador
	public ArrayList<roles> mostrarRoles() {
		miConeccion.open();
		ArrayList<roles> listaRoles = new ArrayList<roles>();
		if(!miConeccion.isError()) {
			try {
				String SQL = "SELECT RolID, NombreRol FROM db_jnf.rol;";
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
	
	//Se utiliza para que el administrado pueda crear un nuevo rol
	public void crearRol(String nombreRol) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			try {
				String SQL = "INSERT INTO db_jnf.rol (NombreRol) values ('"+nombreRol+"');";
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
	
	//Se utiliza para que el administradorp ueda borrar un rol
	public void borrarRol(int idRol) {
		miConeccion.open();
		if(!miConeccion.isError()) {
			try {
				String SQL = "DELETE FROM db_jnf.rol WHERE RolID = "+idRol+";";
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
