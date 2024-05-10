package br.com.nostramassa.gestao.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BairroDTO {

	private Long id;
	private String nome;
	private Double taxaEntrega;
	
}
