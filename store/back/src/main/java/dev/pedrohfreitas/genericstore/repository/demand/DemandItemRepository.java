package dev.pedrohfreitas.genericstore.repository.demand;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.demand.DemandItem;

public interface DemandItemRepository extends JpaRepository<DemandItem, UUID>{

}
