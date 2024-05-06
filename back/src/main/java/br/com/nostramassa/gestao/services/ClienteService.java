package br.com.nostramassa.gestao.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nostramassa.gestao.dtos.pedido.BairroDTO;
import br.com.nostramassa.gestao.dtos.pedido.ClienteDTO;
import br.com.nostramassa.gestao.dtos.pedido.EnderecoDTO;
import br.com.nostramassa.gestao.dtos.pedido.RuaDTO;
import br.com.nostramassa.gestao.models.cliente.Bairro;
import br.com.nostramassa.gestao.models.cliente.Cliente;
import br.com.nostramassa.gestao.models.cliente.Endereco;
import br.com.nostramassa.gestao.models.cliente.Rua;
import br.com.nostramassa.gestao.repositories.BairrosRepository;
import br.com.nostramassa.gestao.repositories.ClienteRepository;
import br.com.nostramassa.gestao.repositories.EnderecoRepository;
import br.com.nostramassa.gestao.repositories.RuasRepository;
import br.com.nostramassa.gestao.util.ConverterDTOUtil;

@Service
public class ClienteService {
	
	@Autowired
	private FrontGetDataService frontGetDataService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BairrosRepository bairrosRepository;
	
	@Autowired
	private RuasRepository ruasRepository;
	
	@Autowired ConverterDTOUtil converterDTOUtil;
	
	public Page<ClienteDTO> getClientes(Pageable pageable) {
		Page<Cliente> clienteBanco = clienteRepository.getClientes(pageable);
		List<ClienteDTO> clientesDTO = new ArrayList<>();
		for( var cliente : clienteBanco.getContent()) {
			ClienteDTO clienteDTO = new ClienteDTO();
			clienteDTO.setId(cliente.getId());
			if(cliente.getEndereco() != null) {
				Endereco endereco = cliente.getEndereco(); 
				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setId(endereco.getId());
				if(endereco.getBairro() != null) {
					enderecoDTO.setBairro(new BairroDTO(endereco.getBairro().getId(), endereco.getBairro().getNome(), endereco.getBairro().getValorTaxa()));	
				}
				if(endereco.getRua() != null) {
					enderecoDTO.setRua(new RuaDTO(endereco.getRua().getId(), endereco.getRua().getNome()));	
				}
				
				enderecoDTO.setNumero(endereco.getNumero());
				enderecoDTO.setBloco(endereco.getBloco());
				enderecoDTO.setApartamento(endereco.getApartamento());
				enderecoDTO.setComplemento(endereco.getComplemento());
				enderecoDTO.setEnderecoDescricao(endereco.getEnderecoDescricao());
				clienteDTO.setEndereco(enderecoDTO);
			}
			clienteDTO.setNome(cliente.getNome());
			clienteDTO.setTelefone(cliente.getTelefone());
			clientesDTO.add(clienteDTO);
		}
		return new PageImpl<>(clientesDTO, pageable, clienteBanco.getTotalElements());
	}

	public ClienteDTO criarCliente(ClienteDTO cliente) {
		Optional<Cliente> existeCliente = clienteRepository.getClienteByTelefone(cliente.getTelefone());
		
		if(existeCliente.isPresent()) {
			cliente.setId(existeCliente.get().getId());
		} else {
			Cliente clienteNovo = new Cliente();
			clienteNovo.setNome(cliente.getNome().toUpperCase());
			clienteNovo.setTelefone(cliente.getTelefone());
			clienteRepository.save(clienteNovo);
			cliente.setId(clienteNovo.getId());
		}
		return cliente;
	}
	
	public String excluirCliente(Long idCliente) {
		Optional<Cliente> existeCliente = clienteRepository.findById(idCliente);
		
		if(existeCliente.isPresent()) {
			clienteRepository.deleteById(idCliente);
		} 
		return "Cliente excluido";
	}

	public ClienteDTO getClienteByTelefone(String telefone) {
		Optional<Cliente> cliente = clienteRepository.getClienteByTelefone(telefone);
		
		if(cliente.isPresent()) {
			ClienteDTO clienteDTO = new ClienteDTO();
			clienteDTO.setId(cliente.get().getId());
			clienteDTO.setNome(cliente.get().getNome());
			clienteDTO.setTelefone(telefone);
			
			if(cliente.get().getEndereco() != null) {
				Endereco endereco = cliente.get().getEndereco(); 
				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setId(endereco.getId());
				if(endereco.getBairro() != null) {
					enderecoDTO.setBairro(new BairroDTO(endereco.getBairro().getId(), endereco.getBairro().getNome(), endereco.getBairro().getValorTaxa()));	
				}
				if(endereco.getRua() != null) {
					enderecoDTO.setRua(new RuaDTO(endereco.getRua().getId(), endereco.getRua().getNome()));	
				}
				
				enderecoDTO.setNumero(endereco.getNumero());
				enderecoDTO.setBloco(endereco.getBloco());
				enderecoDTO.setApartamento(endereco.getApartamento());
				enderecoDTO.setComplemento(endereco.getComplemento());
				enderecoDTO.setEnderecoDescricao(endereco.getEnderecoDescricao());
				clienteDTO.setEndereco(enderecoDTO);
			}

			return clienteDTO;
		}
		
		return null;
	}

	public EnderecoDTO adicionaEnderecoCliente(Long idCliente, EnderecoDTO enderecoDTO) {
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);
		if(cliente.isPresent()) {
			Endereco endereco = new Endereco();
			endereco.setId(idCliente);
			
			if(enderecoDTO.getBairro()!= null) {
				Optional<Bairro> bairro = bairrosRepository.findById(enderecoDTO.getBairro().getId());
				if(bairro.isPresent()) {
					endereco.setBairro(bairro.get());	
				}	
			}
			if(enderecoDTO.getRua()!= null) {
				Optional<Rua> rua = ruasRepository.findById(enderecoDTO.getRua().getId());
				if(rua.isPresent()) {
					endereco.setRua(rua.get());	
				}	
			}
			endereco.setNumero(enderecoDTO.getNumero());
			endereco.setBloco(enderecoDTO.getBloco());
			endereco.setApartamento(enderecoDTO.getApartamento());
			endereco.setComplemento(enderecoDTO.getComplemento());
			endereco.setEnderecoDescricao(enderecoDTO.getEnderecoDescricao());
			enderecoRepository.save(endereco);
			cliente.get().setEndereco(endereco);
			clienteRepository.save(cliente.get());
		}
		
		return null;
	}

}