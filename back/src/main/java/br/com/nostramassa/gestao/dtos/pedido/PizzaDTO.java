package br.com.nostramassa.gestao.dtos.pedido;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaDTO {

	private Long id;

	private String tamanho;

	private Long valor;

	private List<PizzaSaborDTO> pizzassabores = new ArrayList<>();

	private PedidoDTO pedido;
	
	private List<PizzaAcrescimoDTO> pizzasAcrescimos = new ArrayList<>();

}