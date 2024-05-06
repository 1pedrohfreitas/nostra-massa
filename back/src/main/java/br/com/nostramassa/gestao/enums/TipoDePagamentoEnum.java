package br.com.nostramassa.gestao.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDePagamentoEnum {
	
	GERAL(0l,"GERAL"),
	DINHEIRO(1l,"DINHEIRO"),
	CARTAO(2l,"CARTAO"),
	PIX(3l,"PIX");
	
	private final Long codigo;
	private final String descricao;
	
	public static TipoDePagamentoEnum getTipoDePagamentoPorCodigo(Long codigo) {
		return Arrays.stream(TipoDePagamentoEnum.values()).collect(Collectors.toMap(TipoDePagamentoEnum::getCodigo, e -> e))
        .get(codigo);
	}

}
