package com.cadastro.pessoa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.constantes.Mensagens;
import com.cadastro.pessoa.controller.response.Response;
import com.cadastro.pessoa.dao.PessoaRepository;
import com.cadastro.pessoa.enuns.Sexo;
import com.cadastro.pessoa.enuns.Uf;

@SuppressWarnings({ "rawtypes", "unused" })
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class PessoaRestControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    int randomServerPort;
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Before
    public void setUp() {
    	pessoaRepository.deleteAll();
    	
    	Pessoa p = new Pessoa("Dona Herminia", Sexo.FEMININO, "hermina@gmail.com", LocalDate.of(2020, 04, 04), "Teste",
				"Teste", "82658989083", "Avenida Juiz Marco Túlio Isaac", "Laranjeiras", "Betim", "32676358",
				Uf.MINAS_GERAIS, 170);
    	p.setData_cadastro(new Date());
    	pessoaRepository.save(p);
    }
    
	@Test
	public void acesso_nao_autenticado() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/basicauth";
		URI uri = new URI(baseUrl);
		ResponseEntity<StatusResultMatchers> responseEntity = this.restTemplate.getForEntity(uri,
				StatusResultMatchers.class);

		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}

  	@Test
  	public void login_usuario_autenticado() throws Exception {
  		
  		final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/basicauth";
		URI uri = new URI(baseUrl);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");  

        HttpEntity<Pessoa> request = new HttpEntity<>(headers);
		
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  	}
  	
  	@Test
  	public void login_usuario_invalido() throws Exception {
  		
  		final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/basicauth";
  		URI uri = new URI(baseUrl);
  		
  		HttpHeaders headers = new HttpHeaders();
  		headers.setBasicAuth("invalido", "invalido");  
  		
  		HttpEntity<Pessoa> request = new HttpEntity<>(headers);
  		
  		ResponseEntity<String> responseEntity = this.restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
  		
  		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  	}

    @Test
    public void test_salvar_pessoa_rest_controller() throws URISyntaxException {
        final String baseUrl = "http://localhost:"+randomServerPort+"/api/pessoa/salvar";
        URI uri = new URI(baseUrl);
        Pessoa p = new Pessoa("Dona Herminia", Sexo.FEMININO, "hermina@gmail.com", LocalDate.of(2020, 04, 04), "Teste",
				"Teste", "35517459005", "Avenida Juiz Marco Túlio Isaac", "Laranjeiras", "Betim", "32676358",
				Uf.MINAS_GERAIS, 170);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");  

        HttpEntity<Pessoa> request = new HttpEntity<>(p, headers);
        
        ResponseEntity<Response> result = this.restTemplate.postForEntity(uri, request, Response.class);
        Response response = result.getBody();
        Assert.assertEquals(Mensagens.PESSOA_CADASTRADA_COM_SUCESSO, response.getMensagemSucesso());
    }
    
	@Test
    public void test_alterar_pessoa_rest_controller() throws URISyntaxException {
    	
    	Pessoa pessoa = pessoaRepository.findPessoaByCpf("82658989083");
    	pessoa.setNome("Maria José");
    	
        final String baseUrl = "http://localhost:"+randomServerPort+"/api/pessoa/atualizar";
        URI uri = new URI(baseUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");  

        HttpEntity<Pessoa> request = new HttpEntity<>(pessoa, headers);
        
        ResponseEntity<Response> responseEntity = this.restTemplate.exchange(uri, HttpMethod.PUT, request, Response.class);
        Response response = responseEntity.getBody();
        String mensagemSucesso = response.getMensagemSucesso();
        
        Assert.assertEquals(Mensagens.PESSOA_ALTERADA_COM_SUCESSO, response.getMensagemSucesso());
    }
	
	@Test
	public void test_excluir_pessoa_rest_controller() throws URISyntaxException {

		String url = "http://localhost:" + randomServerPort + "/api/pessoa/deletar/{id}";

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("admin", "admin");

		HttpEntity<Pessoa> request = new HttpEntity<>(headers);

		Pessoa pessoa = pessoaRepository.findPessoaByCpf("82658989083");

		restTemplate.delete(url, pessoa.getId());

		ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, Response.class,
				pessoa.getId());

		Response response = responseEntity.getBody();
		String mensagemSucesso = response.getMensagemSucesso();
		assertEquals(Mensagens.PESSOA_EXCLUIDA_COM_SUCESSO, mensagemSucesso);
	}
	
	@Test
	public void test_pesquisar_pessoa_por_id_rest_controller() throws URISyntaxException {
		
		String url = "http://localhost:" + randomServerPort + "/api/pessoa/pesquisar/{id}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("admin", "admin");  
		
		HttpEntity<Pessoa> request = new HttpEntity<>(headers);
		
		Pessoa pessoa = pessoaRepository.findPessoaByCpf("82658989083");
		
		ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Response.class,
				pessoa.getId());
		
		Response response = responseEntity.getBody();
		String mensagemSucesso = response.getMensagemSucesso();
		assertEquals(Mensagens.PESSOA_ENCONTRADA, mensagemSucesso);
	}
	
	@Test
	public void test_listar_pessoas_rest_controller() throws URISyntaxException {
		
		String url = "http://localhost:" + randomServerPort + "/api/pessoa/listar/{page}/{count}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("admin", "admin");  
		
		HttpEntity<Pessoa> request = new HttpEntity<>(headers);
		
		Map<String,Integer> params = new HashMap<>();
		params.put("page", 0);
		params.put("count", 10);
		
		ParameterizedTypeReference<Response<Page<List<Pessoa>>>> parameterizedTypeReference = 
				new ParameterizedTypeReference<Response<Page<List<Pessoa>>>>(){};
		
		ResponseEntity<Response<Page<List<Pessoa>>>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, parameterizedTypeReference, params);
		Response<Page<List<Pessoa>>> response = responseEntity.getBody();
		HttpStatus status = response.getHttpStatus();
		assertEquals(HttpStatus.OK, status);
	}
}
