package com.cadastro.pessoa;

import org.junit.jupiter.api.Test;

//@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@SuppressWarnings("unused")
class CadastroPessoaApplicationTests {
	
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@Autowired
//	private PessoaRestController pessoaRestController;
//
//	@Autowired
//	private PessoaService pessoaService;
//	
//	@Autowired
//	private PessoaRepository pessoaRepository;
	
	//@Test
    public void acesso_Protegido() throws Exception {
//        this.mockMvc.perform(get("/api"))
//                .andExpect(status().isUnauthorized());
    }

    //@Test
    public void login_Usuario() throws Exception {
//        this.mockMvc.perform(get("/api/pessoa/listar").with(httpBasic("admin", "admin")))
//                .andExpect(authenticated());
    }

    //@Test
    public void login_Usuario_Invalido() throws Exception {
//        MvcResult result = this.mockMvc.perform(get("/api/pessoa/listar").with(httpBasic("invalido", "invalido")))
//                .andExpect(unauthenticated())
//                .andExpect(status().is4xxClientError())
//                .andReturn();
//        assertTrue(result.getResponse().getErrorMessage().contains(Unauthorized.class.getSimpleName()));
    }

	@Test
	void pessoa_criado_com_sucesso() {
		
//		try {
//			Pessoa pessoa = Pessoa.class.newInstance();
//			pessoa.setNome("Cauã Otávio Teixeira");
//			pessoa.setCpf("85639888598");
//			pessoa.setData_nascimento(LocalDate.parse("27/07/1955", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//			pessoa.setEmail("cauaotavioteixeira__cauaotavioteixeira@trevorh.com.br");
//			pessoa.setSexo(Sexo.MASCULINO);
//			pessoa.setNacionalidade("Brasileiro");
//			pessoa.setNaturalidade("Curitibano");
//
//			String pessoaJson = Util.asJsonString(pessoa);
//			mockMvc.perform(post("/api/pessoa/salvar").with(httpBasic("admin", "admin"))
//					.content(pessoaJson)
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andExpect(MockMvcResultMatchers.jsonPath("$.mensagemSucesso").value("Pessoa cadastrada com sucesso!"));
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
