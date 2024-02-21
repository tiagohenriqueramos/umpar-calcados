package com.umpar.service;

import java.util.List;

import com.umpar.entities.Saidas;

public interface SaidasService {


	List<Saidas> listaSaidas();

	Saidas buscarPorId(Long id);

	Saidas cadastrar(Saidas saidas);

	Saidas editarSaidas(Long id, Saidas saidas);

	void deletarSaidas(Long id);

}
