package br.com.nostramassa.gestao.dtos.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioBasicoDTO {

	private Long id;
	private String nome;
	private String conteudo;
	
}
