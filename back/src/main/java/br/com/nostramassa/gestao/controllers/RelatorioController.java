package br.com.nostramassa.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.ResponseDTO;
import br.com.nostramassa.gestao.dtos.relatorio.RelatorioBasicoDTO;
import br.com.nostramassa.gestao.services.ImpressoraService;

@RestController
@RequestMapping(value = "/api/relatorio")
public class RelatorioController {
	
	@Autowired
	private ImpressoraService impressoraService;
	
	@PostMapping(path = "")
	public ResponseEntity<ResponseDTO<String>> build(@RequestBody RelatorioBasicoDTO data) {
		impressoraService.geraRelatorio(data.getNome(),data.getConteudo());
		return new ResponseDTO<String>().ok("OK", null);
	}
	
}
