package br.com.nostramassa.gestao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.pedido.PedidoItem;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long>  {
	
	@Query(value = "select * from pedido_item p where p.id_pedido = :idPedido",nativeQuery = true)
	public List<PedidoItem> getItensPedido(Long idPedido);
	
}
