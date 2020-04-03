package com.cadastro.pessoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class CadastroPessoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroPessoaApplication.class, args);
	}

}
