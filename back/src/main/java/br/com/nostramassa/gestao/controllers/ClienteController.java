package br.com.nostramassa.gestao.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.pedido.ClienteDTO;
import br.com.nostramassa.gestao.enums.TipoDePagamentoEnum;
import br.com.nostramassa.gestao.services.ClienteService;
import dev.pedrofreitas.core.dtos.restResponse.ResponseDTO;
import dev.pedrofreitas.core.dtos.restResponse.ResponseMessageDTO;
import dev.pedrofreitas.core.dtos.utils.EnumListDto;
import dev.pedrofreitas.core.enums.TypeMsgEnum;
import dev.pedrofreitas.core.utils.EnumsUtil;

@RestController
@RequestMapping(value = "/api/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<ClienteDTO>>> getClientes(Pageable pageable) {
		List<EnumListDto> listDto = new ArrayList<>();
		try {
			listDto = EnumsUtil.enumsToList(TipoDePagamentoEnum.class);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Page<ClienteDTO> cliente = clienteService.getClientes(pageable);
		return new ResponseDTO<Page<ClienteDTO>>().ok(cliente, null);
	}

	@GetMapping(path = "/telefone/{telefone}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<ClienteDTO>> getClienteByTelefone(@PathVariable String telefone) {
		ClienteDTO cliente = clienteService.getClienteByTelefone(telefone);
		ResponseMessageDTO msg = new ResponseMessageDTO(TypeMsgEnum.SUCCESS);
		if(cliente != null) {
			msg.setTitle("Cliente encontrado com sucesso");
			msg.setMsg("Cliente encontrado com sucesso");	
		} else {
			msg.setTypeMsg(TypeMsgEnum.ERROR);
			msg.setTitle("Registro não encontrado");
			msg.setMsg("Registro não encontrado");
		}
		
		return new ResponseDTO<ClienteDTO>().ok(cliente, Arrays.asList(msg));
	}
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<ClienteDTO>> getClienteById(@PathVariable Long id) {
		ClienteDTO cliente = clienteService.getClienteById(id);
		return new ResponseDTO<ClienteDTO>().ok(cliente, null);
	}

	@PostMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<ClienteDTO>> adicionaCliente(@RequestBody ClienteDTO cliente) {
		return new ResponseDTO<ClienteDTO>().ok(clienteService.criarCliente(cliente), Arrays.asList(new ResponseMessageDTO(TypeMsgEnum.SUCCESS)));
	}
	@DeleteMapping(path = "/{idCliente}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<String>> excluirCliente(@PathVariable Long idCliente) {
		return new ResponseDTO<String>().ok(clienteService.excluirCliente(idCliente), Arrays.asList(new ResponseMessageDTO(TypeMsgEnum.SUCCESS)));
	}
	@GetMapping(path = "/telefone/autoComplete/{telefone}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<String>>> autoCompleteTelefone(Pageable pageable, @PathVariable String telefone) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 100);
		}
		return new ResponseDTO<Page<String>>().ok(clienteService.autoCompleteTelefone(pageable, telefone.replaceAll("_", " ")), null);
	}

}
