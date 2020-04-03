package com.cadastro.pessoa;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.controller.PessoaRestController;
import com.cadastro.pessoa.dao.PessoaRepository;
import com.cadastro.pessoa.enuns.Sexo;
import com.cadastro.pessoa.service.PessoaService;
import com.cadastro.pessoa.util.Util;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@SuppressWarnings("unused")
class CadastroPessoaApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PessoaRestController pessoaRestController;

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	//@Test
    public void acesso_Protegido() throws Exception {
        this.mockMvc.perform(get("/api"))
                .andExpect(status().isUnauthorized());
    }

    //@Test
    public void login_Usuario() throws Exception {
        this.mockMvc.perform(get("/api/pessoa/listar").with(httpBasic("admin", "admin")))
                .andExpect(authenticated());
    }

    //@Test
    public void login_Usuario_Invalido() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/pessoa/listar").with(httpBasic("invalido", "invalido")))
                .andExpect(unauthenticated())
                .andExpect(status().is4xxClientError())
                .andReturn();
        assertTrue(result.getResponse().getErrorMessage().contains(Unauthorized.class.getSimpleName()));
    }

	@Test
	void pessoa_criado_com_sucesso() {
		
		try {
			Pessoa pessoa = Pessoa.class.newInstance();
			pessoa.setNome("Cauã Otávio Teixeira");
			pessoa.setCpf("85639888598");
			pessoa.setData_nascimento(LocalDate.parse("27/07/1955", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
			pessoa.setSexo(Sexo.MASCULINO);
			pessoa.setNacionalidade("Brasileiro");
			pessoa.setNaturalidade("Curitibano");

			String pessoaJson = Util.asJsonString(pessoa);
			mockMvc.perform(post("/api/pessoa/salvar").with(httpBasic("admin", "admin"))
					.content(pessoaJson)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.mensagemSucesso").value("Pessoa cadastrada com sucesso!"));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
