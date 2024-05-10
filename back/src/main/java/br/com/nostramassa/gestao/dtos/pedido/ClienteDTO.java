package br.com.nostramassa.gestao.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

	private Long id;
	private String nome;
	private String telefone;
	private String bairro;
	private String rua;
	private String numero;
	private String bloco;
	private String apartamento;
	private String complemento;
	private String enderecoDescricao;
	private Double taxaEntrega = 0d;
}
