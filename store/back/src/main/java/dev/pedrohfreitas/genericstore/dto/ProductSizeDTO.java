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
public class ProductSizeDTO {
	private UUID id;
	private Timestamp created;
	private String createdBy;
	private Timestamp updated;
	private String updatedBy;
	private String tenantID;
	private String size;
	@Builder.Default
	private Boolean actived = true;
	@Builder.Default
	private Set<ProductItemFlavorDTO> flavors = new HashSet<>();
	
}
