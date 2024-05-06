package br.com.nostramassa.gestao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nostramassa.gestao.models.sistema.Parametros;

public interface ParametrosRepository extends JpaRepository<Parametros, Long>  {
}
