package br.com.nostramassa.gestao.dtos.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.nostramassa.gestao.enums.StatusPedidoEnum;
import br.com.nostramassa.gestao.enums.TipoDePagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

	private Long id;
	
	private Long idPedido;
	
	private LocalDateTime dataPedido;
	
	private boolean entrega;
	
	private boolean apalito;
	
	private TipoDePagamentoEnum tipoPagamento;
	
	private StatusPedidoEnum status;
	
	private Double valor;
	
	private Double taxaEntrega;
	
	private String enderecoDescricao;
	
	private String observacao;
	
	private Long idCliente;
	
	private String telefone;
	
	private String nome;
	
	private String bairro;

	private String rua;

	private String numero;

	private String bloco;

	private String apartamento;

	private String complemento;
	
	private String pedidoRelatorio;
	
	private List<PedidoItemDTO> itensPedido = new ArrayList<>();
	
}
