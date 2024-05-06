package br.com.nostramassa.gestao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.pedido.PizzaAcrescimo;

public interface PizzaAcrescimoRepository extends JpaRepository<PizzaAcrescimo, Long>  {

	@Query(value = "select * from pizza_acrescimo pa\r\n"
			+ "where pa.id in (\r\n"
			+ "select pipr.id_pedido_item_pizza_acrescimo from pedido_item_pizza_acrescimo pipr where pipr.id_pedido = :idPedido\r\n"
			+ "and pipr.id_pedido_item = :idItem and pipr.id_pedido_item_pizza = :idSabor)",nativeQuery = true)
	public List<PizzaAcrescimo> getByItemPedidoAcrescimo(Long idPedido, Long idItem, Long idSabor);
}
