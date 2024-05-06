package br.com.nostramassa.gestao.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedidoEnum {
	
	ABERTO("AB","Pedido em Aberto"),
	AGUARDANDO_PREPARO("AP","Aguardando Preparo"),
	AGUARDANDO_ENTREGA("AE","Aguardando Entrega"),
	CONCLUIDO("CO","Pedido Concluído"),
	CANCELADO("CA","Pedido Cancelado");
	
	private final String codigo;
	private final String descricao;
	
	public static StatusPedidoEnum getStatusPedidoPorCodigo(String codigo) {
		return Arrays.stream(StatusPedidoEnum.values()).collect(Collectors.toMap(StatusPedidoEnum::getCodigo, e -> e))
        .get(codigo);
	}
}
