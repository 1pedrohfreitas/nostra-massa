package dev.pedrohfreitas.genericstore.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import dev.pedrohfreitas.genericstore.models.product.ProductItemFlavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIngredientDTO {
	private UUID id;
	private Timestamp created;
	private String createdBy;
	private Timestamp updated;
	private String updatedBy;
	private String tenantID;
	@Builder.Default
	private Boolean actived = true;
	private String ingredient;
	@Builder.Default
	private Set<ProductItemFlavorDTO> flavors = new HashSet<>();
	
}
