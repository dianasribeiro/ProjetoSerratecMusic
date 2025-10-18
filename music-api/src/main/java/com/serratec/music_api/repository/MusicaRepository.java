package com.serratec.music_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serratec.music_api.domain.Musica;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {

}
