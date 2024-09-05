package dev.pedrohfreitas.genericstore.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemFlavorDTO {
	private UUID id;
	private Timestamp created;
	private String createdBy;
	private Timestamp updated;
	private String updatedBy;
	private String tenantID;
	@Builder.Default
	private Boolean actived = true;
	private String flavor;
	@Builder.Default
	private Set<ProductItemDTO> productItens = new HashSet<>();
	@Builder.Default
	private Set<ProductIngredientDTO> ingredients = new HashSet<>();
	@Builder.Default
	private Set<ProductAddedDTO> addeds = new HashSet<>();
	@Builder.Default
	private Set<ProductSizeDTO> sizes = new HashSet<>();
	@Builder.Default
	private Set<ProductItemFlavorSizePriceDTO> prices = new HashSet<>();
}
