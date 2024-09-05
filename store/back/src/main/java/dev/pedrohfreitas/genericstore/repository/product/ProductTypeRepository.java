package dev.pedrohfreitas.genericstore.repository.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.product.ProductType;


public interface ProductTypeRepository extends JpaRepository<ProductType, UUID>{

	List<ProductType> findAllByTenantID(String tenantID);

	Optional<ProductType> findAllByTenantIDAndId(String tenantID, UUID id);

}
