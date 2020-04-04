package com.cadastro.pessoa;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.cadastro.pessoa.controller.PessoaRestController;
import com.cadastro.pessoa.dao.PessoaRepository;
import com.cadastro.pessoa.service.PessoaService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("unused")
public class CadastroPessoaApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PessoaRestController pessoaRestController;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	//@Test
	//@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
//	public void mytest1() throws Exception {
//		
////		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
////		requestParams.add("page", "0");
////		requestParams.add("count", "10");
//		Pessoa pessoa = Pessoa.class.newInstance();
//		pessoa.setNome("Cauã Otávio Teixeira");
//		pessoa.setCpf("09680312429");
//		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//		pessoa.setData_nascimento(data_nascimento);
//		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
//		pessoa.setSexo(Sexo.MASCULINO);
//		pessoa.setNacionalidade("Brasileiro");
//		pessoa.setNaturalidade("Curitibano");
//		
//		String pessoaJson = Util.asJsonString(pessoa);
//		
//	    mockMvc.perform(post("/api/pessoa/salvar/").param("pessoa", pessoaJson))
//	    	.andExpect(status().isOk());
//	}
	
	@Test
	public void acesso_Nao_Autenticado() throws Exception {
		this.mockMvc.perform(get("/api/v1/basicauth")).andExpect(status().isUnauthorized());
	}

	@Test
	public void login_Usuario_Autenticado() throws Exception {
		this.mockMvc.perform(get("/api/v1/basicauth").with(httpBasic("admin", "admin"))).andExpect(authenticated());
	}

	@Test
	public void login_Usuario_Invalido() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/api/pessoa/listar").with(httpBasic("invalido", "invalido")))
				.andExpect(unauthenticated()).andExpect(status().is4xxClientError()).andReturn();
		assertTrue(result.getResponse().getErrorMessage().contains(Unauthorized.class.getSimpleName()));
	}
}
