/*package com.umpar.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umpar.entities.Usuario;
import com.umpar.repositories.UsuarioRepository;
import com.umpar.service.exceptions.ResourceNotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	private PasswordEncoder encoder;

	public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder encoder) {
		super();
		this.repository = repository;
		this.encoder = encoder;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);

		if (!usuario.isPresent()) {
			throw new ResourceNotFoundException("Email inválido ou não cadastrado");
		}

		if (usuario.get().getSenha() == null) {
			throw new ResourceNotFoundException("Senha não definida para o usuário.");
		}

		boolean senhasBatem = encoder.matches(senha, usuario.get().getSenha());

		if (!senhasBatem) {
			throw new ResourceNotFoundException("Senha inválida.");
		}

		return usuario.get();
	}

	@Transactional
	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		try {
			validarNome(usuario.getNome());
			validarEmail(usuario.getEmail());
			criptografarSenha(usuario);
			return repository.save(usuario);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Erro ao salvar o usuário: " + e.getMessage());
		}
	}

	private void criptografarSenha(Usuario usuario) {
		String senha = usuario.getSenha();
		String senhaCripto = encoder.encode(senha);
		usuario.setSenha(senhaCripto);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if (existe) {
			throw new ResourceNotFoundException("Já existe um usuario cadastrado com este email.");
		}
	}

	@Override
	public void validarNome(String nome) {
	    if (nome.isEmpty() || nome.trim().isEmpty()) {
	        throw new ResourceNotFoundException("Nome é obrigatório.");
	    }
	}


	public Usuario encontrarPorId(Long id) {
		Optional<Usuario> usuario = repository.findById(id);

		if (!usuario.isPresent()) {
			throw new ResourceNotFoundException("Usuário não encontrada para o ID informado.");

		}
		return usuario.get();
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return repository.findAll();
	}

	public void deletarUsuarioPorId(Long id) {
		Optional<Usuario> usuario = repository.findById(id);

		if (!usuario.isPresent()) {
			throw new ResourceNotFoundException("Usuário não encontrado para o ID informado.");
		}

		repository.deleteById(id);
	}

}*/