package br.com.nostramassa.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.ResponseDTO;
import br.com.nostramassa.gestao.dtos.TelegramMessagemDTO;
import br.com.nostramassa.gestao.services.TelegramService;

@RestController
@RequestMapping(value = "/api/telegram")
public class TelegramController {

	@Autowired
	private TelegramService telegramService;

	@PostMapping(path = "")
	public ResponseEntity<ResponseDTO<String>> enviaMensagemTelegram(@RequestBody TelegramMessagemDTO telegramMessagemDTO) {
		telegramService.enviaMensagem(telegramMessagemDTO, 0l);
		return new ResponseDTO<String>().ok("OK", null);
	}
}