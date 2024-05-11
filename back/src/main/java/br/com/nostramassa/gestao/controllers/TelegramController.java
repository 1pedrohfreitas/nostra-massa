package br.com.nostramassa.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.services.TelegramService;

@RestController
@RequestMapping(value = "/api/telegram")
public class TelegramController {
	
	@Autowired
	private TelegramService telegramService;
	
	@PostMapping(path = "")
	public String enviaMensagemTelegram(
			@RequestBody String mensagem) {
		telegramService.enviaMensagem(mensagem);
		return "Ola";
	}	
}