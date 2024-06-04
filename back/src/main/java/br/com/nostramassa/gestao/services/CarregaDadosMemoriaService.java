package br.com.nostramassa.gestao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarregaDadosMemoriaService implements ApplicationRunner {

	@Autowired
	private FrontGetDataService frontGetDataService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	public void carregaDados() {
		frontGetDataService.atualizaDadosInicializacao();
		enderecoService.atualizaTaxasDeEntrega();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Carregando dados na memória...");
		carregaDados();
	}
}
