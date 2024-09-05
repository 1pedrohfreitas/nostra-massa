package dev.pedrohfreitas.genericstore.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageUtil<T>{
	
	public Page<T> convertListToPage(List<T> list, Pageable pageable){
		return new PageImpl<>(list, pageable,list.size());
	}

}
