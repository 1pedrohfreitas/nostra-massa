package br.com.nostramassa.gestao.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nostramassa.gestao.dtos.pedido.BairroDTO;
import br.com.nostramassa.gestao.dtos.pedido.RuaDTO;
import br.com.nostramassa.gestao.repositories.BairrosRepository;
import br.com.nostramassa.gestao.repositories.RuasRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private BairrosRepository bairrosRepository;
	
	@Autowired
	private RuasRepository ruasRepository;
	
	public Page<RuaDTO> getRuas(Pageable pageable) {
		List<RuaDTO> lista = new ArrayList<>();
		ruasRepository.findAll(pageable).forEach(item -> {
			RuaDTO ruaDTO = new RuaDTO();
			ruaDTO.setId(item.getId());
			ruaDTO.setNome(item.getNome());
			lista.add(ruaDTO);
		});
		return new PageImpl<>(lista, pageable, lista.size());
	}
	public Page<BairroDTO> getBairros(Pageable pageable) {
		List<BairroDTO> lista = new ArrayList<>();
		bairrosRepository.findAll(pageable).forEach(item -> {
			BairroDTO bairroDTO = new BairroDTO();
			bairroDTO.setId(item.getId());
			bairroDTO.setNome(item.getNome());
			bairroDTO.setValorTaxa(item.getValorTaxa());
			lista.add(bairroDTO);
		});
		return new PageImpl<>(lista, pageable, lista.size());
	}
}
