package com.umpar.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<List<Calcados>> listaCalcados(){
		List<Calcados> lista = service.listaCalcados();
		return ResponseEntity.ok().body(lista);
	}
}
