package com.serratec.music_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.serratec.music_api.domain.Artista;
import com.serratec.music_api.repository.ArtistaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/artistas")
public class ArtistaController {

	 // Injeção de dependência para a interface de repositório da Música
    @Autowired
    private ArtistaRepository artistaRepository;
    
    // --- MÉTODOS GET (READ) ---
    
    @GetMapping
    @Operation(summary = "Lista todos os artistas", description = "Retorna uma lista com todos os artistas cadastrados.")
    public List<Artista> listarTodos() {
    	//
        return artistaRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um artista por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Artista encontrado"),
        @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    public ResponseEntity<Artista> buscarPorId(@PathVariable Long id) {
    	// Busca o artista pelo ID, que retorna um Optional (pode ou não existir)
        Optional<Artista> artista = artistaRepository.findById(id);
        
        // Se o artista existir, retorna 200 OK. Se não, retorna 404 NOT FOUND.
        return artista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
 // --- MÉTODO POST (CREATE) ---
    @PostMapping
    @Operation(summary = "Cria um novo artista")
    public ResponseEntity<Artista> criar(@Valid @RequestBody Artista artista) {
        Artista novoArtista = artistaRepository.save(artista);
        return new ResponseEntity<>(novoArtista, HttpStatus.CREATED);
    }

    // --- MÉTODO PUT (UPDATE) ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um artista existente")
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @Valid @RequestBody Artista artista) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artista.setId(id);
        Artista artistaAtualizado = artistaRepository.save(artista);
        return ResponseEntity.ok(artistaAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um artista")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artistaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}