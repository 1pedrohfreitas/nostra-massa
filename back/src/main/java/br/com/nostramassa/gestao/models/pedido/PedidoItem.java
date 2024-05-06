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
@Table(name = "pedido_item")
public class PedidoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "tipo")
	private String tipo;

	@Column(name = "id_pedido")
    private Long idPedido;
	
	@Column(name = "nome")
    private String nome;
    
	@Column(name = "descricao")
    private String descricao;
    
	@Column(name = "valor")
    private Double valor;
    
	@Column(name = "tamanho")
    private String tamanho;

	@Column(name = "quantidade")
    private Long quantidade;
	
	@Column(name = "pedido_item_pizza")
    private String pedidoItemPizza;
	
}
