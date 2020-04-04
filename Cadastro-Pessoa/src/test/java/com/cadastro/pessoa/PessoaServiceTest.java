package com.cadastro.pessoa;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.constantes.Mensagens;
import com.cadastro.pessoa.controller.response.Response;
import com.cadastro.pessoa.dao.PessoaRepository;
import com.cadastro.pessoa.enuns.Sexo;
import com.cadastro.pessoa.service.PessoaService;
import com.cadastro.pessoa.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("unused")
public class PessoaServiceTest {
	
	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Before
    public void setUp() {
    	pessoaRepository.deleteAll();
    	
    	Pessoa p = new Pessoa("Dona Herminia", Sexo.FEMININO, "hermina@gmail.com", LocalDate.of(2020, 04, 04), "Teste",
				"Teste", "76051106006");
    	
    	pessoaRepository.save(p);
    }

	@Test
	public void pessoa_criado_com_sucesso_inclusao() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("Cauã Otávio Teixeira");
		pessoa.setCpf("82658989083");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setSexo(Sexo.MASCULINO);
		pessoa.setNacionalidade("Brasileiro");
		pessoa.setNaturalidade("Curitibano");

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.PESSOA_CADASTRADA_COM_SUCESSO, response.getMensagemSucesso());
	}

	@Test
	public void nao_pode_criar_pessoa_com_cpf_existente_inclusao() throws Exception {
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

		assertEquals(response.getErros().get(0),
				Mensagens.JA_EXISTE_UMA_PESSOA_CADASTRADA_COM_ESSE_CPF_POR_FAVOR_TENTE_OUTRO);
	}

	@Test
	public void cpf_invalido_inclusao() throws Exception {
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
	public void cpf_invalido_mais_11_numeros_inclusao() throws Exception {
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

		String mensagemErro = null;
		for (String mensagem : response.getErros()) {
			if (mensagem.equalsIgnoreCase("O CPF não pode ter mais de 11 numeros.")) {
				assertEquals(mensagem, "O CPF não pode ter mais de 11 numeros.");
				break;
			}
		}
	}

	@Test
	public void cpf_nulo_inclusao() throws Exception {
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
	public void nome_nulo_inclusao() throws Exception {
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
	public void nome_com_tamanho_maior_200_caracteres_inclusao() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("Nada do que é social e humano é mais real que as utopias. Na sua vertente eutópica, as "
				+ "utopias constituíram sempre o fundamento simbólico e mítico sem o\n"
				+ " qual nenhuma forma de organização social se suuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
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
	public void email_invalido_inclusao() throws Exception {
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
	public void email_com_mais_de_200_caracteres_inclusao() throws Exception {
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

		String mensagemErro = null;
		for (String mensagem : response.getErros()) {
			if (mensagem.equalsIgnoreCase("O E-mail não pode ter mais de 200 letras.")) {
				assertEquals(mensagem, "O E-mail não pode ter mais de 200 letras.");
				break;
			}
		}
	}

	@Test
	public void data_nascimento_nao_pode_ser_nula_inclusao() throws Exception {
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
	public void nacionalidade_com_mais_200_caracteres_inclusao() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setNacionalidade(
				"Nada do que é social e humano é mais real que as utopias. Na sua vertente eutópica, as \"\n"
						+ "				+ \"utopias constituíram sempre o fundamento simbólico e mítico sem o\\n\" + \n"
						+ "				\" qual nenhuma forma de organização social se suuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		pessoa.setNaturalidade("Curitibano");

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(response.getErros().get(0), "Nacionalidade não pode ter mais de 200 caracteres.");
	}

	@Test
	public void naturalidade_com_mais_200_caracteres_inclusao() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setNacionalidade("qualquer uma");
		pessoa.setNaturalidade(
				"Nada do que é social e humano é mais real que as utopias. Na sua vertente eutópica, as \\\"\\n\" + \n"
						+ "				\"				+ \\\"utopias constituíram sempre o fundamento simbólico e mítico sem o\\\\n\\\" + \\n\" + \n"
						+ "				\"				\\\" qual nenhuma forma de organização social se suuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(response.getErros().get(0), "Naturalidade não pode ter mais de 200 caracteres.");
	}

	@Test
	public void alterar_pessoa_com_sucesso() throws Exception {

		Pessoa pessoa = pessoaService.findByCpf("76051106006");
		pessoa.setEmail("jose@gmail.com");

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.update(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(response.getMensagemSucesso(), Mensagens.PESSOA_ALTERADA_COM_SUCESSO);
	}

	@Test
	public void deletar_pessoa_com_sucesso() throws Exception {

		Pessoa pessoa = pessoaService.findByCpf("76051106006");

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.delete(pessoa.getId());
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(response.getMensagemSucesso(), Mensagens.PESSOA_EXCLUIDA_COM_SUCESSO);
	}

	@Test
	public void deletar_pessoa_inexistente() throws Exception {

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.delete(99999l);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(response.getErros().get(0), Mensagens.NAO_E_POSSIVEL_APAGAR_UMA_PESSOA_INEXISTENTE);
	}

	@Test
	public void pesquisar_todos_registros() throws Exception {

		ResponseEntity<Response<Page<List<Pessoa>>>> responseEntity = pessoaService.findAll(0, 10);
		Response<Page<List<Pessoa>>> response = responseEntity.getBody();

		assertEquals(response.getHttpStatus(), HttpStatus.OK);
	}

	@Test
	public void pesquisar_por_id_sucesso() throws Exception {

		Pessoa pessoa = pessoaService.findByCpf("76051106006");
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.findById(pessoa.getId());
		Response<Pessoa> response = responseEntity.getBody();
		Pessoa pessoaConsultada = Pessoa.class.cast(response.getData());

		assertEquals(pessoaConsultada.getId(), pessoa.getId());
	}

	@Test
	public void pesquisar_por_id_inexistente() throws Exception {

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.findById(9999l);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(response.getErros().get(0), Mensagens.PESSOA_NAO_ENCONTRADA);
	}

}
