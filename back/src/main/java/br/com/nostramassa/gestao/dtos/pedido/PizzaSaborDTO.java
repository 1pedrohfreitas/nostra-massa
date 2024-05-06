package br.com.nostramassa.gestao.dtos.pedido;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaSaborDTO {

	private Long id;
	
	private String nome;
	
	private Double tamanhoMedia;
	
	private Double tamanhoGrande;
	
	private Double tamanhoGigante;
	
	private Map<String,PizzaAcrescimoDTO> pizzasAcrescimos = new LinkedHashMap<>();
	
	private Map<String,PizzaSaborIngredienteDTO> pizzaSaborIngredientes = new LinkedHashMap<>();
	
}
