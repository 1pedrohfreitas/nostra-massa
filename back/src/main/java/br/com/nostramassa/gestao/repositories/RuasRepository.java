package br.com.nostramassa.gestao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.cliente.Rua;


public interface RuasRepository extends JpaRepository<Rua, Long>  {
	
	@Query(value = "select * from rua r where r.nome like %:item%",nativeQuery = true)
	public Page<Rua> getAutoComplete(Pageable pageable, String item);

}
