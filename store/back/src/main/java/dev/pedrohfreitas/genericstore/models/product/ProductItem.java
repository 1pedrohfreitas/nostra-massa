package dev.pedrohfreitas.genericstore.models.product;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "p_item")
public class ProductItem {
	
	public ProductItem() {
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

	@Column(name = "item", length = 100, nullable = false)
	private String item;

	@Column(name = "description", length = 400, nullable = true)
	private String description;
	
	@Builder.Default
	@Column(name = "maxAmountFlavor", nullable = true)
	private Integer maxAmountFlavor = 1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_product_type")
	private ProductType productType;

	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "p_item_flavor", joinColumns = @JoinColumn(name = "id_product_item"), inverseJoinColumns = @JoinColumn(name = "id_flavor"))
	private Set<ProductItemFlavor> flavors = new HashSet();

}