package com.umpar.controller;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

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

import com.umpar.entities.Calcados;
import com.umpar.service.CalcadosService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/calcados")
public class CalcadosController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	CalcadosService service;

	@GetMapping
	public ResponseEntity<List<Calcados>> listaCalcados() {
		List<Calcados> lista = service.listaCalcados();
		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Calcados> encontrarPorId(@PathVariable Long id) {
		Calcados calcados = service.buscarPorId(id);
		return ResponseEntity.ok().body(calcados);
	}

	@PostMapping
	public ResponseEntity<Calcados> cadastrar(@RequestBody Calcados calcados) {
		calcados = service.cadastrar(calcados);
		URI uri = ServletUriComponentsBuilder.fromPath("/{id}").buildAndExpand(calcados.getId()).toUri();
		return ResponseEntity.created(uri).body(calcados);
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
