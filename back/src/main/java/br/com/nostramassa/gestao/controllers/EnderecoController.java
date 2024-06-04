package br.com.nostramassa.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.ResponseDTO;
import br.com.nostramassa.gestao.dtos.pedido.BairroDTO;
import br.com.nostramassa.gestao.dtos.pedido.RuaDTO;
import br.com.nostramassa.gestao.services.EnderecoService;

@RestController
@RequestMapping(value = "/api/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@GetMapping(path = "/rua", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<RuaDTO>>> getRuas(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		return new ResponseDTO<Page<RuaDTO>>().ok(enderecoService.getRuas(pageable), null);
	}
	
	@GetMapping(path = "/bairro", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<BairroDTO>>> getBairros(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		return new ResponseDTO<Page<BairroDTO>>().ok(enderecoService.getBairros(pageable), null);
	}
	
	@GetMapping(path = "/rua/autoComplete/{rua}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> autoCompleteRua(Pageable pageable, @PathVariable String rua) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 100);
		}
		return new ResponseDTO<Page<String>>().ok(enderecoService.autoCompleteRua(pageable, rua.replaceAll("_", " ")), null);
	}
	
	@GetMapping(path = "/bairro/autoComplete/{bairro}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> autoCompleteBairro(Pageable pageable, @PathVariable String bairro) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 100);
		}
		return new ResponseDTO<Page<String>>().ok(enderecoService.autoCompleteBairro(pageable, bairro.replaceAll("_", " ")), null);
	}
	
	@GetMapping(path = "/bairro/taxaEntrega/{bairro}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Double>> getTaxaDeEntrega(@PathVariable String bairro) {
		return new ResponseDTO<Double>().ok(enderecoService.getTaxasDeEntrega(bairro.replaceAll("_", " ")), null);
	}
	
}
