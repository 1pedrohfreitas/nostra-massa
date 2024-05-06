package br.com.nostramassa.gestao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.pedido.PizzaIngrediente;

public interface PizzaIngredienteRepository extends JpaRepository<PizzaIngrediente, Long>  {
	@Query(value = "select * from pizza_ingrediente pi where pi.id in (\r\n"
			+ "select psi.id_pizza_ingrediente from pizza_sabor_ingrediente psi where psi.id_pizza_sabor = :idSabor)",nativeQuery = true)
	public List<PizzaIngrediente> getIngredientesByIdSabor(Long idSabor);

	@Query(value = "select * from pizza_ingrediente pi\r\n"
			+ "where pi.id in (\r\n"
			+ "select pipr.id_pedido_item_ingrediente_retirar from pedido_item_pizza_retirar pipr where pipr.id_pedido = :idPedido\r\n"
			+ "and pipr.id_pedido_item = :idItem and pipr.id_pedido_item_pizza = :idSabor)",nativeQuery = true)
	public List<PizzaIngrediente> getByItemPedidoRetirar(Long idPedido, Long idItem, Long idSabor);

}
