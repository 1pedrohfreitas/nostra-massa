package dev.pedrohfreitas.genericstore.repository.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.product.ProductItemFlavorSizePrice;

public interface ProductItemFlavorSizePriceRepository extends JpaRepository<ProductItemFlavorSizePrice, UUID>{
	List<ProductItemFlavorSizePrice> findAllByTenantID(String tenantID);

	Optional<ProductItemFlavorSizePrice> findAllByTenantIDAndId(String tenantID, UUID id);
}
