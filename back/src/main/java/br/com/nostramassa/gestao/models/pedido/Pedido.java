package br.com.nostramassa.gestao.models.pedido;

import java.time.LocalDateTime;

import br.com.nostramassa.gestao.enums.StatusPedidoEnum;
import br.com.nostramassa.gestao.enums.TipoDePagamentoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "idpedido")
	private Long idPedido;
	
	@Column(name = "datapedido")
	private LocalDateTime dataPedido = LocalDateTime.now();
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusPedidoEnum status = StatusPedidoEnum.ABERTO;
	
	@Column(name = "entrega")
	private boolean entrega = true;
	
	@Column(name = "apalito")
	private boolean apalito = false;
	
	@Column(name = "tipopagamento")
	@Enumerated(EnumType.STRING)
	private TipoDePagamentoEnum tipoPagamento = TipoDePagamentoEnum.GERAL;
	
	@Column(name = "valor")
	private Double valor = 0d;
	
	@Column(name = "taxaentrega")
	private Double taxaEntrega = 0d;
	
	@Column(name = "enderecodescricao", length = 500)
	private String enderecoDescricao;
	
	@Column(name = "observacao", length = 500)
	private String observacao;
	
	@Column(name = "nome", length = 150)
	private String nome;
	
	@Column(name = "telefone", length = 30)
	private String telefone;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "rua")
	private String rua;

	@Column(name = "numero")
	private String numero;

	@Column(name = "bloco")
	private String bloco;

	@Column(name = "apartamento")
	private String apartamento;

	@Column(name = "complemento")
	private String complemento;
	
}
