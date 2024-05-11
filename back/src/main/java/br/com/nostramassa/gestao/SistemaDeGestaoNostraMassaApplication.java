package br.com.nostramassa.gestao;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;


@SpringBootApplication
public class SistemaDeGestaoNostraMassaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeGestaoNostraMassaApplication.class, args);
	}
	
	@Bean
	WebClient webClient(WebClient.Builder builder) {
		HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofMinutes(2));
		return builder.baseUrl("")
				.clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}

}
