package br.com.nostramassa.gestao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nostramassa.gestao.dtos.FrontDataServiceDTO;
import br.com.nostramassa.gestao.dtos.ResponseDTO;
import br.com.nostramassa.gestao.services.FrontGetDataService;

@RestController
@RequestMapping(value = "/api/getDadosGeral")
public class FrontDataController {
	
	@Autowired
	private FrontGetDataService frontGetDataService;
	
//	@GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<ResponseDTO<FrontDataServiceDTO>> getDadosGeral(){
//		return new ResponseDTO<FrontDataServiceDTO>().ok(frontGetDataService.getDataToFront(), null);
//	}
}
