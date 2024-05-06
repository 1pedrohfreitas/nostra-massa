package br.com.nostramassa.gestao.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaSaborIngredienteDTO {

	private Long id;

	private String nome;
	
	private Boolean habilitado = false;

}
