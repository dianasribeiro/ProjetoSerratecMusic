package com.serratec.music_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serratec.music_api.domain.Artista;

import jakarta.validation.Valid;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

	@SuppressWarnings("unchecked")
	Artista save(@Valid Artista artista);

	Optional<Artista> findById(Long id);
	

}
