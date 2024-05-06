package br.com.nostramassa.gestao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nostramassa.gestao.models.pedido.PizzaIngrediente;
import br.com.nostramassa.gestao.models.pedido.PizzaSaborIngrediente;

public interface PizzaSaborIngredienteRepository extends JpaRepository<PizzaSaborIngrediente, Long>  {
}
