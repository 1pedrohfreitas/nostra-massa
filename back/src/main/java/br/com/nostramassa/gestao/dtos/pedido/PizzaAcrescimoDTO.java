package br.com.nostramassa.gestao.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaAcrescimoDTO {

	private Long id;

	private String nome;

	private Double tamanhoMediaMetade;
	
	private Double tamanhoGrandeMetade;
	
	private Double tamanhoGiganteMetade;
	
	private Double tamanhoMediaToda;
	
	private Double tamanhoGrandeToda;
	
	private Double tamanhoGiganteToda;
	
	private Boolean habilitado = false;
	
}