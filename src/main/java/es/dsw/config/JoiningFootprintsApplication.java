package es.dsw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan({"es.dsw"})
public class JoiningFootprintsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoiningFootprintsApplication.class, args);
	}
	
	@Value("${ruta.cartelesDesapareciones}")
	private String rutaDirectorio;
	
	@Value("${ruta.cartelesAdopciones}")
	private String rutaDirectorio2;
	
	
	@Configuration
    public class MvcConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/carteles/desaparicion/**")
                    .addResourceLocations("file:"+rutaDirectorio);
            
            registry.addResourceHandler("/img/carteles/adopcion/**")
            		.addResourceLocations("file:"+rutaDirectorio2);
        }
        
    }

}
