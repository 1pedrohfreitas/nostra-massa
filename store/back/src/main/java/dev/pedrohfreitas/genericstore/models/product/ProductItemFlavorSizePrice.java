package dev.pedrohfreitas.genericstore.models.product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "p_item_flavor_size_price")
public class ProductItemFlavorSizePrice {
	
	public ProductItemFlavorSizePrice() {
		this.tenantID = TenantLocalStorage.getTenantID();
	}
	public void setTenantID(String tenantID) {		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;
	
	@Builder.Default
	@Column(name = "created", nullable = false, updatable = false)
	private Timestamp created = Timestamp.from(Instant.now());
	
	@Column(name = "created_by", nullable = false, updatable = false)
	private String createdBy;
	
	@Builder.Default
	@Column(name = "updated", nullable = false, updatable = false)
	private Timestamp updated = Timestamp.from(Instant.now());
	
	@Column(name = "updated_By", nullable = false, updatable = false)
	private String updatedBy;
	
	@Column(name = "tenant_id", nullable = false, updatable = false)
	private String tenantID;
	
	@Builder.Default
	@Column(name = "actived", nullable = false)
	private Boolean actived = true;

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item_flavor")
	private ProductItemFlavor flavor;
	
}
