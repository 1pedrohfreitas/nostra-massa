package dev.pedrohfreitas.genericstore.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pedrohfreitas.default_itens.dots.ResponseDTO;
import dev.pedrohfreitas.genericstore.util.CRUDBasicUtil;

@RestController
@RequestMapping(value = "/api/crud-basic/")
public class CRUDController {

	private final CRUDBasicUtil crudBasicUtil;

	public CRUDController(CRUDBasicUtil crudBasicUtil) {
		this.crudBasicUtil = crudBasicUtil;
	}

	@PostMapping(path = "{modelType}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Object>> create(
			@PathVariable String modelType,
			@RequestBody Object body
			) {
		return new ResponseDTO<Object>().created(crudBasicUtil.create(modelType, body));
	}
	
	@PutMapping(path = "{modelType}/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Object>> put(
			@PathVariable String modelType,
			@RequestBody Object body,
			@PathVariable UUID id
			) {
		return new ResponseDTO<Object>().ok(crudBasicUtil.put(modelType,id, body));
	}

	@GetMapping(path = "{modelType}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Page<Object>>> getAll(
			@PathVariable String modelType,
			@RequestHeader(required = false) Pageable pageable) {
		return new ResponseDTO<Object>().ok(crudBasicUtil.getAll(modelType, null, pageable));
	}
	
	@GetMapping(path = "{modelType}/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseDTO<Object>> getByID(
			@PathVariable String modelType,
			@PathVariable UUID id) {
		return new ResponseDTO<Object>().ok((Object)crudBasicUtil.getByID(modelType,id));		
	}

}
