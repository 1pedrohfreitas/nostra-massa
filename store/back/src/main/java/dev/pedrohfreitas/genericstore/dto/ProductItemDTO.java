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
public class ProductItemDTO{
	private UUID id;
	private Timestamp created;
	private String createdBy;
	private Timestamp updated;
	private String updatedBy;
	private String tenantID;
	@Builder.Default
	private Boolean actived = true;
	private String item;
	private String description;
	private Integer maxAmountFlavor;
	private ProductTypeDTO productType;
	@Builder.Default
	private Set<ProductItemFlavorDTO> flavors = new HashSet<>();
}
