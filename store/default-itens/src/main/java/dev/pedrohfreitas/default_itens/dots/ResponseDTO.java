package dev.pedrohfreitas.default_itens.dots;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
	
	private T data;
	
	private List<ResponseMessagemDTO> msgs;
	
	public ResponseEntity<T> setResponse(T data, HttpStatus httpStatus){
		ResponseEntity<T> responseEntity = new ResponseEntity<T>(data, httpStatus);
		return responseEntity;
	}
	
	public ResponseEntity<ResponseDTO<T>> ok(T data){
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
		if(data == null) {
			ResponseMessagemDTO msg = new ResponseMessagemDTO();
			msg.setTitulo("Registro não encontrado");
			msg.setTypeMsg("alerta");
			msgs.add(msg);	
		}
		responseDTO.setData(data);
		responseDTO.setMsgs(msgs);
		ResponseEntity<ResponseDTO<T>> responseEntity = new ResponseEntity<ResponseDTO<T>>(responseDTO, HttpStatus.OK);
		
		return responseEntity;
	}
	
	public ResponseEntity<ResponseDTO<T>> ok(T data,List<ResponseMessagemDTO> msgs){
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
		responseDTO.setData(data);
		responseDTO.setMsgs(msgs);
		ResponseEntity<ResponseDTO<T>> responseEntity = new ResponseEntity<ResponseDTO<T>>(responseDTO, HttpStatus.OK);
		
		return responseEntity;
	}
	public ResponseEntity<ResponseDTO<T>> ok(T data, HttpHeaders headers,List<ResponseMessagemDTO> msgs){
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
		ResponseEntity<ResponseDTO<T>> responseEntity = new ResponseEntity<ResponseDTO<T>>(responseDTO, headers, HttpStatus.OK);
		
		return responseEntity;
	}
	public ResponseEntity<ResponseDTO<Page<T>>> ok(Page<T> data){
		ResponseDTO<Page<T>> responseDTO = new ResponseDTO<>();
		if(data.getTotalElements() == 0l) {
			ResponseMessagemDTO msg = new ResponseMessagemDTO();
			msg.setTitulo("Nenhum registro encontrado");
			msg.setTypeMsg("sucesso");
			this.msgs = Arrays.asList(msg);
		}
		responseDTO.setData(data);
		responseDTO.setMsgs(msgs);
		ResponseEntity<ResponseDTO<Page<T>>> responseEntity = new ResponseEntity<ResponseDTO<Page<T>>>(responseDTO, HttpStatus.OK);
		
		return responseEntity;
	}
	public ResponseEntity<ResponseDTO<Page<T>>> ok(Page<T> data,List<ResponseMessagemDTO> msgs){
		ResponseDTO<Page<T>> responseDTO = new ResponseDTO<>();
		responseDTO.setData(data);
		responseDTO.setMsgs(msgs);
		ResponseEntity<ResponseDTO<Page<T>>> responseEntity = new ResponseEntity<ResponseDTO<Page<T>>>(responseDTO, HttpStatus.OK);
		
		return responseEntity;
	}
	public ResponseEntity<ResponseDTO<Page<T>>> ok(Page<T> data, HttpHeaders headers,List<ResponseMessagemDTO> msgs){
		ResponseDTO<Page<T>> responseDTO = new ResponseDTO<>();
		responseDTO.setData(data);
		responseDTO.setMsgs(msgs);
		ResponseEntity<ResponseDTO<Page<T>>> responseEntity = new ResponseEntity<ResponseDTO<Page<T>>>(responseDTO, headers, HttpStatus.OK);
		
		return responseEntity;
	}
	public ResponseEntity<ResponseDTO<T>> created(T data){
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
			ResponseMessagemDTO msg = new ResponseMessagemDTO();
			msg.setTitulo("Cadastrado realizado com sucesso");
			msg.setTypeMsg("sucesso");
		responseDTO.setData(data);
		responseDTO.setMsgs(Arrays.asList(msg));
		ResponseEntity<ResponseDTO<T>> responseEntity = new ResponseEntity<ResponseDTO<T>>(responseDTO, HttpStatus.CREATED);
		return responseEntity;
	}
	public ResponseEntity<ResponseDTO<T>> created(T data, List<ResponseMessagemDTO> msgs){
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
		responseDTO.setData(data);
		responseDTO.setMsgs(msgs);
		ResponseEntity<ResponseDTO<T>> responseEntity = new ResponseEntity<ResponseDTO<T>>(responseDTO, HttpStatus.CREATED);
		return responseEntity;
	}
	public ResponseEntity<ResponseDTO<T>>created(T data, HttpHeaders headers, List<ResponseMessagemDTO> msgs){
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
		responseDTO.setData(data);
		responseDTO.setMsgs(msgs);
		ResponseEntity<ResponseDTO<T>> responseEntity = new ResponseEntity<ResponseDTO<T>>(responseDTO, headers, HttpStatus.CREATED);
		return responseEntity;
	}
}
