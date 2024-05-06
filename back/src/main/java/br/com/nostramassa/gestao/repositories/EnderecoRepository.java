package br.com.nostramassa.gestao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.cliente.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>  {
	
	@Query(value = "select e.* from cliente c\r\n"
			+ "inner join endereco e on e.id = c.idendereco\r\n"
			+ "where c.id = :idCliente",nativeQuery = true)
	public Optional<Endereco> getEderecoByIdCliente(Long idCliente);
	
//	@Query(value = "select * from cliente c where c.telefone = :telefone limit 1",nativeQuery = true)
//	public Optional<Endereco> getEderecoByAllFields(Long idCliente);

}
