package com.serratec.music_api.controller;

import com.serratec.music_api.domain.Musica;
import com.serratec.music_api.domain.Playlist;
import com.serratec.music_api.domain.Usuario;
import com.serratec.music_api.repository.MusicaRepository;
import com.serratec.music_api.repository.PlaylistRepository;
import com.serratec.music_api.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    @GetMapping
    @Operation(summary = "Lista todas as playlists", description = "Retorna uma lista com todas as playlists cadastradas.")
    public List<Playlist> listarTodas() {
        return playlistRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma playlist por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist encontrada"),
            @ApiResponse(responseCode = "404", description = "Playlist não encontrada")
    })
    public ResponseEntity<Playlist> buscarPorId(@PathVariable Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        return playlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cria uma nova playlist", description = "Cria uma nova playlist e a associa a um usuário existente.")
    public ResponseEntity<String> criar(@Valid @RequestBody Playlist playlist) {
        // Validate request body to avoid NullPointerException
        if (playlist == null || playlist.getUsuario() == null || playlist.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().body("Campo 'usuario.id' é obrigatório no corpo da requisição.");
        }

        Long usuarioId = playlist.getUsuario().getId();
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário com ID " + usuarioId + " não encontrado.");
        }

        // Associa o usuário completo (buscado do banco) à nova playlist
        playlist.setUsuario(usuarioOpt.get());

        // Optional: validate músicas if provided
        if (playlist.getMusicas() != null && !playlist.getMusicas().isEmpty()) {
            Set<Musica> musicasValidadas = new HashSet<>();
            for (Musica m : playlist.getMusicas()) {
                if (m != null && m.getId() != null) {
                    musicaRepository.findById(m.getId()).ifPresent(musicasValidadas::add);
                }
            }
            playlist.setMusicas(musicasValidadas);
        }

        playlistRepository.save(playlist);
        return new ResponseEntity<>("Playlist criada com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma playlist existente", description = "Atualiza os dados de uma playlist, incluindo sua lista de músicas.")
    public ResponseEntity<Playlist> atualizar(@PathVariable Long id, @Valid @RequestBody Playlist playlistData) {
        Optional<Playlist> playlistExistenteOpt = playlistRepository.findById(id);

        if (playlistExistenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Playlist playlistExistente = playlistExistenteOpt.get();
        
        // Atualiza os dados básicos da playlist
        playlistExistente.setNome(playlistData.getNome());
        playlistExistente.setDescricao(playlistData.getDescricao());

        // Lógica para atualizar a lista de músicas
        Set<Musica> musicasAtualizadas = new HashSet<>();
        if (playlistData.getMusicas() != null) {
            for (Musica musicaInfo : playlistData.getMusicas()) {
                // Busca cada música pelo ID fornecido no JSON
                Optional<Musica> musicaOpt = musicaRepository.findById(musicaInfo.getId());
                // Se a música existir, adiciona à nova lista
                musicaOpt.ifPresent(musicasAtualizadas::add);
            }
        }
        // Substitui a lista de músicas antiga pela nova
        playlistExistente.setMusicas(musicasAtualizadas);

        Playlist playlistSalva = playlistRepository.save(playlistExistente);
        return ResponseEntity.ok(playlistSalva);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma playlist")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!playlistRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        playlistRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}