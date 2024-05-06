package br.com.nostramassa.gestao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nostramassa.gestao.models.pedido.PizzaSabor;

public interface PizzaSaborRepository extends JpaRepository<PizzaSabor, Long>  {
	

}
