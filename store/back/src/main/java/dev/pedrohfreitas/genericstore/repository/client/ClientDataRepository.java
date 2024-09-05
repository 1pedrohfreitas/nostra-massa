package dev.pedrohfreitas.genericstore.repository.client;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.client.ClientData;

public interface ClientDataRepository extends JpaRepository<ClientData, UUID>{
	List<ClientData> findAllByTenantID(String tenantID);
}