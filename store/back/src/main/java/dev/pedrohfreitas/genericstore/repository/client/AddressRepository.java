package dev.pedrohfreitas.genericstore.repository.client;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.client.Address;

public interface AddressRepository extends JpaRepository<Address, UUID>{

}
