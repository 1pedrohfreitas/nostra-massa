package br.com.nostramassa.gestao.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramMessagemDTO {
	
	private String chat_id;
	private String text;
	
}
