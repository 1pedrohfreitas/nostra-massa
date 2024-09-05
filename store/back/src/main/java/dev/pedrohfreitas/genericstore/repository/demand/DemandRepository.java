package dev.pedrohfreitas.genericstore.repository.demand;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.demand.Demand;

public interface DemandRepository extends JpaRepository<Demand, UUID>{

}
