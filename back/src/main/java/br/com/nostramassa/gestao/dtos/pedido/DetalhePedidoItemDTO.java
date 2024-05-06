package br.com.nostramassa.gestao.dtos.pedido;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalhePedidoItemDTO {

	private List<PedidoItemDTO> itensPedido = new ArrayList<>();	
}
