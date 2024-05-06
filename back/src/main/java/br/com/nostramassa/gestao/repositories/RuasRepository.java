package br.com.nostramassa.gestao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nostramassa.gestao.models.cliente.Rua;


public interface RuasRepository extends JpaRepository<Rua, Long>  {

}
