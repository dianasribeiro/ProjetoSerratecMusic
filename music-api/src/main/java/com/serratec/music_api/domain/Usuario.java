package com.serratec.music_api.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario {
    
	@Id // Chave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento
	private Long id;
	
	@NotBlank(message = "Prencha o nome do usuário")
	@Size(min = 3 ,max = 30, message = "O nome do usuário deve ter no máximo 30 caracteres")
	private String nome;
	
	@NotBlank(message = "Prencha o email do usuário")
	@Email(message = "Prencha um email válido")
	@Column(unique = true)
	private String email;
	
	@OneToOne(cascade = CascadeType.ALL) // Faz com que o pefil seja salvo junto com o usuário
	@JoinColumn(name = "perfil_id",referencedColumnName = "id")
	private Perfil perfil;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true) // Um usuário pode ter várias playlists
	@JsonManagedReference // Evita o loop infinito na serialização do JSON
	private List<Playlist> playlists;
	
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Perfil getPerfil() {
		return perfil;
	}


	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}


	public List<Playlist> getPlaylists() {
		return playlists;
	}


	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}
}
