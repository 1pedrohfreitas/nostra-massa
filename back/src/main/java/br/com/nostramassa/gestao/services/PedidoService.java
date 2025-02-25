package br.com.nostramassa.gestao.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nostramassa.gestao.dtos.pedido.PedidoDTO;
import br.com.nostramassa.gestao.dtos.pedido.PedidoItemDTO;
import br.com.nostramassa.gestao.enums.StatusPedidoEnum;
import br.com.nostramassa.gestao.models.pedido.Pedido;
import br.com.nostramassa.gestao.models.pedido.PedidoItem;
import br.com.nostramassa.gestao.repositories.PedidoItemRepository;
import br.com.nostramassa.gestao.repositories.PedidoRepository;

@Service
public class PedidoService {
	
	private final ModelMapper mapper = new ModelMapper();

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoItemRepository pedidoItemRepository;
	
	public PedidoDTO criarPedido() {
		PedidoDTO pedidoDTO = new PedidoDTO();
		Pedido pedido = new Pedido();
		Optional<Pedido> ultimoPedido = pedidoRepository.getUltimoPedidoNoite();
		Long idPedido = 1l;
		if (ultimoPedido.isPresent()) {

			if (ultimoPedido.get().getStatus().equals(StatusPedidoEnum.ABERTO)) {
				pedido = ultimoPedido.get();
				pedidoDTO = getDadosPedido(ultimoPedido.get().getId()); 
				return pedidoDTO;
			} else {
				idPedido = ultimoPedido.get().getIdPedido() + 1;
			}

		}

		pedido.setIdPedido(idPedido);
		pedido.setDataPedido(LocalDateTime.now());
		pedido = pedidoRepository.save(pedido);
		pedidoDTO.setId(pedido.getId());
		
		pedidoDTO = mapper.map(pedido, PedidoDTO.class);
		
		return pedidoDTO;
	}

	public PedidoDTO atualiza(PedidoDTO pedidoDTO) {
		Pedido getPedido = pedidoRepository.findById(pedidoDTO.getId()).get();
		Pedido pedido = mapper.map(pedidoDTO, Pedido.class);

		pedido.setDataPedido(getPedido.getDataPedido());
		if (pedidoDTO.getStatus() != null) {
			pedido.setStatus(pedidoDTO.getStatus());
		} else {
			pedido.setStatus(getPedido.getStatus());
		}
		pedidoRepository.save(pedido);
		
		return pedidoDTO;
	}

	public PedidoDTO cancelaPedido(Long idPedido) {
		PedidoDTO pedidoDTO = new PedidoDTO();
		Optional<Pedido> getPedido = pedidoRepository.getPedidoNoite(idPedido);
		if (getPedido.isPresent()) {
			Pedido pedido = getPedido.get();
			pedido.setStatus(StatusPedidoEnum.CANCELADO);
			pedidoRepository.save(pedido);
			pedidoDTO = mapper.map(pedido, PedidoDTO.class);
			return pedidoDTO;
		}
		return null;
	}

	public PedidoDTO getDadosPedido(Long id) {
		Optional<Pedido> pedidoBanco = pedidoRepository.findById(id);
		PedidoDTO pedidoDTO = new PedidoDTO();
		if(pedidoBanco.isPresent()) {
			pedidoDTO = mapper.map(pedidoBanco.get(), PedidoDTO.class);
		}
		pedidoDTO.setItensPedido(new ArrayList<>());
		
		List<PedidoItem> itensPedido = pedidoItemRepository.getItensPedido(id);
		
		for(var itemPedido : itensPedido) {
			pedidoDTO.getItensPedido().add(mapper.map(itemPedido, PedidoItemDTO.class));
		}
		
		
		return pedidoDTO;
	}

	public PedidoDTO getDadosPedidoDiario(Long id) {
		Optional<Pedido> pedidoBanco = pedidoRepository.getPedidoNoite(id);
		PedidoDTO pedidoDTO = new PedidoDTO();
		if (pedidoBanco.isPresent()) {
			pedidoDTO = mapper.map(pedidoBanco.get(), PedidoDTO.class);
		}
		pedidoDTO.setItensPedido(new ArrayList<>());

		List<PedidoItem> itensPedido = pedidoItemRepository.getItensPedido(id);

		for (PedidoItem item : itensPedido) {
			pedidoDTO.getItensPedido().add(mapper.map(item, PedidoItemDTO.class));
		}
		return pedidoDTO;
	}

