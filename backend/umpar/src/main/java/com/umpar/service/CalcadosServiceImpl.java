package com.umpar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umpar.entities.Calcados;
import com.umpar.repositories.CalcadosRepository;
import com.umpar.service.exceptions.EntityNorFoundException;
import com.umpar.service.exceptions.ResourceNotFoundException;

@Service
public class CalcadosServiceImpl implements CalcadosService {

	@Autowired
	CalcadosRepository repository;

	@Override
	public List<Calcados> listaCalcados() {
		List<Calcados> listCalcados = repository.findAll();
		return listCalcados;
	}

	@Override
	public Calcados buscarPorId(Long id) {
		Optional<Calcados> calcados = repository.findById(id);
		return calcados.orElseThrow(() -> new ResourceNotFoundException("Sem calçados com esta Id!"));
	}
	@Override
	public Calcados cadastrar(Calcados calcados) {
		return repository.save(calcados);
	}
	@Override
	public Calcados editarCalcados(Long id, Calcados novoCalcados) {
		try {
			Calcados calcados = repository.getReferenceById(id);
			atualizarDados(novoCalcados, calcados);
			return repository.save(calcados);
		}catch(EntityNorFoundException e) {
			throw new EntityNorFoundException("Calçado não encontrado");
		}
		
	}

	private void atualizarDados(Calcados novoCalcados, Calcados calcados) {
		calcados.setId(novoCalcados.getId());
		calcados.setNome(novoCalcados.getNome());
		calcados.setTamanho(novoCalcados.getTamanho());
		calcados.setPreco(novoCalcados.getPreco());
		calcados.setMarca(novoCalcados.getMarca());
		calcados.setCor(novoCalcados.getCor());
		calcados.setMaterial(novoCalcados.getMaterial());
		
	}

	@Override
	public void deletarCalcados(Long id) {
		try {
			repository.deleteById(id);
		} catch (EntityNorFoundException e) {
			throw new EntityNorFoundException("Calçado não encontrado");

		}
	}



}
