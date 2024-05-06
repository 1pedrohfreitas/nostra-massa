package br.com.nostramassa.gestao.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemDTO {

    private Long id;

    private Long idPedido;

    private String descricao;
    
    private String tipo;
    
    private Double valor;
    
    private String tamanho;

    private Long quantidade;
    
    private String nome;
    
    private String pedidoItemPizza;
	
}
