package com.example.productapp;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;

@SpringBootApplication
public class ProductAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAppApplication.class, args);
	}
}

@Controller
class ProductController {

//	@GetMapping(path = "/products")
//	public String getProducts(Model model){
//		model.addAttribute("products", Arrays.asList("iPad","iPhone","iPod"));
//		return "products";
//	}

	@GetMapping(path = "/products")
	public String getProducts(HttpServletRequest request,  Model model){
        KeycloakAuthenticationToken userPrincipal = (KeycloakAuthenticationToken)request.getUserPrincipal();
        OidcKeycloakAccount account = userPrincipal.getAccount();
        KeycloakSecurityContext keycloakSecurityContext = account.getKeycloakSecurityContext();
        IDToken idToken = keycloakSecurityContext.getIdToken();
        AdapterDeploymentContext attribute = (AdapterDeploymentContext) request.getServletContext().getAttribute(AdapterDeploymentContext.class.getName());
//		KeycloakPrincipal<> principal = ((KeycloakAuthenticationToken) userPrincipal).getPrincipal();
		model.addAttribute("products", Arrays.asList("iPad","iPhone","iPod"));
		model.addAttribute("token", idToken.getEmail());
		return "products";
	}

	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "/";
	}
}
