package br.com.nostramassa.gestao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nostramassa.gestao.models.cliente.Bairro;

public interface BairrosRepository extends JpaRepository<Bairro, Long>  {
}
