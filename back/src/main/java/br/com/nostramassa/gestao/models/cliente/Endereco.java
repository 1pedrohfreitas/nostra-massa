package br.com.nostramassa.gestao.models.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Bairro bairro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Rua rua;

	@Column(name = "numero", nullable = false, length = 30)
	private String numero;

	@Column(name = "bloco", nullable = true, length = 20)
	private String bloco;

	@Column(name = "apartamento", nullable = true, length = 20)
	private String apartamento;

	@Column(name = "complemento", nullable = true, length = 300)
	private String complemento;
	
	@Column(name = "endereco_descricao", nullable = true, length = 300)
	private String enderecoDescricao;

}
