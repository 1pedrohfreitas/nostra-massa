package br.com.nostramassa.gestao.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nostramassa.gestao.dtos.FrontDataServiceDTO;
import br.com.nostramassa.gestao.dtos.pedido.BairroDTO;
import br.com.nostramassa.gestao.dtos.pedido.BebidaDTO;
import br.com.nostramassa.gestao.dtos.pedido.BebidaTamanhoValorDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaAcrescimoDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaSaborDTO;
import br.com.nostramassa.gestao.dtos.pedido.PizzaSaborIngredienteDTO;
import br.com.nostramassa.gestao.dtos.pedido.RuaDTO;
import br.com.nostramassa.gestao.models.pedido.PizzaIngrediente;
import br.com.nostramassa.gestao.repositories.BairrosRepository;
import br.com.nostramassa.gestao.repositories.BebidaRepository;
import br.com.nostramassa.gestao.repositories.PizzaAcrescimoRepository;
import br.com.nostramassa.gestao.repositories.PizzaIngredienteRepository;
import br.com.nostramassa.gestao.repositories.PizzaSaborRepository;
import br.com.nostramassa.gestao.repositories.RuasRepository;

@Service
public class FrontGetDataService {

	private List<BairroDTO> bairros = new ArrayList<>();
	private List<RuaDTO> ruas = new ArrayList<>();
	private List<PizzaSaborDTO> pizzasSabor = new ArrayList<>();
	private List<PizzaAcrescimoDTO> pizzasAcrescimos = new ArrayList<>();
	private List<BebidaDTO> bebidas = new ArrayList<>();

	@Autowired
	private RuasRepository ruasRepository;

	@Autowired
	private BairrosRepository bairrosRepository;

	@Autowired
	private PizzaSaborRepository pizzaSaborRepository;

	@Autowired
	private BebidaRepository bebidaRepository;

	@Autowired
	private PizzaIngredienteRepository pizzaIngredienteRepository ;
	
	@Autowired
	private PizzaAcrescimoRepository pizzaAcrescimoRepository;
	
	public void atualizaDadosInicializacao() {
		atualizaRuas();
		atualizaBairros();
		atualizaPizzas();
		atualizaBebidas();
		atualizaAcrecimos();
	}

	public FrontDataServiceDTO getDataToFront() {
		atualizaDadosInicializacao();
		FrontDataServiceDTO frontDataServiceDTO = new FrontDataServiceDTO();
		frontDataServiceDTO.setRuas(ruas);
		frontDataServiceDTO.setBairros(bairros);
		frontDataServiceDTO.setPizzasSabor(pizzasSabor);
		frontDataServiceDTO.setBebidas(bebidas);
		frontDataServiceDTO.setPizzasAcrescimos(pizzasAcrescimos);

		return frontDataServiceDTO;
	}

	public void atualizaRuas() {
		ruas = new ArrayList<>();
		ruasRepository.findAll().forEach(item -> {
			RuaDTO ruaDTO = new RuaDTO();
			ruaDTO.setId(item.getId());
			ruaDTO.setNome(item.getNome());
			ruas.add(ruaDTO);
		});
	}

	public void atualizaBairros() {
		bairros = new ArrayList<>();
		bairrosRepository.getBairrosOrderByMaisUtilizados().forEach(item -> {
			BairroDTO bairroDTO = new BairroDTO();
			bairroDTO.setId(item.getId());
			bairroDTO.setNome(item.getNome());
			bairroDTO.setTaxaEntrega(item.getValorTaxa());
			bairros.add(bairroDTO);
		});
	}
	
	public void atualizaPizzas() {
		pizzasSabor = new ArrayList<>();
		pizzaSaborRepository.findAll().forEach(item -> {
			List<PizzaIngrediente> listaIngredientes = pizzaIngredienteRepository.getIngredientesByIdSabor(item.getId());
			PizzaSaborDTO pizzaSaborDTO = new PizzaSaborDTO();
			pizzaSaborDTO.setId(item.getId());
			pizzaSaborDTO.setNome(item.getNome());
			pizzaSaborDTO.setTamanhoMedia(item.getTamanhoMedia());
			pizzaSaborDTO.setTamanhoGrande(item.getTamanhoGrande());
			pizzaSaborDTO.setTamanhoGigante(item.getTamanhoGigante());
			
			Map<String,PizzaSaborIngredienteDTO> ingredientes = new LinkedHashMap<>();

			for (var ingrediente : listaIngredientes) {
				PizzaSaborIngredienteDTO pizzaSaborIngredienteDTO = new PizzaSaborIngredienteDTO();
				pizzaSaborIngredienteDTO.setId(ingrediente.getId());
				pizzaSaborIngredienteDTO.setNome(ingrediente.getNome());
				pizzaSaborIngredienteDTO.setHabilitado(false);
				ingredientes.put(ingrediente.getNome(),pizzaSaborIngredienteDTO);
			}
			pizzaSaborDTO.setPizzaSaborIngredientes(ingredientes);

			pizzasSabor.add(pizzaSaborDTO);
		});
	}
	
	public void atualizaAcrecimos() {
		pizzasAcrescimos = new ArrayList<>();
		pizzaAcrescimoRepository.findAll().forEach(item -> {
			PizzaAcrescimoDTO pizzaAcrescimoDTO = new PizzaAcrescimoDTO();
			pizzaAcrescimoDTO.setId(item.getId());
			pizzaAcrescimoDTO.setNome(item.getNome());
			pizzaAcrescimoDTO.setTamanhoMediaMetade(item.getTamanhoMediaMetade());
			pizzaAcrescimoDTO.setTamanhoMediaToda(item.getTamanhoMediaToda());
			pizzaAcrescimoDTO.setTamanhoGrandeMetade(item.getTamanhoGrandeMetade());
			pizzaAcrescimoDTO.setTamanhoGrandeToda(item.getTamanhoGrandeToda());
			pizzaAcrescimoDTO.setTamanhoGiganteMetade(item.getTamanhoGiganteMetade());
			pizzaAcrescimoDTO.setTamanhoGiganteToda(item.getTamanhoGiganteToda());
			
			pizzasAcrescimos.add(pizzaAcrescimoDTO);
		});

	}
	
	public void atualizaBebidas() {
		bebidas = new ArrayList<>();
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
			bebidas.add(bebidaDTO);
		}
	}
}
