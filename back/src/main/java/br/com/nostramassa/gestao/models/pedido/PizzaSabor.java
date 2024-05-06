package br.com.nostramassa.gestao.models.pedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pizza_sabor")
public class PizzaSabor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "tamanho_media")
	private Double tamanhoMedia;
	
	@Column(name = "tamanho_grande")
	private Double tamanhoGrande;
	
	@Column(name = "tamanho_gigante")
	private Double tamanhoGigante;
	
}
