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

import br.com.nostramassa.gestao.dtos.pedido.BairroDTO;
import br.com.nostramassa.gestao.dtos.pedido.RuaDTO;
import br.com.nostramassa.gestao.services.EnderecoService;
import dev.pedrofreitas.core.dtos.restResponse.ResponseDTO;

@RestController
@RequestMapping(value = "/api/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@GetMapping(path = "/rua", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<RuaDTO>>> getRuas(Pageable pageable) {
		return new ResponseDTO<Page<RuaDTO>>().ok(enderecoService.getRuas(PageRequest.of(0, 2000)), null);
	}
	
	@GetMapping(path = "/bairro", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<BairroDTO>>> getBairros(Pageable pageable) {
		return new ResponseDTO<Page<BairroDTO>>().ok(enderecoService.getBairros(PageRequest.of(0, 1000)), null);
	}

	@GetMapping(path = "/bairroMaisUtilizados", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> bairroMaisUtilizados(Pageable pageable) {
		return new ResponseDTO<Page<String>>().ok(enderecoService.getBairrosMaisUtilizados(PageRequest.of(0, 50)), null);
	}
	
	@GetMapping(path = "/bairroMaisUtilizados/autoComplete/{bairro}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> autoCompleteBairroMaisUtilizados(Pageable pageable, @PathVariable String bairro) {
		return new ResponseDTO<Page<String>>().ok(enderecoService.autoCompleteBairrosMaisUtilizados(PageRequest.of(0, 20),bairro), null);
	}
	
	@GetMapping(path = "/rua/autoComplete/{rua}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> autoCompleteRua(Pageable pageable, @PathVariable String rua) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 50);
		}
		return new ResponseDTO<Page<String>>().ok(enderecoService.autoCompleteRua(PageRequest.of(0, 100), rua.replaceAll("_", " ")), null);
	}
	
	@GetMapping(path = "/bairro/autoComplete/{bairro}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> autoCompleteBairro(Pageable pageable, @PathVariable String bairro) {
		return new ResponseDTO<Page<String>>().ok(enderecoService.autoCompleteBairro(PageRequest.of(0, 100), bairro.replaceAll("_", " ")), null);
	}
	
	@GetMapping(path = "/bairro/taxaEntrega/{bairro}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Double>> getTaxaDeEntrega(@PathVariable String bairro) {
		return new ResponseDTO<Double>().ok(enderecoService.getTaxasDeEntrega(bairro.replaceAll("_", " ")), null);
	}
	
}
