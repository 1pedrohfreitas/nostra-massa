package dev.pedrohfreitas.genericstore.repository.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.product.ProductIngredient;

public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, UUID> {
	List<ProductIngredient> findAllByTenantID(String tenantID);

	Optional<ProductIngredient> findAllByTenantIDAndId(String tenantID, UUID id);
}
