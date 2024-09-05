package dev.pedrohfreitas.genericstore.repository.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.product.ProductAdded;

public interface ProductAddedRepository extends JpaRepository<ProductAdded, UUID> {
	List<ProductAdded> findAllByTenantID(String tenantID);

	Optional<ProductAdded> findAllByTenantIDAndId(String tenantID, UUID id);
}
