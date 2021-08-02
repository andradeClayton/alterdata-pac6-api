package org.serratec.java2backend.alterdatapac6.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "USUARIO", uniqueConstraints={@UniqueConstraint(columnNames={"userName","email","nickName"})})
public class UsuarioEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String nickName;
	
	@OneToOne
	private ImagemEntity imagem;
	
	@ManyToOne 
	@JoinColumn(referencedColumnName = "id")
	private PapelEntity papel;
	
	private String userName;
	
	//@JsonBackReference
	private String password;
	
	
	@ManyToOne()
	@JoinColumn(referencedColumnName = "id")
	private EquipeEntity equipe;
	
	
	@ManyToOne()
	@JoinColumn(referencedColumnName = "id")
	private StatusEntity status;

	private String email;
	
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;
	
	
	
	
	public UsuarioEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public ImagemEntity getImagem() {
		return imagem;
	}





	public void setImagem(ImagemEntity imagem) {
		this.imagem = imagem;
	}





	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Date getDtNascimento() {
		return dtNascimento;
	}



	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}



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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public PapelEntity getPapel() {
		return papel;
	}

	public void setPapel(PapelEntity papel) {
		this.papel = papel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EquipeEntity getEquipe() {
		return equipe;
	}

	public void setEquipe(EquipeEntity equipe) {
		this.equipe = equipe;
	}

	public StatusEntity getStatus() {
		return status;
	}

	public void setStatus(StatusEntity status) {
		this.status = status;
	}





	
	
	
}
