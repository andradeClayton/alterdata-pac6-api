package org.serratec.java2backend.alterdatapac6.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "STATUS", uniqueConstraints={@UniqueConstraint(columnNames={"nome"})})
public class StatusEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	//private String descricao;
	
	@OneToMany(mappedBy = "status")
	@JsonIgnore
	private List<UsuarioEntity> usuarios;
	
	private String emoji;

	public StatusEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public String getEmoji() {
		return emoji;
	}




	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}




	/*
	 * public String getDescricao() { return descricao; }
	 * 
	 * 
	 * 
	 * public void setDescricao(String descricao) { this.descricao = descricao; }
	 */



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

	public List<UsuarioEntity> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioEntity> usuarios) {
		this.usuarios = usuarios;
	}
	
	
	

}
