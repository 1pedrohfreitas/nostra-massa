package br.com.nostramassa.gestao.dtos.pedido;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BebidaDTO {
	
	private Long id;

	private String nome;
	
	private List<BebidaTamanhoValorDTO> tamanhoValor;


}