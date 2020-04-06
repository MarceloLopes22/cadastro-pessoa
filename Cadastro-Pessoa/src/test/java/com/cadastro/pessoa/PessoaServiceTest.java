package com.cadastro.pessoa;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.cadastro.pessoa.basica.Endereco;
import com.cadastro.pessoa.basica.Pessoa;
import com.cadastro.pessoa.constantes.Mensagens;
import com.cadastro.pessoa.controller.response.Response;
import com.cadastro.pessoa.dao.PessoaRepository;
import com.cadastro.pessoa.enuns.Sexo;
import com.cadastro.pessoa.enuns.Uf;
import com.cadastro.pessoa.service.PessoaService;
import com.cadastro.pessoa.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("unused")
@Transactional
public class PessoaServiceTest {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Before
    public void setUp() {
    	pessoaRepository.deleteAll();
    	
    	Endereco endereco = getEndereco();
    	endereco.setData_cadastro(new Date());
    	
    	Pessoa p = new Pessoa("Dona Herminia", Sexo.FEMININO, "hermina@gmail.com", LocalDate.of(2020, 04, 04), "Teste",
				"Teste", "76051106006", endereco);
    	p.setData_cadastro(new Date());
    	
    	pessoaRepository.save(p);
    }

	private Endereco getEndereco() {
		Endereco endereco = new Endereco("Avenida Juiz Marco Túlio Isaac", "Laranjeiras", "Betim", "32676358", Uf.MINAS_GERAIS, 170);
		return endereco;
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
		pessoa.setEndereco(getEndereco());

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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.JA_EXISTE_UMA_PESSOA_CADASTRADA_COM_ESSE_CPF_POR_FAVOR_TENTE_OUTRO, response.getErros().get(0));
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.INFORME_UM_CPF_VALIDO, response.getErros().get(0));
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		String mensagemErro = null;
		for (String mensagem : response.getErros()) {
			if (mensagem.equalsIgnoreCase(Mensagens.O_CPF_NAO_PODE_TER_MAIS_DE_11_NUMEROS)) {
				assertEquals(Mensagens.O_CPF_NAO_PODE_TER_MAIS_DE_11_NUMEROS, mensagem);
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.O_CPF_DEVE_SER_PREENCHIDO, response.getErros().get(0));
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.NOME_E_OBRIGATORIO, response.getErros().get(0));
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.NOME_NAO_PODE_TER_MAIS_DE_200_LETRAS, response.getErros().get(0));
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.INFORME_UM_E_MAIL_VALIDO, response.getErros().get(0));
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		String mensagemErro = null;
		for (String mensagem : response.getErros()) {
			if (mensagem.equalsIgnoreCase(Mensagens.O_E_MAIL_NAO_PODE_TER_MAIS_DE_200_LETRAS)) {
				assertEquals(Mensagens.O_E_MAIL_NAO_PODE_TER_MAIS_DE_200_LETRAS, mensagem);
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.A_DATA_DE_NASCIMENTO_DEVE_SER_PREENCHIDA, response.getErros().get(0));
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
		pessoa.setEndereco(getEndereco());

		String pessoaJson = Util.asJsonString(pessoa);

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.NACIONALIDADE_NAO_PODE_TER_MAIS_DE_200_CARACTERES, response.getErros().get(0));
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
		pessoa.setEndereco(getEndereco());
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.NATURALIDADE_NAO_PODE_TER_MAIS_DE_200_CARACTERES, response.getErros().get(0));
	}
	
	@Test
	public void pessoa_sem_endereco_preenchido_inclusao() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setNacionalidade("qualquer uma");
		pessoa.setNaturalidade("qualquer uma");
		pessoa.setEndereco(null);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.O_ENDERECO_E_OBRIGATORTIO, response.getErros().get(0));
	}
	
	@Test
	public void pessoa_com_cep_invalido_inclusao() throws Exception {
		Pessoa pessoa = Pessoa.class.newInstance();
		pessoa.setNome("xpto");
		pessoa.setCpf("32056383012");
		LocalDate data_nascimento = LocalDate.parse("1955-07-27", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		pessoa.setData_nascimento(data_nascimento);
		pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
		pessoa.setNacionalidade("qualquer uma");
		pessoa.setNaturalidade("qualquer uma");
		Endereco endereco = getEndereco();
		endereco.setCep("158789358452489");
		pessoa.setEndereco(endereco);
		
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.create(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.CEP_INVALIDO, response.getErros().get(0));
	}

	@Test
	public void alterar_pessoa_com_sucesso() throws Exception {

		Pessoa pessoa = pessoaService.findByCpf("76051106006");
		pessoa.setEmail("jose@gmail.com");
		pessoa.getEndereco().setData_atualizacao(new Date());

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.update(pessoa);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.PESSOA_ALTERADA_COM_SUCESSO, response.getMensagemSucesso());
	}

	@Test
	public void deletar_pessoa_com_sucesso() throws Exception {

		Pessoa pessoa = pessoaService.findByCpf("76051106006");

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.delete(pessoa.getId());
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.PESSOA_EXCLUIDA_COM_SUCESSO, response.getMensagemSucesso());
	}

	@Test
	public void deletar_pessoa_inexistente() throws Exception {

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.delete(99999l);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.NAO_E_POSSIVEL_APAGAR_UMA_PESSOA_INEXISTENTE, response.getErros().get(0));
	}

	@Test
	public void pesquisar_todos_registros() throws Exception {

		ResponseEntity<Response<Page<List<Pessoa>>>> responseEntity = pessoaService.findAll(0, 10);
		Response<Page<List<Pessoa>>> response = responseEntity.getBody();

		assertEquals(HttpStatus.OK, response.getHttpStatus());
	}

	@Test
	public void pesquisar_por_id_sucesso() throws Exception {

		Pessoa pessoa = pessoaService.findByCpf("76051106006");
		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.findById(pessoa.getId());
		Response<Pessoa> response = responseEntity.getBody();
		Pessoa pessoaConsultada = Pessoa.class.cast(response.getData());

		assertEquals(pessoa.getId(), pessoaConsultada.getId());
	}

	@Test
	public void pesquisar_por_id_inexistente() throws Exception {

		ResponseEntity<Response<Pessoa>> responseEntity = pessoaService.findById(9999l);
		Response<Pessoa> response = responseEntity.getBody();

		assertEquals(Mensagens.PESSOA_NAO_ENCONTRADA, response.getErros().get(0));
	}

}
