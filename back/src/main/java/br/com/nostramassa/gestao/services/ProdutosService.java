package br.com.nostramassa.gestao.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
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
import br.com.nostramassa.gestao.models.pedido.Bebida;
import br.com.nostramassa.gestao.models.pedido.PizzaAcrescimo;
import br.com.nostramassa.gestao.models.pedido.PizzaIngrediente;
import br.com.nostramassa.gestao.models.pedido.PizzaSabor;
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
		ModelMapper modelMapper = new ModelMapper();
		acrescimoRepository.findAll().forEach(item -> {
			lista.add(modelMapper.map(item, PizzaAcrescimoDTO.class));
		});
		
		return new PageImpl<>(lista, pageable, lista.size());
	}
	
	public PizzaAcrescimoDTO cadastroAcrescimo(PizzaAcrescimoDTO pizzaAcrescimoDTO) {
		ModelMapper modelMapper = new ModelMapper();
		PizzaAcrescimo pizzaAcrescimo = modelMapper.map(pizzaAcrescimoDTO, PizzaAcrescimo.class);
		acrescimoRepository.save(pizzaAcrescimo);
		return pizzaAcrescimoDTO;
	}
	public Page<PizzaSaborIngredienteDTO> getIngredientes(Pageable pageable) {
		ModelMapper modelMapper = new ModelMapper();
		List<PizzaSaborIngredienteDTO> lista = new ArrayList<>();
		pizzaIngredienteRepository.findAll(pageable).forEach(item->{
			lista.add(modelMapper.map(item, PizzaSaborIngredienteDTO.class));
		});
		return new PageImpl<>(lista, pageable, lista.size());
	}
	
	public PizzaSaborIngredienteDTO cadastroIngredientes(PizzaSaborIngredienteDTO pizzaSaborIngredienteDTO) {
		ModelMapper modelMapper = new ModelMapper();
		PizzaIngrediente pizzaIngrediente = modelMapper.map(pizzaSaborIngredienteDTO, PizzaIngrediente.class);
		pizzaIngredienteRepository.save(pizzaIngrediente);
		return pizzaSaborIngredienteDTO;
	}
	
	public Page<PizzaSaborDTO> getPizzas(Pageable pageable) {
		List<PizzaSaborDTO> lista = new ArrayList<>();
		return new PageImpl<>(lista, pageable, lista.size());
	}
	
	public PizzaSaborDTO cadastroPizzaSabor(PizzaSaborDTO pizzaSaborDTO) {
		ModelMapper modelMapper = new ModelMapper();
		PizzaSabor pizzaSabor = modelMapper.map(pizzaSaborDTO, PizzaSabor.class);
		pizzaSaborRepository.save(pizzaSabor);
		return pizzaSaborDTO;
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
	
	public BebidaDTO cadastroBebida(BebidaDTO bebidaDTO) {
		ModelMapper modelMapper = new ModelMapper();
		Bebida bebida = modelMapper.map(bebidaDTO, Bebida.class);
		bebidaRepository.save(bebida);
		return bebidaDTO;
	}
	
	public Page<String> getBebidaTamanho(Pageable pageable) {
		List<String> lista = new ArrayList<>();
		Map<String,String> bebidasMap = new LinkedHashMap<>();
		bebidaRepository.findAll().forEach(item -> {
			bebidasMap.put(item.getTamanho(),item.getTamanho());
		});
		
		for(var bebida : bebidasMap.entrySet()) {
			lista.add(bebida.getValue());
		}
		return new PageImpl<>(lista, pageable, lista.size());
	}

}
