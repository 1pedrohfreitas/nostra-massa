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
@Table(name = "pizza_acrescimo")
public class PizzaAcrescimo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nome", nullable = false, length = 30)
	private String nome;
	
	@Column(name = "tamanho_media_metade")
	private Double tamanhoMediaMetade;
	
	@Column(name = "tamanho_grande_metade")
	private Double tamanhoGrandeMetade;
	
	@Column(name = "tamanho_gigante_metade")
	private Double tamanhoGiganteMetade;
	
	@Column(name = "tamanho_media_toda")
	private Double tamanhoMediaToda;
	
	@Column(name = "tamanho_grande_toda")
	private Double tamanhoGrandeToda;
	
	@Column(name = "tamanho_gigante_toda")
	private Double tamanhoGiganteToda;

}