package br.com.nostramassa.gestao.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nostramassa.gestao.dtos.pedido.ClienteDTO;
import br.com.nostramassa.gestao.models.cliente.Cliente;
import br.com.nostramassa.gestao.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Page<ClienteDTO> getClientes(Pageable pageable) {
		Page<Cliente> clienteBanco = clienteRepository.getClientes(pageable);
		List<ClienteDTO> clientesDTO = new ArrayList<>();
		for (var cliente : clienteBanco.getContent()) {
			ClienteDTO clienteDTO = new ClienteDTO();
			clienteDTO.setId(cliente.getId());
			clienteDTO.setApartamento(cliente.getApartamento());
			clienteDTO.setBairro(cliente.getBairro());
			clienteDTO.setBloco(cliente.getBloco());
			clienteDTO.setComplemento(cliente.getComplemento());
			clienteDTO.setEnderecoDescricao(cliente.getEnderecoDescricao());
			clienteDTO.setNumero(cliente.getNumero());
			clienteDTO.setRua(cliente.getRua());
			clienteDTO.setTaxaEntrega(cliente.getTaxaEntrega());
			clienteDTO.setNome(cliente.getNome());
			clienteDTO.setTelefone(cliente.getTelefone());
			clientesDTO.add(clienteDTO);
		}
		return new PageImpl<>(clientesDTO, pageable, clienteBanco.getTotalElements());
	}

	public ClienteDTO criarCliente(ClienteDTO cliente) {
		Optional<Cliente> existeCliente = clienteRepository.getClienteByTelefone(cliente.getTelefone());

		if (existeCliente.isPresent()) {
			cliente.setId(existeCliente.get().getId());
		}

		Cliente clienteNovo = new Cliente();
		clienteNovo.setId(cliente.getId());
		clienteNovo.setApartamento(cliente.getApartamento());
		clienteNovo.setBairro(cliente.getBairro());
		clienteNovo.setBloco(cliente.getBloco());
		clienteNovo.setComplemento(cliente.getComplemento());
		clienteNovo.setEnderecoDescricao(cliente.getEnderecoDescricao());
		clienteNovo.setNumero(cliente.getNumero());
		clienteNovo.setRua(cliente.getRua());
		clienteNovo.setTaxaEntrega(cliente.getTaxaEntrega());
		clienteNovo.setNome(cliente.getNome().toUpperCase());
		clienteNovo.setTelefone(cliente.getTelefone());
		clienteRepository.save(clienteNovo);
		cliente.setId(clienteNovo.getId());

		return cliente;
	}

	public String excluirCliente(Long idCliente) {
		Optional<Cliente> existeCliente = clienteRepository.findById(idCliente);

		if (existeCliente.isPresent()) {
			clienteRepository.deleteById(idCliente);
		}
		return "Cliente excluido";
	}
	public ClienteDTO getClienteById(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isPresent()) {
			ClienteDTO clienteDTO = new ClienteDTO();
			clienteDTO.setId(cliente.get().getId());
			clienteDTO.setApartamento(cliente.get().getApartamento());
			clienteDTO.setBairro(cliente.get().getBairro());
			clienteDTO.setBloco(cliente.get().getBloco());
			clienteDTO.setComplemento(cliente.get().getComplemento());
			clienteDTO.setEnderecoDescricao(cliente.get().getEnderecoDescricao());
			clienteDTO.setNumero(cliente.get().getNumero());
			clienteDTO.setRua(cliente.get().getRua());
			clienteDTO.setTaxaEntrega(cliente.get().getTaxaEntrega());
			clienteDTO.setNome(cliente.get().getNome());
			clienteDTO.setTelefone(cliente.get().getTelefone());
			return clienteDTO;
		}

		return null;
	}

	public ClienteDTO getClienteByTelefone(String telefone) {
		Optional<Cliente> cliente = clienteRepository.getClienteByTelefone(telefone);

		if (cliente.isPresent()) {
			ClienteDTO clienteDTO = new ClienteDTO();
			clienteDTO.setId(cliente.get().getId());
			clienteDTO.setApartamento(cliente.get().getApartamento());
			clienteDTO.setBairro(cliente.get().getBairro());
			clienteDTO.setBloco(cliente.get().getBloco());
			clienteDTO.setComplemento(cliente.get().getComplemento());
			clienteDTO.setEnderecoDescricao(cliente.get().getEnderecoDescricao());
			clienteDTO.setNumero(cliente.get().getNumero());
			clienteDTO.setRua(cliente.get().getRua());
			clienteDTO.setTaxaEntrega(cliente.get().getTaxaEntrega());
			clienteDTO.setNome(cliente.get().getNome());
			clienteDTO.setTelefone(cliente.get().getTelefone());
			return clienteDTO;
		}

		return null;
	}

}