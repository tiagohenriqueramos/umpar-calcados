package com.umpar.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umpar.entities.Calcados;
import com.umpar.entities.Fornecedor;
import com.umpar.service.CalcadosService;
import com.umpar.service.FornecedorService;
import com.umpar.service.exceptions.ResourceNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/calcados")
@MultipartConfig
public class CalcadosController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CalcadosController.class);

	@Autowired
	CalcadosService service;

	@Autowired
	FornecedorService fornecedorService;

	@GetMapping
	public ResponseEntity<List<Calcados>> listaCalcados() {
		List<Calcados> lista = service.listaCalcados();
		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Calcados> encontrarPorId(@PathVariable Long id) {
		try {
			Calcados calcados = service.buscarPorId(id);

			if (calcados != null) {
				return ResponseEntity.ok().body(calcados);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "/cadastro", consumes = "multipart/form-data")
	public ResponseEntity<Calcados> cadastrar(@RequestParam("file") MultipartFile file,
			@RequestParam("nome") String nome, @RequestParam("material") String material,
			@RequestParam("marca") String marca, @RequestParam("cor") String cor,
			@RequestParam("fornecedor_id") Long fornecedor_id, @RequestParam("precoGrade") Double precoGrade,
			@RequestParam("frete") Double frete, @RequestParam("data") LocalDate data,
			@RequestParam("grade") String gradeString) throws JsonMappingException, JsonProcessingException {
		try {
			logger.info("Iniciando o método cadastrar");

			ObjectMapper objectMapper = new ObjectMapper();
			Map<Integer, Integer> grade = objectMapper.readValue(gradeString,
					new TypeReference<Map<Integer, Integer>>() {
					});

			logger.info("Dados lidos do gradeString: {}", grade);

			Fornecedor fornecedor = fornecedorService.buscarPorId(fornecedor_id);

			if (fornecedor == null) {
				logger.warn("Fornecedor não encontrado com ID: {}", fornecedor_id);
				return ResponseEntity.notFound().build();
			}

			logger.info("Fornecedor encontrado: {}", fornecedor);

			Calcados calcados = new Calcados();
			calcados.setNome(nome);
			calcados.setMaterial(material);
			calcados.setMarca(marca);
			calcados.setCor(cor);

			calcados.setFornecedor(fornecedor);
			calcados.setPrecoGrade(precoGrade);
			calcados.setFrete(frete);
			calcados.setData(data);
			calcados.setGrade(grade);

			calcados = service.cadastrar(calcados, file);

			URI uri = ServletUriComponentsBuilder.fromPath("/{id}").buildAndExpand(calcados.getId()).toUri();

			logger.info("Cadastro concluído com sucesso. URI: {}", uri);

			return ResponseEntity.created(uri).body(calcados);
		} catch (Exception e) {
			logger.error("Erro durante o cadastro", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/imagem/{nomeImagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("nomeImagem") String imagem) throws IOException {
		File imagemArquivo = new File("c:/imagens/" + imagem);
		System.out.println(imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Calcados> editar(@PathVariable Long id, @RequestBody Calcados calcados) {
		calcados = service.editarCalcados(id, calcados);
		return ResponseEntity.ok().body(calcados);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletarCalcados(id);
		return ResponseEntity.noContent().build();
	}

}
