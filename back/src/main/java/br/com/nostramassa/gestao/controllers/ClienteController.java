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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.ResponseDTO;
import br.com.nostramassa.gestao.dtos.ResponseMessagemDTO;
import br.com.nostramassa.gestao.dtos.pedido.ClienteDTO;
import br.com.nostramassa.gestao.dtos.pedido.EnderecoDTO;
import br.com.nostramassa.gestao.services.ClienteService;

@RestController
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping(path = "/cliente", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<ClienteDTO>>> getClientes(Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 10000);
		}
		Page<ClienteDTO> cliente = clienteService.getClientes(pageable);
		return new ResponseDTO<Page<ClienteDTO>>().ok(cliente, null);
	}

	@GetMapping(path = "/cliente/{telefone}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<ClienteDTO>> getClienteByTelefone(@PathVariable String telefone) {
		ClienteDTO cliente = clienteService.getClienteByTelefone(telefone);
		ResponseMessagemDTO msg = new ResponseMessagemDTO();
		if(cliente != null) {
			msg.setTypeMsg("sucesso");
			msg.setTitulo("Cliente encontrado com sucesso");
			msg.setMsg("Cliente encontrado com sucesso");	
		} else {
			msg.setTypeMsg("erro");
			msg.setTitulo("Registro não encontrado");
			msg.setMsg("Registro não encontrado");
		}
		
		return new ResponseDTO<ClienteDTO>().ok(cliente, Arrays.asList(msg));
	}

	@PostMapping(path = "/cliente", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<ClienteDTO>> adicionaCliente(@RequestBody ClienteDTO cliente) {
		ResponseMessagemDTO msg = new ResponseMessagemDTO();
		msg.setTitulo("Cliente cadastrado com sucesso");
		msg.setTypeMsg("sucesso");
		return new ResponseDTO<ClienteDTO>().ok(clienteService.criarCliente(cliente), Arrays.asList(msg));
	}
	@DeleteMapping(path = "/cliente/{idCliente}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<String>> excluirCliente(@PathVariable Long idCliente) {
		ResponseMessagemDTO msg = new ResponseMessagemDTO();
		msg.setTitulo("Cliente excluido com sucesso");
		msg.setTypeMsg("sucesso");
		return new ResponseDTO<String>().ok(clienteService.excluirCliente(idCliente), Arrays.asList(msg));
	}

	@PostMapping(path = "/cliente/endereco/{idCliente}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<EnderecoDTO>> adicionaEnderecoCliente(@RequestBody EnderecoDTO enderecoDTO,
			@PathVariable Long idCliente) {
		return new ResponseDTO<EnderecoDTO>().ok(clienteService.adicionaEnderecoCliente(idCliente, enderecoDTO), null);
	}

//	@PutMapping(path = "/cliente/{idCliente}", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<ResponseDTO<ClienteDTO>> atualizaCliente(@RequestBody ClienteDTO clienteDTO,
//			@PathVariable Long idCliente) {
//		return new ResponseDTO<ClienteDTO>().ok(clienteService.atualizaClienteDTO(idCliente, clienteDTO), null);
//	}
//
//	@GetMapping(path = "/cliente/endereco/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<ResponseDTO<EnderecoDTO>> getEnderecoClienteByID(@PathVariable Long id) {
//		EnderecoDTO endereco = clienteService.getEnderecoClienteByIDCliente(id);
//		ResponseMessagemDTO msg = new ResponseMessagemDTO();
//		msg.setTitulo("Endereço atualizado");
//		msg.setTypeMsg("sucesso");
//		return new ResponseDTO<EnderecoDTO>().ok(endereco, Arrays.asList(msg));
//	}

}
