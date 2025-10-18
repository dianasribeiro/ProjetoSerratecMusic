package com.serratec.music_api.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.serratec.music_api.domain.Musica;

@SuppressWarnings("unused")
@Entity

// Evita o loop infinito na serialização do JSON
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property ="id")

public class Artista {
	
    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento
     private Long id;
    

	@NotBlank(message = "Prencha o nome do artista")
    @Size(min = 3 ,max = 50, message = "O nome do artista deve ter no máximo 50 caracteres")
     private String nome;
	
	@NotBlank(message = "Prencha a nacionalidade do artista")
    @Size(min = 3 ,max = 30, message = "A nacionalidade do artista deve ter no máximo 30 caracteres")
     private String nacionalidade;
    
    @ManyToMany(mappedBy ="artistas")
    private Set<Musica> musicas;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Set<Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(Set<Musica> musicas) {
		this.musicas = musicas;
	}
}
	

	