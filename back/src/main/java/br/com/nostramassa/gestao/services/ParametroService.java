package br.com.nostramassa.gestao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nostramassa.gestao.models.sistema.Parametros;
import br.com.nostramassa.gestao.repositories.ParametrosRepository;

@Service
public class ParametroService {
	
	@Autowired
	private ParametrosRepository parametrosRepository;
	
	public Parametros getParametros() {
		Optional<Parametros> parametros = parametrosRepository.findById(1l);
		if(parametros.isPresent()) {
			return parametros.get();
		}
		Parametros novoParametro = new Parametros();
		novoParametro.setId(1l);
		novoParametro.setImpressora("");
		novoParametro.setImprimir(false);
		parametrosRepository.save(novoParametro);
		return novoParametro;
	}
	
	public Parametros atualizaParametros(Parametros parametros) {
		parametros.setId(1l);
		parametrosRepository.save(parametros);
		return parametros;
	}

}
