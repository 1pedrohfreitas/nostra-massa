package br.com.nostramassa.gestao.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nostramassa.gestao.dtos.pedido.BebidaDTO;
import br.com.nostramassa.gestao.dtos.pedido.BebidaTamanhoValorDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaAcrescimoDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaSaborDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaSaborIngredienteDTO;
import br.com.nostramassa.gestao.repositories.BebidaRepository;
import br.com.nostramassa.gestao.repositories.PizzaAcrescimoRepository;
import br.com.nostramassa.gestao.repositories.PizzaIngredienteRepository;
import br.com.nostramassa.gestao.repositories.PizzaSaborRepository;

@Service
public class ProdutosService {
	
	@Autowired
	private PizzaAcrescimoRepository acrescimoRepository;
	
	@Autowired
	private PizzaIngredienteRepository pizzaIngredienteRepository;
	
	@Autowired
	private PizzaSaborRepository pizzaSaborRepository;
	
	@Autowired
	private BebidaRepository bebidaRepository;
	
	public Page<PizzaAcrescimoDTO> getAcrescimos(Pageable pageable) {
		List<PizzaAcrescimoDTO> lista = new ArrayList<>();
		
		acrescimoRepository.findAll().forEach(item -> {
			PizzaAcrescimoDTO pizzaAcrescimoDTO = new PizzaAcrescimoDTO();
			pizzaAcrescimoDTO.setNome(item.getNome());
			pizzaAcrescimoDTO.setTamanhoMediaMetade(item.getTamanhoMediaMetade());
			pizzaAcrescimoDTO.setTamanhoMediaToda(item.getTamanhoMediaToda());
			pizzaAcrescimoDTO.setTamanhoGrandeMetade(item.getTamanhoGrandeMetade());
			pizzaAcrescimoDTO.setTamanhoGrandeToda(item.getTamanhoGrandeToda());
			pizzaAcrescimoDTO.setTamanhoGiganteMetade(item.getTamanhoGiganteMetade());
			pizzaAcrescimoDTO.setTamanhoGiganteToda(item.getTamanhoGiganteToda());
			
			lista.add(pizzaAcrescimoDTO);
		});
		
		return new PageImpl<>(lista, pageable, lista.size());
	}
	public Page<PizzaSaborIngredienteDTO> getIngredientes(Pageable pageable) {
		List<PizzaSaborIngredienteDTO> lista = new ArrayList<>();
		pizzaIngredienteRepository.findAll(pageable).forEach(item->{
			PizzaSaborIngredienteDTO pizzaSaborIngredienteDTO = new PizzaSaborIngredienteDTO();
			pizzaSaborIngredienteDTO.setId(item.getId());
			pizzaSaborIngredienteDTO.setNome(item.getNome());
			
			lista.add(pizzaSaborIngredienteDTO);
		});
		return new PageImpl<>(lista, pageable, lista.size());
	}
	public Page<PizzaSaborDTO> getPizzas(Pageable pageable) {
		List<PizzaSaborDTO> lista = new ArrayList<>();
		return new PageImpl<>(lista, pageable, lista.size());
	}
	public Page<BebidaDTO> getBebidas(Pageable pageable) {
		List<BebidaDTO> lista = new ArrayList<>();
		Map<String,List<BebidaTamanhoValorDTO>> bebidasMap = new LinkedHashMap<>();
		bebidaRepository.findAll().forEach(item -> {
			BebidaTamanhoValorDTO bebidaTamanhoValorDTO = new BebidaTamanhoValorDTO();
			bebidaTamanhoValorDTO.setTamanho(item.getTamanho());
			bebidaTamanhoValorDTO.setValor(item.getValor());
			
			if(bebidasMap.containsKey(item.getNome())) {
				bebidasMap.get(item.getNome()).add(bebidaTamanhoValorDTO);
			} else {
				bebidasMap.put(item.getNome(),new ArrayList<>());
				bebidasMap.get(item.getNome()).add(bebidaTamanhoValorDTO);
			}
		});
		
		for(var bebida : bebidasMap.entrySet()) {
			BebidaDTO bebidaDTO = new BebidaDTO();
			bebidaDTO.setNome(bebida.getKey());
			bebidaDTO.setTamanhoValor(bebida.getValue());
			lista.add(bebidaDTO);
		}
		return new PageImpl<>(lista, pageable, lista.size());
	}

}
