package br.com.nostramassa.gestao.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

@Service
public class ConverterDTOUtil {
	
	public static String dateToString(Timestamp data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedString = formatter.format(data);

        return formattedString;
	}
	
//	public static PedidoDTO pedidoToPedidoDTO(Pedido pedido) {
//		PedidoDTO pedidoDTO = new PedidoDTO();
//		pedidoDTO.setId(pedido.getId());
//		pedidoDTO.setIdPedido(pedido.getIdPedido());
//		pedidoDTO.setApalito(pedido.isApalito());
//		pedidoDTO.setNumero(pedido.getNumero());
//		pedidoDTO.setObservacao(pedido.getObservacao());
//		pedidoDTO.setRua(pedido.getRua());
//		pedidoDTO.setApartamento(pedido.getApartamento());
//		pedidoDTO.setBairro(pedido.getBairro());
//		pedidoDTO.setBloco(pedido.getBloco());
//		pedidoDTO.setComplemento(pedido.getComplemento());
//		pedidoDTO.setDataPedido(pedido.getDataPedido());
//		pedidoDTO.setEnderecoDescricao(pedido.getEnderecoDescricao());
//		pedidoDTO.setEntrega(pedido.isEntrega());
//		pedidoDTO.setItensPedido(null);
//		pedidoDTO.setClienteNome(pedido.getNome());
//		pedidoDTO.setClienteTelefone(pedido.getTelefone());
//		pedidoDTO.setStatus(pedido.getStatus());
//		pedidoDTO.setValorTaxa(pedido.getTaxaEntrega());
//		pedidoDTO.setTipoPagamento(pedido.getTipoPagamento());
//		pedidoDTO.setValor(pedido.getValor());
//		return pedidoDTO;
//	}
//	
//	public static Pedido pedidoDTOToPedido(PedidoDTO pedidoDTO) {
//		Pedido pedido = new Pedido();
//		pedido.setId(pedidoDTO.getId());
//		pedido.setIdPedido(pedidoDTO.getIdPedido());
//		pedido.setApalito(pedidoDTO.isApalito());
//		pedido.setNumero(pedidoDTO.getNumero());
//		pedido.setObservacao(pedidoDTO.getObservacao());
//		pedido.setRua(pedidoDTO.getRua());
//		pedido.setApartamento(pedidoDTO.getApartamento());
//		pedido.setBairro(pedidoDTO.getBairro());
//		pedido.setBloco(pedidoDTO.getBloco());
//		pedido.setComplemento(pedidoDTO.getComplemento());
//		pedido.setDataPedido(pedidoDTO.getDataPedido());
//		pedido.setEnderecoDescricao(pedidoDTO.getEnderecoDescricao());
//		pedido.setEntrega(pedidoDTO.isEntrega());
////		pedido.setItensPedido(null);
//		pedido.setNome(pedidoDTO.getClienteNome());
//		pedido.setTelefone(pedidoDTO.getClienteTelefone());
//		pedido.setStatus(pedidoDTO.getStatus());
//		pedido.setTaxaEntrega(pedidoDTO.getValorTaxa());
//		pedido.setTipoPagamento(pedidoDTO.getTipoPagamento());
//		pedido.setValor(pedidoDTO.getValor());
//		return pedido;
//	}
	
}
