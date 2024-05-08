package br.com.nostramassa.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.ResponseDTO;
import br.com.nostramassa.gestao.dtos.pedido.BairroDTO;
import br.com.nostramassa.gestao.dtos.pedido.RuaDTO;
import br.com.nostramassa.gestao.services.EnderecoService;

@RestController
@RequestMapping(value = "/endereco")
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
	
}