package br.com.nostramassa.gestao.dtos;

import java.util.List;

import br.com.nostramassa.gestao.dtos.pedido.BebidaDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaAcrescimoDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaSaborDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontDataServiceDTO {
	private List<PizzaSaborDTO> pizzasSabor;
	private List<PizzaAcrescimoDTO> pizzasAcrescimos;
	private List<BebidaDTO> bebidas;
}
