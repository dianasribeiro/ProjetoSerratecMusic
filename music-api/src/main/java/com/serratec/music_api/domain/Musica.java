package com.serratec.music_api.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.serratec.music_api.Enum.GeneroMusical;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity

//Evita o loop infinito na serialização do JSON
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property ="id")

public class Musica {
	
	@Id // Chave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento
	private Long id;
	
	@NotBlank(message = "Prencha o título da música")
	@Size(min = 5 ,max = 50, message = "O título da música deve ter no máximo 50 caracteres")
	private String titulo;
	
	// minutos is optional in input; if missing controller will set a default. Keep @Min to validate positive values when provided.
	@Min(value = 1, message = "A duração deve ser maior que zero")
	private Integer minutos; 
	
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Preencha o gênero musical")
	private GeneroMusical genero;
	
	@ManyToMany
	@JoinTable(
	    name = "musica_artista",
	    joinColumns = @JoinColumn(name = "musica_id"),
	    inverseJoinColumns = @JoinColumn(name = "artista_id")
	)

	private Set<Artista> artistas;
	
	@ManyToMany(mappedBy ="musicas")
	private Set<Playlist> playlists;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Integer getMinutos() {
		return minutos;
	}
	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}
	public GeneroMusical getGenero() {
		return genero;
	}
	public void setGenero(GeneroMusical genero) {
		this.genero = genero;
	}
	
	public Set<Artista> getArtistas() {
		return artistas;
	}
	public void setArtistas(Set<Artista> artistas) {
		this.artistas = artistas;
	}
	public Set<Playlist> getPlaylists() {
		return playlists;
	}
	public void setPlaylists(Set<Playlist> playlists) {
		this.playlists = playlists;
	}

	}