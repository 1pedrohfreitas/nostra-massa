package br.com.nostramassa.gestao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.nostramassa.gestao.dtos.TelegramMessagemDTO;
import br.com.nostramassa.gestao.models.sistema.Parametros;
import br.com.nostramassa.gestao.repositories.ParametrosRepository;
import io.netty.resolver.dns.DnsNameResolverTimeoutException;
import reactor.core.publisher.Mono;

@Service
public class TelegramService {
	
	@Autowired
	private WebClient webClient;
	
	@Autowired
	private ParametrosRepository parametrosRepository;
	private final String URL_BASE_API_TELEGRAM = "https://api.telegram.org/bot";
	private final String URL_LISTA_CONVERSAS = "/getUpdates";
	private final String URL_MENSAGEM_TELEGRAM = "/sendMessage";
	
	public void enviaMensagem(TelegramMessagemDTO mensagem, Long tentativa) {
		if(tentativa == null) {
			tentativa = 0l;
		}
		
		Parametros parametros = parametrosRepository.findById(1l).get();
		
		if(tentativa < 5) {
			try {
				Mono<String> returnWebClient = this.webClient.method(HttpMethod.POST)
						.uri(URL_BASE_API_TELEGRAM + parametros.getTelegramApiToken()+URL_MENSAGEM_TELEGRAM)
						.bodyValue(mensagem)
						.retrieve()
						.bodyToMono(String.class);

				returnWebClient.block();	
			} catch (DnsNameResolverTimeoutException e) {
				tentativa = tentativa + 1;
				System.out.print("Vou tentar de novo " + tentativa.toString());
				enviaMensagem(mensagem, tentativa);
			}			
		}
	}
	
}
