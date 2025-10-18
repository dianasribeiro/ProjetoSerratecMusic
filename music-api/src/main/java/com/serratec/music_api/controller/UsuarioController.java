package com.serratec.music_api.controller;


import com.serratec.music_api.domain.Usuario;
import com.serratec.music_api.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

	@RestController
	@RequestMapping("/usuarios")
	public class UsuarioController {
		

	    @Autowired
	    private UsuarioRepository usuarioRepository;

	    @GetMapping
	    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados.")
	    public List<Usuario> listarTodos() {
	        return usuarioRepository.findAll();
	    }

	    @GetMapping("/{id}")
	    @Operation(summary = "Busca um usuário por ID")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
	            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	    })
	    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
	        Optional<Usuario> usuario = usuarioRepository.findById(id);
	        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário e seu perfil associado em uma única requisição.")
	    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
	    	
	        // @OneToOne(cascade = CascadeType.ALL) na entidade Usuario,
	        // o Perfil enviado no JSON será salvo automaticamente junto com o Usuário.
	    	
	        Usuario novoUsuario = usuarioRepository.save(usuario);
	        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
	    }

	    @PutMapping("/{id}")
	    @Operation(summary = "Atualiza um usuário existente")
	    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
	        if (!usuarioRepository.existsById(id)) {
	            return ResponseEntity.notFound().build();
	        }
	        usuario.setId(id);
	        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
	        return ResponseEntity.ok(usuarioAtualizado);
	    }

	    @DeleteMapping("/{id}")
	    @Operation(summary = "Deleta um usuário")
	    public ResponseEntity<Void> deletar(@PathVariable Long id) {
	        if (!usuarioRepository.existsById(id)) {
	            return ResponseEntity.notFound().build();
	        }
	        usuarioRepository.deleteById(id);
	        return ResponseEntity.noContent().build();
	    }
	}

	


