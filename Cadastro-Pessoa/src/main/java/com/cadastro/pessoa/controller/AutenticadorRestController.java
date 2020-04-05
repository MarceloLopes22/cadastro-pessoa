package com.cadastro.pessoa.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.pessoa.basica.Autenticador;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1")
public class AutenticadorRestController {

	@GetMapping(path = "/basicauth")
	public Autenticador basicauth() {
		return new Autenticador("Você está autenticado!");
	}
}
