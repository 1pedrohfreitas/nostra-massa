package br.com.nostramassa.gestao.models.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 150)
	private String nome;
	
	@Column(name = "telefone", nullable = true, length = 30, unique = true)
	private String telefone;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "rua")
	private String rua;

	@Column(name = "numero")
	private String numero;

	@Column(name = "bloco")
	private String bloco;

	@Column(name = "apartamento")
	private String apartamento;

	@Column(name = "complemento")
	private String complemento;
	
	@Column(name = "enderecodescricao")
	private String enderecoDescricao;
	
	@Column(name = "taxaentrega")
	private Double taxaEntrega = 0d;
}