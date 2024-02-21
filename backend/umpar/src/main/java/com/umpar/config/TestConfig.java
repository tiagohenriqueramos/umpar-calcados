package com.umpar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	/*@Autowired
	private CalcadosRepository calcadosRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private EntradasRepository entradasRepository;

	@Autowired
	private SaidasRepository saidasRepository;

	@Autowired
	SaidasService saidasService;


	/*
	 * @Autowired private UsuarioRepository usuarioRepository;
	 * 
	 * @Autowired private PasswordEncoder passwordEncoder;
	 */
	@Override
	public void run(String... args) throws Exception {

		/*
		 * String senha = "123456"; String senhaCodificada =
		 * passwordEncoder.encode(senha); Usuario usuario = new Usuario(null, "tiago",
		 * "tiago@tiago.com", senhaCodificada);
		 * 
		 * usuarioRepository.saveAll(Arrays.asList(usuario));
		 */

	/*	LocalDate data = LocalDate.now();
		LocalDate data1 = data.plusDays(1);

		Fornecedor f1 = new Fornecedor(null, "Avacy Distribuidora e Comércio de Calçados Ltda", "61.234.829/0001-43",
				"Rua Gomes Cardim", "235", "Brás", "São Paulo ", "São Paulo ", "(11) 5461-4932 ",
				" contato@avacy.com.br", "https://www.avacy.com.br");

		Fornecedor f2 = new Fornecedor(null, "Comércio de Calçados Ltda", "61.234.829/0001-43", "Rua  Cardim", "235",
				"Brás", "São Paulo ", "São Paulo ", "(11) 5461-4932 ", " contato@com.br", "https://www.com.br");

		fornecedorRepository.saveAll(Arrays.asList(f1, f2));

		Calcados c1 = new Calcados(null, "Sapato Social", "Couro", "Marca X", f1, "", null);

		Calcados c2 = new Calcados(null, "Mocassim", "Camurça", "Marca T", f2, "", null);

		calcadosRepository.saveAll(Arrays.asList(c1, c2));



		Map<Integer, Integer> grade1 = new HashMap<>();
		grade1.put(33, 1);
		grade1.put(34, 1);
		grade1.put(35, 2);
		grade1.put(36, 3);

		Entradas e1 = new Entradas(null, c1, Cor.AMARELO, 500.0, 15.0, data1, grade1);

		e1.setGrade(grade1);

		Map<Integer, Integer> grade2 = new HashMap<>();
		grade2.put(40, 3);
		grade2.put(39, 3);
		grade2.put(38, 3);
		grade2.put(37, 3);

		Entradas e2 = new Entradas(null, c2, Cor.AZUL, 720.0, 15.0, data1, grade2);

		e2.setGrade(grade2);
		entradasRepository.saveAll(Arrays.asList(e1, e2));
		
		
		Saidas s1 = new Saidas();
		Saidas s2 = new Saidas(null, data1, c2,10.0, 40 ,  120.0, 15.0, 2.50, 0.3, Pagamento.PIX, 0.0, 0.0);
		Saidas s3 = new Saidas(null, data1, c2,1.0, 39, 120.0, 15.0, 2.50, 0.3, Pagamento.PIX, 0.0, 0.0);

		
		saidasRepository.saveAll(Arrays.asList(s2, s3));

	*/}

}
