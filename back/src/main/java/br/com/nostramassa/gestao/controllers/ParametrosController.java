package br.com.nostramassa.gestao.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.ResponseDTO;
import br.com.nostramassa.gestao.dtos.ResponseMessagemDTO;
import br.com.nostramassa.gestao.models.sistema.Parametros;
import br.com.nostramassa.gestao.services.FrontGetDataService;
import br.com.nostramassa.gestao.services.ImpressoraService;
import br.com.nostramassa.gestao.services.ParametroService;

@RestController
@RequestMapping(value = "/api/parametros")
public class ParametrosController {
	
	@Autowired
	private ImpressoraService impressoraService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private FrontGetDataService frontGetDataService;
	
	@GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Parametros>> getParametros() {
		Parametros parametros = parametroService.getParametros();
		return new ResponseDTO<Parametros>().ok(parametros, null);
	}
	@PutMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Parametros>> atualizaParametros(
			@RequestBody Parametros parametros) {
		Parametros parametrosRetorno = parametroService.atualizaParametros(parametros);
		ResponseMessagemDTO msg = new ResponseMessagemDTO();
		msg.setTypeMsg("sucesso");
		msg.setTitulo("Paramêtros Atualizados");
		return new ResponseDTO<Parametros>().ok(parametrosRetorno, Arrays.asList(msg));
	}
	
	@GetMapping(path = "/listaImpressoras", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> listaImpressoras(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		List<String> impressoras = impressoraService.listaImpressoras();
		Page<String> impressorasPage = new PageImpl<>(impressoras, pageable, impressoras.size());
		return new ResponseDTO<Page<String>>().ok(impressorasPage, null);
	}
	
	@PostMapping(path = "/atualizaDadosMemoria", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<String>> atualizaDadosMemoria(
			@RequestBody Parametros parametros) {
		frontGetDataService.atualizaDadosInicializacao();
		ResponseMessagemDTO msg = new ResponseMessagemDTO();
		msg.setTypeMsg("sucesso");
		msg.setTitulo("Dados da Memoria Atualizados");
		return new ResponseDTO<String>().ok("Dados da Memoria Atualizados", Arrays.asList(msg));
	}
	
}
