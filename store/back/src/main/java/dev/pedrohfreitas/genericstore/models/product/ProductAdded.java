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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "p_added", uniqueConstraints = @UniqueConstraint(columnNames = {"added","tenant_id"}))
public class ProductAdded {
	
	public ProductAdded() {
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

	@Column(name = "added",length = 100, nullable = false)
	private String added;
	
	@Column(name = "description",length = 400, nullable = true)
	private String description;
	
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY,mappedBy = "addeds")
	private Set<ProductItemFlavor> flavors = new HashSet();
	
}