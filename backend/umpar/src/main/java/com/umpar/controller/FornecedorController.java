package com.umpar.controller;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.umpar.entities.Fornecedor;
import com.umpar.service.FornecedorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/fornecedor")
@MultipartConfig
public class FornecedorController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	FornecedorService service;

	@GetMapping
	public ResponseEntity<List<Fornecedor>> listaCalcados() {
		List<Fornecedor> lista = service.listaFornecedor();
		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Fornecedor> encontrarPorId(@PathVariable Long id) {
		Fornecedor fornecedor = service.buscarPorId(id);
		return ResponseEntity.ok().body(fornecedor);
	}

	@PostMapping
	public ResponseEntity<Fornecedor> cadastrar(@RequestBody Fornecedor fornecedor) {
		fornecedor = service.cadastrar(fornecedor);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fornecedor.getId())
				.toUri();
		return ResponseEntity.created(uri).body(fornecedor);
	}
	

	@PutMapping(value = "/{id}")
	public ResponseEntity<Fornecedor> editar(@PathVariable Long id, @RequestBody Fornecedor fornecedor) {
		fornecedor = service.editarCalcados(id, fornecedor);
		return ResponseEntity.ok().body(fornecedor);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletarFornecedor(id);
		return ResponseEntity.noContent().build();
	}
}
