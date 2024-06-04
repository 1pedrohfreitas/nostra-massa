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

import br.com.nostramassa.gestao.dtos.pedido.BairroDTO;
import br.com.nostramassa.gestao.dtos.pedido.RuaDTO;
import br.com.nostramassa.gestao.models.cliente.Bairro;
import br.com.nostramassa.gestao.models.cliente.Rua;
import br.com.nostramassa.gestao.repositories.BairrosRepository;
import br.com.nostramassa.gestao.repositories.RuasRepository;

@Service
public class EnderecoService {
	
	private Map<String, Double> valorTaxaEntrega= new LinkedHashMap<>();
	
	private final ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private BairrosRepository bairrosRepository;
	
	@Autowired
	private RuasRepository ruasRepository;
	
	public Page<RuaDTO> getRuas(Pageable pageable) {
		List<RuaDTO> lista = new ArrayList<>();
		ruasRepository.findAll(pageable).forEach(item -> {
			lista.add(mapper.map(item, RuaDTO.class));
		});
		
		
		return new PageImpl<>(lista, pageable, lista.size());
	}
	public Page<BairroDTO> getBairros(Pageable pageable) {
		List<BairroDTO> lista = new ArrayList<>();
		bairrosRepository.findAll(pageable).forEach(item -> {
			lista.add(mapper.map(item, BairroDTO.class));
		});
		return new PageImpl<>(lista, pageable, lista.size());
	}
	public Page<String> autoCompleteRua(Pageable pageable, String itemFiltro) {
		List<String> lista = new ArrayList<>();
		ruasRepository.getAutoComplete(pageable, itemFiltro).forEach(item -> {
			lista.add(item.getNome());
		});
		
		
		return new PageImpl<>(lista, pageable, lista.size());
	}
	public Page<String> autoCompleteBairro(Pageable pageable, String itemFiltro) {
		List<String> lista = new ArrayList<>();
		bairrosRepository.getAutoComplete(pageable,itemFiltro).forEach(item -> {
			lista.add(item.getNome());
		});
		return new PageImpl<>(lista, pageable, lista.size());
	}
	
	public void atualizaTaxasDeEntrega() {
		valorTaxaEntrega = new LinkedHashMap<>();
		bairrosRepository.getBairrosOrderByMaisUtilizados().forEach(item -> {
			valorTaxaEntrega.put(item.getNome(), item.getValorTaxa());
		});
	}
	
	public Double getTaxasDeEntrega(String bairro) {
		if(valorTaxaEntrega.containsKey(bairro)) {
			return valorTaxaEntrega.get(bairro);	
		}
		return 0.0;
	}
	
	public void adicionaBairro(BairroDTO bairro) {
		bairrosRepository.save(mapper.map(bairro, Bairro.class));
		atualizaTaxasDeEntrega();
	}
	
	public void adicionaRua(RuaDTO rua) {
		ruasRepository.save(mapper.map(rua, Rua.class));
	}
}
