package br.com.nostramassa.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.services.ImpressoraService;

@RestController
public class PrintController {
	
	@Autowired
	private ImpressoraService impressoraService;
	
	@GetMapping(path = "/printer")
	public String list() {
		impressoraService.geraRelatorio(null);
		return "Ola";
	}
	
}