package dev.pedrohfreitas.genericstore.repository.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.product.ProductSize;

public interface ProductSizeRepository extends JpaRepository<ProductSize, UUID>{

	List<ProductSize> findAllByTenantID(String tenantID);

	Optional<ProductSize> findAllByTenantIDAndId(String tenantID, UUID id);

}
