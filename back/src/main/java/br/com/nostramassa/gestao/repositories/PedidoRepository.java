package br.com.nostramassa.gestao.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.pedido.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>  {
	
	@Query(value = "From Pedido")
	public Page<Pedido> getAllPedidos(Pageable pageable);
	
	@Query(value = "select distinct split_part(cast(p.datapedido as varchar),' ',1) from pedido p",nativeQuery = true)
	public Page<String> getAllDatasReferencia(Pageable pageable);
	
	@Query(value = "select * from pedido p where p.datapedido between :inicio and :fim",nativeQuery = true)
	public List<Pedido> getPedidosByDatasReferencia(LocalDateTime inicio, LocalDateTime fim);
	
	@Query(nativeQuery = true, value = "SELECT *\r\n"
			+ "FROM pedido p\r\n"
			+ "WHERE p.datapedido BETWEEN CURRENT_DATE AND (CURRENT_DATE + INTERVAL '1 DAY' - INTERVAL '1 SECOND') order by p.id desc limit 1")
	public Optional<Pedido> getUltimoPedidoNoite();
	
	@Query(nativeQuery = true, value = "SELECT *\r\n"
			+ "FROM pedido p\r\n"
			+ "WHERE p.datapedido BETWEEN CURRENT_DATE AND (CURRENT_DATE + INTERVAL '1 DAY' - INTERVAL '1 SECOND') and p.idpedido = :idPedido order by p.id desc limit 1")
	public Optional<Pedido> getPedidoNoite(Long idPedido);
}
