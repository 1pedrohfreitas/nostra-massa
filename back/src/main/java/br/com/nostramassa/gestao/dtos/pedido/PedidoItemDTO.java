package br.com.nostramassa.gestao.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemDTO {

    private Long id = 0l; 

    private Long idPedido = 0l;

    private String descricao = "";
    
    private String tipo = "";
    
    private Double valor = 0d;
    
    private String tamanho;

    private Long quantidade = 0l;
    
    private String nome = "";
    
    private String pedidoItemPizza = "";
	
}
