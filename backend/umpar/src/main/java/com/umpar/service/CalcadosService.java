package com.umpar.service;

import java.util.List;

import com.umpar.entities.Calcados;

public interface CalcadosService {

	List<Calcados> listaCalcados();
	
	Calcados buscarPorId(Long id);
	
	Calcados cadastrar(Calcados calcados);
	
	Calcados editarCalcados(Long id, Calcados calcados);
	
	void deletarCalcados(Long id);
}
