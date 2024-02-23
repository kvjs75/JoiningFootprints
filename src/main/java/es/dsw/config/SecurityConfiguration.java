package es.dsw.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import es.dsw.datos.consultasUsuarios;
import es.dsw.models.usuario;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	public static InMemoryUserDetailsManager InMemory;
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
					
									//Tecnologias
									.requestMatchers("/ccs/**").permitAll()
									.requestMatchers("/js/**").permitAll()
									.requestMatchers("/img/**").permitAll()
									.requestMatchers("/bootstrap-5.3.2-dist/**").permitAll()
									.requestMatchers("/cropperjs-main/**").permitAll()
									
									//Index
									.requestMatchers("/").permitAll()
									.requestMatchers("/index").permitAll()
									.requestMatchers("/masCarteles").permitAll()
									
									//Registro
									.requestMatchers("/registro").permitAll()
									.requestMatchers("/crearUsuario").permitAll()
									
									//Login
									.requestMatchers("/login").permitAll()
									
									//Publicar
									.requestMatchers("/publicar1_1").hasRole("usuario")
									.requestMatchers("/paso1_1").hasRole("usuario")
									.requestMatchers("/publicar1_2").hasRole("usuario")
									.requestMatchers("/paso1_2").hasRole("usuario")
									.requestMatchers("/publicar2_1").hasRole("usuario")
									.requestMatchers("/paso2_1").hasRole("usuario")
									.requestMatchers("/publicar2_2").hasRole("usuario")
									.requestMatchers("/paso2_2").hasRole("usuario")
									.requestMatchers("/publicar_confirmacion").hasRole("usuario")
									
									//Publicacion
									.requestMatchers("/publicacion").permitAll()
									.requestMatchers("/reportarCartel").hasRole("usuario")
									.requestMatchers("/comentarios").hasRole("usuario")
									.requestMatchers("/cajaComentarios").permitAll()
									.requestMatchers("/likeComentarios").permitAll()
									.requestMatchers("/reportarComentarios").hasRole("usuario")
									.requestMatchers("/borrarComentarios").hasRole("usuario")
									
									//Comunidad
									.requestMatchers("/comunidad").permitAll()
									.requestMatchers("/masPerfiles").permitAll()
									
									//Perfil
									.requestMatchers("/perfil").permitAll()
									.requestMatchers("/resolverCartel").hasRole("usuario")
									.requestMatchers("/noResolverCartel").hasRole("usuario")
									.requestMatchers("/borrarCartelesActuales").hasRole("usuario")
									.requestMatchers("/borrarCartelesResueltos").hasRole("usuario")
									.requestMatchers("/contadorCartelesActuales").permitAll()
									.requestMatchers("/contadorCartelesResueltos").permitAll()
									.requestMatchers("/contadorCartelesPendientes").permitAll()
									.requestMatchers("/cartelesActuales").permitAll()
									.requestMatchers("/cartelesResueltos").permitAll()
									.requestMatchers("/cartelesPendientes").permitAll()
									
									//Administrar
									.requestMatchers("/administrar").hasRole("administrador")
									.requestMatchers("/masUsuarios").hasRole("administrador")
									.requestMatchers("/borrarUsuarios").hasRole("administrador")
									.requestMatchers("/masRoles").hasRole("administrador")
									.requestMatchers("/crearRol").hasRole("administrador")
									.requestMatchers("/borrarRol").hasRole("administrador")
									
									//Moderar
									.requestMatchers("/moderar").hasRole("moderador")
									.requestMatchers("/contadorCartelesPendienteModerador").hasRole("moderador")
									.requestMatchers("/mostarCartelesPendientes").hasRole("moderador")
									.requestMatchers("/aprobarCartel").hasRole("moderador")
									.requestMatchers("/rechazarCartel").hasRole("moderador")
									.requestMatchers("/contadorCartelesDenunciadoModerador").hasRole("moderador")
									.requestMatchers("/mostrarCartelesDenunciado").hasRole("moderador")
									.requestMatchers("/marcarReporteResueltoCartel").hasRole("moderador")
									.requestMatchers("/contadorComentarioDenunciadoModerador").hasRole("moderador")
									.requestMatchers("/mostrarComentarioDenunciado").hasRole("moderador")
									.requestMatchers("/marcarReporteResueltoComentario").hasRole("moderador")
									.requestMatchers("/borrarPublicacionModerador").hasRole("moderador")
									
									//Resto de las paginas. Solo se pueden acceder estando autenticado
									.anyRequest().authenticated()
									)
			.httpBasic(withDefaults())
			.formLogin(form -> form
								.loginPage("/login")
								.loginProcessingUrl("/logginprocess")
								.permitAll()
					)
			.logout((logout) -> logout.logoutSuccessUrl("/").permitAll());
		return http.build();
		
	}
	
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		//Al inicializar la aplicacion se registran los usuarios ya existentes desde la base de datos
		consultasUsuarios consulta = new consultasUsuarios();
		//Se crea una array rellenada con los datos de la tabla de usuarios.
		ArrayList<usuario> usuarios = consulta.mostrarUsuarios();
		
		
		InMemory = new InMemoryUserDetailsManager();
		
		//Se utiliza dicha array para añadir cada dato en "InMemory"  
		for(int i = 0;i<usuarios.size();i++) {
			//divide cada rol con una coma.
			String[] roles = usuarios.get(i).getRoles().split(",");
			@SuppressWarnings("deprecation")
			UserDetails user = User.withDefaultPasswordEncoder()
									.username(usuarios.get(i).getNombreUsuario())
									.password(usuarios.get(i).getContrasena())
									.roles(roles)
									.build();
			InMemory.createUser(user);
		}
		

		return InMemory;
	}
}
