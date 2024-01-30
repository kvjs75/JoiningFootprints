package es.dsw.controllers;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.dsw.config.SecurityConfiguration;
import es.dsw.datos.consultasCarteles;
import es.dsw.datos.consultasUsuarios;
import es.dsw.models.cartelDesaparicion;
import es.dsw.models.cartelAdopcion;
import es.dsw.models.carteles;
import es.dsw.models.controlErroresUsuarios;
import es.dsw.models.usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	
}