	public Page<String> getDatasPedidos() {
		Pageable pageable = PageRequest.of(0, 10000);
		
		List<String> resultado = pedidoRepository.getAllDatasReferencia(); 
		Page<String> pageDTO = new PageImpl<>(resultado, pageable, resultado.size());
		return pageDTO;
	}

	public Page<PedidoDTO> getPedidosByData(String dataReferencia, Pageable pageable) {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("uuuu-MM-dd");
		
		LocalDateTime inicio = LocalDate.parse(dataReferencia, parser).atStartOfDay();
		LocalDateTime fim = inicio.plusDays(1);
		Page<Pedido> pedidos = pedidoRepository.getPedidosByDatasReferencia(inicio, fim,pageable);
		List<PedidoDTO> listaPedido = new ArrayList<>();
		pedidos.forEach(item -> {
			PedidoDTO pedidoDTO = mapper.map(item, PedidoDTO.class);
			listaPedido.add(pedidoDTO);
		});
		Page<PedidoDTO> pageDTO = new PageImpl<>(listaPedido, pageable, listaPedido.size());
		return pageDTO;
	}

	public Page<PedidoDTO> getAll(Pageable pageable) {

		Page<Pedido> page = pedidoRepository.getAllPedidos(pageable);
		List<PedidoDTO> listaPedido = new ArrayList<>();
		page.getContent().forEach(item -> {
			PedidoDTO pedidoDTO = mapper.map(item, PedidoDTO.class);
			listaPedido.add(pedidoDTO);
		});
		Page<PedidoDTO> pageDTO = new PageImpl<>(listaPedido, pageable, page.getTotalElements());
		return pageDTO;
	}
	
	public Page<PedidoDTO> getPedidosByTelefone(Pageable pageable, String telefone) {

		Page<Pedido> page = pedidoRepository.getAllPedidosByTelefone(pageable, telefone);
		List<PedidoDTO> listaPedido = new ArrayList<>();
		page.getContent().forEach(item -> {
			PedidoDTO pedidoDTO = mapper.map(item, PedidoDTO.class);
			listaPedido.add(pedidoDTO);
		});
		Page<PedidoDTO> pageDTO = new PageImpl<>(listaPedido, pageable, page.getTotalElements());
		return pageDTO;
	}

	public void adicionaItemPedido(Long idPedido, PedidoItemDTO pedidoItemDTO) {
		PedidoItem pedidoItem = new PedidoItem();
		pedidoItem.setIdPedido(idPedido);
		if (pedidoItemDTO.getId() != null && pedidoItemDTO.getId() != 0) {
			Optional<PedidoItem> pedidoItemBanco = pedidoItemRepository.findById(pedidoItemDTO.getId());
			if (pedidoItemBanco.isPresent()) {
				pedidoItem = pedidoItemBanco.get();

			}
		}
		pedidoItem.setDescricao(pedidoItemDTO.getDescricao());
		pedidoItem.setTipo(pedidoItemDTO.getTipo());
		pedidoItem.setQuantidade(pedidoItemDTO.getQuantidade());
		pedidoItem.setTamanho(pedidoItemDTO.getTamanho());
		pedidoItem.setValor(pedidoItemDTO.getValor());
		pedidoItem.setNome(pedidoItemDTO.getNome());
		pedidoItem.setPedidoItemPizza(pedidoItemDTO.getPedidoItemPizza());

		pedidoItemRepository.save(pedidoItem);

	}

	public void deletaItemPedido(Long idPedido, Long idItem) {
		pedidoItemRepository.deleteById(idItem);
	}

	public PedidoItemDTO getItemPedido(Long idItem) {
		PedidoItemDTO pedidoItemDTO = new PedidoItemDTO();
		if(idItem != 0) {
			PedidoItem item = new PedidoItem();
			Optional<PedidoItem> pedidoItem = pedidoItemRepository.findById(idItem);
			if (pedidoItem.isPresent()) {
				item = pedidoItem.get();
			}
			
			pedidoItemDTO.setDescricao(item.getDescricao());
			pedidoItemDTO.setId(item.getId());
			pedidoItemDTO.setIdPedido(item.getIdPedido());
			pedidoItemDTO.setNome(item.getNome());
			pedidoItemDTO.setQuantidade(item.getQuantidade());
			pedidoItemDTO.setTamanho(item.getTamanho());
			pedidoItemDTO.setTipo(item.getTipo());
			pedidoItemDTO.setValor(item.getValor());
			pedidoItemDTO.setPedidoItemPizza(item.getPedidoItemPizza());	
		}
		
		return pedidoItemDTO;
	}

}
