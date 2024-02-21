package com.umpar.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.umpar.entities.Calcados;
import com.umpar.repositories.CalcadosRepository;
import com.umpar.service.exceptions.EntityNorFoundException;
import com.umpar.service.exceptions.ResourceNotFoundException;

@Service
public class CalcadosServiceImpl implements CalcadosService {

	@Autowired
	CalcadosRepository repository;
	
	 @Autowired
	    private EntityManager entityManager;

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
		} catch (EntityNorFoundException e) {
			throw new EntityNorFoundException("Calçado não encontrado");
		}

	}

	private void atualizarDados(Calcados novoCalcados, Calcados calcados) {
		calcados.setId(novoCalcados.getId());
		calcados.setNome(novoCalcados.getNome());
		calcados.setMarca(novoCalcados.getMarca());
		calcados.setCor(novoCalcados.getCor());
		calcados.setMaterial(novoCalcados.getMaterial());

	}
    @Transactional

	@Override
	public void deletarCalcados(Long id) {
		try {
			 entityManager.createQuery("UPDATE Saidas s SET s.calcados = null WHERE s.calcados.id = :id")
             .setParameter("id", id)
             .executeUpdate();
			repository.deleteById(id);
		} catch (EntityNorFoundException e) {
			throw new EntityNorFoundException("Calçado não encontrado");

		}

	}

	@Override
	public Calcados cadastrar(Calcados calcados, MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				String base64Image = Base64.getEncoder().encodeToString(bytes);

				calcados.setArquivo(bytes);
				String nomeImagem = String.valueOf(calcados.getNome());
				Path caminho = Paths.get("c:/imagens/" + nomeImagem);
				Files.write(caminho, bytes);
				calcados.setNomeImagem(nomeImagem);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return repository.save(calcados);
	}

	@Override
	public byte[] buscarImagemPorNome(String nomeImagem) {
		try {
			Path caminho = Paths.get("c:/imagens/" + nomeImagem);
			return Files.readAllBytes(caminho);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void removerDaGrade(Long calcadoId, Integer numero, Integer quantidade) {
		Calcados calcados = repository.findById(calcadoId)
				.orElseThrow(() -> new RuntimeException("Calçado não encontrado com ID: " + calcadoId));

		Map<Integer, Integer> grade = calcados.getGrade();

		if (grade.containsKey(numero)) {
			Integer quantidadeAtual = grade.get(numero);

			if (quantidadeAtual >= quantidade) {
				int novaQuantidade = quantidadeAtual - quantidade;
				if (novaQuantidade == 0) {
					grade.remove(numero);
				} else {
					grade.put(numero, novaQuantidade);
				}

				repository.save(calcados);
			} else {
				throw new RuntimeException("A quantidade a ser removida é maior do que a quantidade atual na grade.");
			}
		} else {
			throw new RuntimeException("O número especificado não está presente na grade. ID do calçado: " + calcadoId
					+ ", Número: " + numero);
		}
	}

}
