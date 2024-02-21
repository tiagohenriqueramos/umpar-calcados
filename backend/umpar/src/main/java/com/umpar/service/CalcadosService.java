package com.umpar.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.umpar.entities.Calcados;

public interface CalcadosService {

	List<Calcados> listaCalcados();

	Calcados buscarPorId(Long id);

	Calcados cadastrar(Calcados calcados);

	Calcados editarCalcados(Long id, Calcados calcados);

	Calcados cadastrar(Calcados calcados, MultipartFile file);

	void deletarCalcados(Long id);

	byte[] buscarImagemPorNome(String nomeImagem);


	void removerDaGrade(Long calcadoId, Integer tamanho, Integer quantidade);



}
