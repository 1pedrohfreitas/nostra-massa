package dev.pedrohfreitas.genericstore.repository.client;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.client.AddressNeighborhood;

public interface AddressNeighborhoodRepository extends JpaRepository<AddressNeighborhood, UUID>{

}
