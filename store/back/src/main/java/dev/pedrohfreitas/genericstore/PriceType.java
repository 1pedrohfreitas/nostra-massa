package dev.pedrohfreitas.genericstore;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PriceType {
	AP("AB","Average Price","Preço Médio"),
	HP("HP","Highest Price","Maior Preço"),
	LP("LP","Lowest price","Menor Preço"),
	FI("FI","Fixed","Fixo");
	
	private final String code;
	private final String description;
	private final String descriptionPortuguese;
	
	public static PriceType getStatusPedidoPorCodigo(String code) {
		return Arrays.stream(PriceType.values()).collect(Collectors.toMap(PriceType::getCode, e -> e))
        .get(code);
	}
}
