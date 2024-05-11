package br.com.nostramassa.gestao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class TelegramService {
	
	@Autowired
	private WebClient webClient;
	
	
	private final String URL_MENSAGEM_LISTA_CONVERSAS = "https://api.telegram.org/bot7114526201:AAGq7RY8-nwhK6H7FB0QTUroHY1vibwlTvo/getUpdates";
	private final String URL_MENSAGEM_TELEGRAM = "https://api.telegram.org/bot7114526201:AAGq7RY8-nwhK6H7FB0QTUroHY1vibwlTvo/sendMessage";
	
	public void enviaMensagem(String value) {
		Mono<String> returnWebClient = this.webClient.method(HttpMethod.POST)
				.uri(URL_MENSAGEM_TELEGRAM)
				.bodyValue(value)
				.retrieve()
				.bodyToMono(String.class);

		returnWebClient.block();
		
	}
	
}
