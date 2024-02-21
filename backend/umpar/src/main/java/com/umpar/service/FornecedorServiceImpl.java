package com.umpar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umpar.entities.Fornecedor;
import com.umpar.repositories.FornecedorRepository;
import com.umpar.service.exceptions.EntityNorFoundException;
import com.umpar.service.exceptions.ResourceNotFoundException;

@Service
public class FornecedorServiceImpl implements FornecedorService {

	@Autowired
	FornecedorRepository repository;

	@Override
	public List<Fornecedor> listaFornecedor() {
		List<Fornecedor> listFornecedor = repository.findAll();
		return listFornecedor;
	}

	@Override
	public Fornecedor buscarPorId(Long id) {
		Optional<Fornecedor> fornecedor = repository.findById(id);
		return fornecedor.orElseThrow(() -> new ResourceNotFoundException("Sem calçados com esta Id!"));
	}

	@Override
	public Fornecedor cadastrar(Fornecedor fornecedor) {
		return repository.save(fornecedor);

	}

	@Override
	public Fornecedor editarCalcados(Long id, Fornecedor novofornecedor) {
		try {
			Fornecedor fornecedor = repository.getReferenceById(id);
			atualizarDados(novofornecedor, fornecedor);
			return repository.save(fornecedor);
		} catch (EntityNorFoundException e) {
			throw new EntityNorFoundException("Fonercedo não encontrado");
		}
	}

	private void atualizarDados(Fornecedor novoFornecedor, Fornecedor fornecedor) {
		fornecedor.setId(novoFornecedor.getId());
		fornecedor.setRazaoSocial(novoFornecedor.getRazaoSocial());
		fornecedor.setCnpj(novoFornecedor.getCnpj());
		fornecedor.setRuaAvenida(novoFornecedor.getRuaAvenida());
		fornecedor.setNumero(novoFornecedor.getNumero());
		fornecedor.setBairro(novoFornecedor.getBairro());
		fornecedor.setEstado(novoFornecedor.getEstado());
		fornecedor.setCidade(novoFornecedor.getCidade());
		fornecedor.setTelefone(novoFornecedor.getTelefone());
		fornecedor.setEmail(novoFornecedor.getEmail());
		fornecedor.setSite(novoFornecedor.getSite());

	}

	@Override
	public void deletarFornecedor(Long id) {
		try {
			repository.deleteById(id);
		} catch (EntityNorFoundException e) {
			throw new EntityNorFoundException("Fornecedor não encontrado");

		}

	}

}
