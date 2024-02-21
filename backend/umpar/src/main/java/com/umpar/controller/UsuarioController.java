/*package com.umpar.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umpar.config.security.JWTTokenService;
import com.umpar.entities.Usuario;
import com.umpar.service.UsuarioService;
import com.umpar.service.exceptions.ResourceNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	JWTTokenService jwtService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarTodos() {
		List<Usuario> lista = usuarioService.listarUsuarios();
		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> encontrarPorId(@PathVariable Long id) {
		Usuario usuario = usuarioService.encontrarPorId(id);
		return ResponseEntity.ok().body(usuario);

	}

	@PostMapping
	public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario) {
		try {
			usuario = usuarioService.salvarUsuario(usuario);
			return ResponseEntity.ok("Usuário salvo com sucesso");
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		usuarioService.deletarUsuarioPorId(id);
		return ResponseEntity.noContent().build();
	}

}             */  