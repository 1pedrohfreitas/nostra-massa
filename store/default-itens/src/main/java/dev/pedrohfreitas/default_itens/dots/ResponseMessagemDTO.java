package dev.pedrohfreitas.default_itens.dots;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ResponseMessagemDTO {
	String titulo;
	String msg;
	String typeMsg;
}
