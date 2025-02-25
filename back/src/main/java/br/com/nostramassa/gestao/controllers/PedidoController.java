package br.com.nostramassa.gestao.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.pedido.PedidoDTO;
import br.com.nostramassa.gestao.dtos.pedido.PedidoItemDTO;
import br.com.nostramassa.gestao.enums.StatusPedidoEnum;
import br.com.nostramassa.gestao.services.ImpressoraService;
import br.com.nostramassa.gestao.services.PedidoService;
import dev.pedrofreitas.core.dtos.restResponse.ResponseDTO;
import dev.pedrofreitas.core.dtos.restResponse.ResponseMessageDTO;
import dev.pedrofreitas.core.enums.TypeMsgEnum;

@RestController
@RequestMapping(value = "api/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ImpressoraService impressoraService;
	
	@GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<PedidoDTO>>> lista(
			Pageable pageable
			) {
		Page<PedidoDTO> pedidos = pedidoService.getAll(PageRequest.of(0, 100));
		return new ResponseDTO<Page<PedidoDTO>>().ok(pedidos, null);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<ResponseDTO<PedidoDTO>> busca(
			@PathVariable Long id) {
		return new ResponseDTO<PedidoDTO>().ok(pedidoService.getDadosPedido(id), null);
	}
	
	@GetMapping(path = "/telefone/{telefone}")
	public ResponseEntity<ResponseDTO<Page<PedidoDTO>>> getPedidoByTelefone(
			@PathVariable String telefone,
			Pageable pageable) {
		return new ResponseDTO<Page<PedidoDTO>>().ok(pedidoService.getPedidosByTelefone(PageRequest.of(0, 100),telefone), null);
	}
	
	@PutMapping(path = "/cancela/{id}")
	public ResponseEntity<ResponseDTO<PedidoDTO>> cancelaPedido(
			@PathVariable Long id) {
		
		PedidoDTO pedido = pedidoService.cancelaPedido(id);
		ResponseMessageDTO msg = new ResponseMessageDTO(TypeMsgEnum.SUCCESS);
		if(pedido != null) {
			msg.setTitle("Pedido cancelado com sucesso");
		} else {
			msg.setTypeMsg(TypeMsgEnum.ERROR);
			msg.setTitle("Registro não encontrado");
		}
		
		return new ResponseDTO<PedidoDTO>().ok(pedido, Arrays.asList(msg));
	}
	
	@GetMapping(path = "/diario/{id}")
	public ResponseEntity<ResponseDTO<PedidoDTO>> buscaPedidoDiario(
			@PathVariable Long id) {
		return new ResponseDTO<PedidoDTO>().ok(pedidoService.getDadosPedidoDiario(id), null);
	}
	
	@GetMapping(path = "/datas")
	public ResponseEntity<ResponseDTO<Page<String>>> buscaDatasFiltro() {
		return new ResponseDTO<Page<String>>().ok(pedidoService.getDatasPedidos(), null);
	}
	
	@GetMapping(path = "/datas/{dataReferencia}")
	public ResponseEntity<ResponseDTO<Page<PedidoDTO>>> buscaPedidosbyFiltro(
			@PathVariable String dataReferencia,
			Pageable pageable) {
		return new ResponseDTO<Page<PedidoDTO>>().ok(pedidoService.getPedidosByData(dataReferencia, PageRequest.of(0, 100)), null);
	}
	
	@PostMapping(path = "/novo", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<PedidoDTO>> novoPedido() {
		PedidoDTO pedido = pedidoService.criarPedido();
		return new ResponseDTO<PedidoDTO>().created(pedido, null);
	}
	
	@PutMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<PedidoDTO>> atualizaPedido(
			@PathVariable Long id,
			@RequestBody PedidoDTO pedidoDTO) {
		PedidoDTO pedido = pedidoService.atualiza(pedidoDTO);
		return new ResponseDTO<PedidoDTO>().ok(pedido, null);
	}
	@GetMapping(path = "/itemPedido/{idItem}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<PedidoItemDTO>> getItemPedido(
			@PathVariable Long idItem) {
		return new ResponseDTO<PedidoItemDTO>().ok(pedidoService.getItemPedido(idItem), null);
	}
	
	@DeleteMapping(path = "/itemPedido/{idPedido}/{idItem}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<String>> deletaItemPedido(@PathVariable Long idPedido,
			@PathVariable Long idItem) {
		pedidoService.deletaItemPedido(idPedido, idItem);
		return new ResponseDTO<String>().ok("OK", null);
	}
	@PostMapping(path = "/itemPedido/{idPedido}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<String>> itemPedido(
			@PathVariable Long idPedido,
			@RequestBody PedidoItemDTO pedidoItemDTO) {
		pedidoService.adicionaItemPedido(idPedido,pedidoItemDTO);
		return new ResponseDTO<String>().ok("OK", null);
	}
	
	@PostMapping(path = "/imprimirPedido", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<String>> imprimirPedido(
			@RequestBody PedidoDTO pedido
			) {
		pedido.setStatus(StatusPedidoEnum.CONCLUIDO);
		pedidoService.atualiza(pedido);
		impressoraService.geraPedido(pedido);
		return new ResponseDTO<String>().ok("Imprimindo", null);
	}
	
}
