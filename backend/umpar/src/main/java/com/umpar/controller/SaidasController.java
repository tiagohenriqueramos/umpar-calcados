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

import com.umpar.entities.Saidas;
import com.umpar.service.CalcadosService;
import com.umpar.service.FornecedorService;
import com.umpar.service.SaidasService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/saidas")
@MultipartConfig
public class SaidasController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	SaidasService service;
	
	@Autowired
	CalcadosService calcadosService;

	@Autowired
	FornecedorService fornecedorService;

	@GetMapping
	public ResponseEntity<List<Saidas>> listaSaidas() {
		List<Saidas> lista = service.listaSaidas();
		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Saidas> encontrarPorId(@PathVariable Long id) {
		Saidas saidas = service.buscarPorId(id);
		return ResponseEntity.ok().body(saidas);
	}

	@PostMapping
	public ResponseEntity<Saidas> cadastrar(@RequestBody Saidas saidas) {
		saidas = service.cadastrar(saidas);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saidas.getId())
				.toUri();
		return ResponseEntity.created(uri).body(saidas);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Saidas> editar(@PathVariable Long id, @RequestBody Saidas saidas) {
		saidas = service.editarSaidas(id, saidas);
		return ResponseEntity.ok().body(saidas);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletarSaidas(id);
		return ResponseEntity.noContent().build();
	}
}
