package com.umpar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umpar.entities.Calcados;
import com.umpar.entities.Saidas;
import com.umpar.repositories.CalcadosRepository;
import com.umpar.repositories.SaidasRepository;
import com.umpar.service.exceptions.EntityNorFoundException;
import com.umpar.service.exceptions.ResourceNotFoundException;

@Service
public class SaidasServiceImpl implements SaidasService {

	@Autowired
	SaidasRepository repository;

	@Autowired
	CalcadosService calcadosService;
	
	@Autowired
	CalcadosRepository calcadosRepository;

	@Override
	public List<Saidas> listaSaidas() {
		List<Saidas> listFornecedor = repository.findAll();
		return listFornecedor;
	}

	@Override
	public Saidas buscarPorId(Long id) {
		Optional<Saidas> fornecedor = repository.findById(id);
		return fornecedor.orElseThrow(() -> new ResourceNotFoundException("Sem calçados com esta Id!"));
	}

	@Override
	public Saidas cadastrar(Saidas saidas) {
		
		
		valorUnidade(saidas.getCalcados().getId(), saidas.getUnidade());
		
		saidas.setLiquido( valorUnidade(saidas.getCalcados().getId(), saidas.getUnidade()) - saidas.getEmbalagem()  );
		saidas.setUnidade(valorUnidade(saidas.getCalcados().getId(), saidas.getUnidade()));

		calcadosService.removerDaGrade(saidas.getCalcados().getId(), saidas.getTamanho(), saidas.getQuantidade());
		return repository.save(saidas);
	}

	@Override
	public Saidas editarSaidas(Long id, Saidas novaSaidas) {
		try {
			Saidas saidas = repository.getReferenceById(id);
			atualizarDados(novaSaidas, saidas);

			return repository.save(saidas);
		} catch (EntityNorFoundException e) {
			throw new EntityNorFoundException("Fonercedo não encontrado");
		}
	}

	private void atualizarDados(Saidas novaSaidas, Saidas saidas) {
		saidas.setId(novaSaidas.getCalcados().getId());
		saidas.setData(novaSaidas.getData());
		saidas.setEmbalagem(novaSaidas.getEmbalagem());
		saidas.setPrecoVenda(novaSaidas.getPrecoVenda());
	}

	@Override
	public void deletarSaidas(Long id) {
		try {
			repository.deleteById(id);
		} catch (EntityNorFoundException e) {
			throw new EntityNorFoundException("Fornecedor não encontrado");

		}
		
		

	}
	public Double valorUnidade(Long id, Double unidade) {
		Calcados calcados = calcadosRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Calçado não encontrado com ID: " + id));
	 double  valorUnidade = calcados.getPrecoGrade() / 7.0;
	    return valorUnidade;
	}



}