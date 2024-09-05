package dev.pedrohfreitas.genericstore.repository.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.product.ProductItemFlavor;

public interface ProductItemFlavorRepository extends JpaRepository<ProductItemFlavor, UUID>{
	List<ProductItemFlavor> findAllByTenantID(String tenantID);

	Optional<ProductItemFlavor> findAllByTenantIDAndId(String tenantID, UUID id);
}
