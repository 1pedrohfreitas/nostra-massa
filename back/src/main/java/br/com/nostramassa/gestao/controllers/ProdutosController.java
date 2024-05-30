package br.com.nostramassa.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.ResponseDTO;
import br.com.nostramassa.gestao.dtos.pedido.BebidaDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaAcrescimoDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaSaborDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaSaborIngredienteDTO;
import br.com.nostramassa.gestao.services.ProdutosService;

@RestController
@RequestMapping(value = "/api/produto")
public class ProdutosController {

	@Autowired
	private ProdutosService produtosService;

	@GetMapping(path = "/acrescimo", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<PizzaAcrescimoDTO>>> getAcrescimos(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		return new ResponseDTO<Page<PizzaAcrescimoDTO>>().ok(produtosService.getAcrescimos(pageable), null);
	}
	
	@PostMapping(path = "/acrescimo", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<PizzaAcrescimoDTO>> cadastraAcrescimos(
			@RequestBody PizzaAcrescimoDTO acrescimo) {
		return new ResponseDTO<PizzaAcrescimoDTO>().ok(produtosService.cadastroAcrescimo(acrescimo), null);
	}
	
	@GetMapping(path = "/ingrediente", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<PizzaSaborIngredienteDTO>>> getIngredientes(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		return new ResponseDTO<Page<PizzaSaborIngredienteDTO>>().ok(produtosService.getIngredientes(pageable), null);
	}
	
	@PostMapping(path = "/ingrediente", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<PizzaSaborIngredienteDTO>> cadastraIngredientes(
			@RequestBody PizzaSaborIngredienteDTO ingredienteDTO) {
		return new ResponseDTO<PizzaSaborIngredienteDTO>().ok(produtosService.cadastroIngredientes(ingredienteDTO), null);
	}
	
	@GetMapping(path = "/pizza", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<PizzaSaborDTO>>> getPizzas(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		return new ResponseDTO<Page<PizzaSaborDTO>>().ok(produtosService.getPizzas(pageable), null);
	}
	
	@PostMapping(path = "/pizza", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<PizzaSaborDTO>> cadastraPizzas(
			@RequestBody PizzaSaborDTO pizza) {
		return new ResponseDTO<PizzaSaborDTO>().ok(produtosService.cadastroPizzaSabor(pizza), null);
	}
	
	@GetMapping(path = "/bebida", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<BebidaDTO>>> getBebidas(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		return new ResponseDTO<Page<BebidaDTO>>().ok(produtosService.getBebidas(pageable), null);
	}
	
	@PostMapping(path = "/bebida", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<BebidaDTO>> cadastraBebida(
			@RequestBody BebidaDTO bebida) {
		return new ResponseDTO<BebidaDTO>().ok(produtosService.cadastroBebida(bebida), null);
	}
	
	@GetMapping(path = "/bebidaTamanho", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> getBebidaTamanho(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		return new ResponseDTO<Page<String>>().ok(produtosService.getBebidaTamanho(pageable), null);
	}

}
