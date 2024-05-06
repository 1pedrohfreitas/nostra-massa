package br.com.nostramassa.gestao.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

	private Long id;
	private BairroDTO bairro;
	private RuaDTO rua;
	private String numero;
	private String bloco;
	private String apartamento;
	private String complemento;
	private String enderecoDescricao;

}
