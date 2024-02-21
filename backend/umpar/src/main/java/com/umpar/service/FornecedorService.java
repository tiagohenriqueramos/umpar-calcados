package com.umpar.service;

import java.util.List;

import com.umpar.entities.Fornecedor;

public interface FornecedorService {

	List<Fornecedor> listaFornecedor();

	Fornecedor buscarPorId(Long id);

	Fornecedor cadastrar(Fornecedor fornecedor);

	Fornecedor editarCalcados(Long id, Fornecedor fornecedor);

	void deletarFornecedor(Long id);
}
