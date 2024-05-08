package br.com.nostramassa.gestao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.cliente.Bairro;

public interface BairrosRepository extends JpaRepository<Bairro, Long>  {
	
	@Query(value = "select id, nome, valortaxa from (\r\n"
			+ "	select b.*, \r\n"
			+ "	(select count(p.bairro) from pedido p where p.bairro = b.nome) as total \r\n"
			+ "	from bairro b) as c order by c.total desc, c.nome asc",nativeQuery = true)
	List<Bairro> getBairrosOrderByMaisUtilizados();
}
