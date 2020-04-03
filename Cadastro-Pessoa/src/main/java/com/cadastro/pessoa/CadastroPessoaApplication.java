package com.cadastro.pessoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CadastroPessoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroPessoaApplication.class, args);
	}

}
