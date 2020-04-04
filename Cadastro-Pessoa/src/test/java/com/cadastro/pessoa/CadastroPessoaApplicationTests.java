package com.cadastro.pessoa;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.constantes.Mensagens;
import com.cadastro.pessoa.controller.PessoaRestController;
import com.cadastro.pessoa.controller.response.Response;
import com.cadastro.pessoa.dao.PessoaRepository;
import com.cadastro.pessoa.enuns.Sexo;
import com.cadastro.pessoa.service.PessoaService;
import com.cadastro.pessoa.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = "server.port=4200")
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
	
	@BeforeEach
	public void init() {
		try {
			this.login_Usuario_Autenticado();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void acesso_Nao_Autenticado() throws Exception {
        this.mockMvc.perform(get("/api/v1/basicauth"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void login_Usuario_Autenticado() throws Exception {
        this.mockMvc.perform(get("/api/v1/basicauth").with(httpBasic("admin", "admin")))
                .andExpect(authenticated());
    }

    @Test
    public void login_Usuario_Invalido() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/pessoa/listar").with(httpBasic("invalido", "invalido")))
                .andExpect(unauthenticated())
                .andExpect(status().is4xxClientError())
                .andReturn();
        assertTrue(result.getResponse().getErrorMessage().contains(Unauthorized.class.getSimpleName()));
    }

	@Test
	public void pessoa_criado_com_sucesso() throws Exception {
			Pessoa pessoa = Pessoa.class.newInstance();
			pessoa.setNome("Cauã Otávio Teixeira");
			pessoa.setCpf("76051106006");
			LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			pessoa.setData_nascimento(data_nascimento);
			pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
			pessoa.setSexo(Sexo.MASCULINO);
			pessoa.setNacionalidade("Brasileiro");
			pessoa.setNaturalidade("Curitibano");

			String pessoaJson = Util.asJsonString(pessoa);
			
			ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
			Response<Pessoa> response = responseEntity.getBody();
			
			assertEquals(response.getMensagemSucesso(), Mensagens.PESSOA_CADASTRADA_COM_SUCESSO);
	}

	@Test
	public void nao_pode_criar_pessoa_com_cpf_existente() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("Cauã Otávio Teixeira");
		pessoa.setCpf("76051106006");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");

		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), Mensagens.JA_EXISTE_UMA_PESSOA_CADASTRADA_COM_ESSE_CPF_POR_FAVOR_TENTE_OUTRO);
	}
	
	@Test
	public void cpf_invalido() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("Cauã Otávio Teixeira");
		pessoa.setCpf("11111111111");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "Informe um CPF valido.");
	}
	
	@Test
	public void cpf_invalido_mais_11_numeros() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("Cauã Otávio Teixeira");
		pessoa.setCpf("111111111111");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "O CPF não pode ter mais de 11 numeros.");
	}
	
	@Test
	public void cpf_nulo() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("Cauã Otávio Teixeira");
		pessoa.setCpf(null);
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "O CPF deve ser preenchido");
	}
	
	@Test
	public void nome_nulo() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome(null);
		pessoa.setCpf("27743451038");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "Nome é obrigatorio.");
	}
	
	@Test
	public void nome_com_tamanho_maior_200_caracteres() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("Nada do que é social e humano é mais real que as utopias. Na sua vertente eutópica, as "
				+ "utopias constituíram sempre o fundamento simbólico e mítico sem o\n" + 
				" qual nenhuma forma de organização social se suuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "Nome não pode ter mais de 200 letras.");
	}
	
	@Test
	public void email_invalido() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeiratrevorh.com.br");
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "Informe um E-mail valido.");
	}
	
	@Test
	public void email_com_mais_de_200_caracteres() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeiracauaotavioteixeiracauaotavioteixeiracauaotavioteixeira"
				+ "cauaotavioteixeiracauaotavioteixeiracauaotavioteixeiracauaotavioteixeiracauaotavioteixeiracauaotaviot"
				+ "eixeiracauaotavioteixeiracauaotavioteixeiracauaotavioteixeira@gmail.com.br");
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(1), "O E-mail não pode ter mais de 200 letras.");
	}
	
	@Test
	public void data_nascimento_nao_pode_ser_nula() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		pessoa.setData_nascimento(null);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "A data de nascimento deve ser preenchida.");
	}
	
	@Test
	public void nacionalidade_com_mais_200_caracteres() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setNacionalidade("Nada do que é social e humano é mais real que as utopias. Na sua vertente eutópica, as \"\n" + 
				"				+ \"utopias constituíram sempre o fundamento simbólico e mítico sem o\\n\" + \n" + 
				"				\" qual nenhuma forma de organização social se suuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		pessoa.setNaturalidade("Curitibano");
		
		String pessoaJson = Util.asJsonString(pessoa);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "Nacionalidade não pode ter mais de 200 caracteres.");
	}
	
	@Test
	public void naturalidade_com_mais_200_caracteres() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setNacionalidade("qualquer uma");
		pessoa.setNaturalidade("Nada do que é social e humano é mais real que as utopias. Na sua vertente eutópica, as \\\"\\n\" + \n" + 
				"				\"				+ \\\"utopias constituíram sempre o fundamento simbólico e mítico sem o\\\\n\\\" + \\n\" + \n" + 
				"				\"				\\\" qual nenhuma forma de organização social se suuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();
		
		assertEquals(response.getErros().get(0), "Naturalidade não pode ter mais de 200 caracteres.");
	}
}
