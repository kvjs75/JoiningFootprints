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
								
									.requestMatchers("/ccs/**").permitAll()
									.requestMatchers("/js/**").permitAll()
									.requestMatchers("/img/**").permitAll()
									.requestMatchers("/bootstrap-5.3.2-dist/**").permitAll()
									.requestMatchers("/cropperjs-main/**").permitAll()
									.requestMatchers("/").permitAll()
									.requestMatchers("/index").permitAll()
									.requestMatchers("/masCarteles").permitAll()
									.requestMatchers("/registro").permitAll()
									.requestMatchers("/crearUsuario").permitAll()
									.requestMatchers("/login").permitAll()
									.requestMatchers("/publicar1_1").hasRole("usuario")
									.requestMatchers("/paso1_1").hasRole("usuario")
									.requestMatchers("/publicar1_2").hasRole("usuario")
									.requestMatchers("/paso1_2").hasRole("usuario")
									.requestMatchers("/publicar2_1").hasRole("usuario")
									.requestMatchers("/paso2_1").hasRole("usuario")
									.requestMatchers("/publicar2_2").hasRole("usuario")
									.requestMatchers("/paso2_2").hasRole("usuario")
									.requestMatchers("/publicacion").permitAll()
									.requestMatchers("/reportarCartel").hasRole("usuario")
									.requestMatchers("/comentarios").hasRole("usuario")
									.requestMatchers("/cajaComentarios").permitAll()
									.requestMatchers("/likeComentarios").permitAll()
									.requestMatchers("/reportarComentarios").hasRole("usuario")
									.requestMatchers("/borrarComentarios").hasRole("usuario")
									.requestMatchers("/comunidad").permitAll()
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
									.requestMatchers("/administrar").hasRole("administrador")
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
		consultasUsuarios consulta = new consultasUsuarios();
		ArrayList<usuario> usuarios = consulta.mostrarUsuarios();
		InMemory = new InMemoryUserDetailsManager();
		for(int i = 0;i<usuarios.size();i++) {
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
