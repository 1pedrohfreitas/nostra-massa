package dev.pedrohfreitas.genericstore.repository.client;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.client.AddressStreet;

public interface AddressStreetRepository extends JpaRepository<AddressStreet, UUID>{

}
