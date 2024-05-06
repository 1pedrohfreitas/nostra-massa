package br.com.nostramassa.gestao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nostramassa.gestao.models.pedido.Bebida;

public interface BebidaRepository extends JpaRepository<Bebida, Long>  {
}
