package br.com.nostramassa.gestao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>  {
	
	@Query(value = "select * from cliente c ",nativeQuery = true)
	public Page<Cliente> getClientes(Pageable pageable);
	
	@Query(value = "select distinct c.telefone from cliente c",nativeQuery = true)
	public List<String> getTelefones();
	
	@Query(value = "select * from cliente c where c.telefone = :telefone limit 1",nativeQuery = true)
	public Optional<Cliente> getClienteByTelefone(String telefone);
	
}
