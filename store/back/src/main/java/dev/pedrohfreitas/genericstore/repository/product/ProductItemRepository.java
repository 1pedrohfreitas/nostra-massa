package dev.pedrohfreitas.genericstore.repository.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.product.ProductItem;

public interface ProductItemRepository extends JpaRepository<ProductItem, UUID>{

	List<ProductItem> findAllByTenantID(String tenantID);

	Optional<ProductItem> findAllByTenantIDAndId(String tenantID, UUID id);

}
