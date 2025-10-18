package com.serratec.music_api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serratec.music_api.domain.Artista;
import com.serratec.music_api.domain.Musica;
import com.serratec.music_api.repository.ArtistaRepository;
import com.serratec.music_api.repository.MusicaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private ArtistaRepository artistaRepository; // Injetado para lidar com o relacionamento

    @GetMapping
    @Operation(summary = "Lista todas as músicas", description = "Retorna uma lista com todas as músicas cadastradas.")
    public List<Musica> listarTodas() {
        return musicaRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma música por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Música encontrada"),
            @ApiResponse(responseCode = "404", description = "Música não encontrada")
    })
    public ResponseEntity<Musica> buscarPorId(@PathVariable Long id) {
        Optional<Musica> musica = musicaRepository.findById(id);
        return musica.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cria uma nova música", description = "Cria uma nova música e a associa a artistas existentes.")
    public ResponseEntity<Musica> criar(@Valid @RequestBody Musica musica) {
        // Validate artistas list: every artista object must provide an id
        if (musica.getArtistas() != null) {
            // collect any invalid entries (null or id == null)
            StringBuilder invalid = new StringBuilder();
            for (Artista artistaInfo : musica.getArtistas()) {
                if (artistaInfo == null || artistaInfo.getId() == null) {
                    if (invalid.length() > 0) invalid.append(", ");
                    invalid.append("artista com id nulo ou objeto inválido");
                }
            }
            if (invalid.length() > 0) {
                return ResponseEntity.badRequest().build();
            }
        }

        // Lógica para associar artistas que já existem no banco de dados.
        // O JSON de entrada deve conter a lista de artistas apenas com seus IDs.
        Set<Artista> artistasCompletos = new HashSet<>();
        if (musica.getArtistas() != null) {
            for(Artista artistaInfo : musica.getArtistas()) {
                // Para cada artista recebido, busca ele completo no banco de dados
                Optional<Artista> artistaOpt = artistaRepository.findById(artistaInfo.getId());
                // Se o artista existir, adiciona na lista que será salva na música
                artistaOpt.ifPresent(artistasCompletos::add);
            }
        }
        musica.setArtistas(artistasCompletos);

        Musica novaMusica = musicaRepository.save(musica);
        return new ResponseEntity<>(novaMusica, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma música existente")
    public ResponseEntity<Musica> atualizar(@PathVariable Long id, @Valid @RequestBody Musica musica) {
        if (!musicaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        musica.setId(id);

        // Reaplica a mesma lógica do POST para garantir que os artistas sejam associados corretamente
        Set<Artista> artistasCompletos = new HashSet<>();
        if (musica.getArtistas() != null) {
            for(Artista artistaInfo : musica.getArtistas()) {
                Optional<Artista> artistaOpt = artistaRepository.findById(artistaInfo.getId());
                artistaOpt.ifPresent(artistasCompletos::add);
            }
        }
        musica.setArtistas(artistasCompletos);

        Musica musicaAtualizada = musicaRepository.save(musica);
        return ResponseEntity.ok(musicaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma música")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!musicaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        musicaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}