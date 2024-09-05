package dev.pedrohfreitas.genericstore.models.product;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import dev.pedrohfreitas.genericstore.configs.UserLocalStorage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "p_type", uniqueConstraints = @UniqueConstraint(columnNames = { "type", "tenant_id" }))
public class ProductType {

	@PrePersist
	@PreUpdate
	public void setDefault() {
		if (this.tenantID == null) {
			this.tenantID = TenantLocalStorage.getTenantID();
		}
		if (this.createdBy == null) {
			this.createdBy = UserLocalStorage.getUserID();
		}
		if (this.created == null) {
			this.created = Timestamp.from(Instant.now());
		}
		this.updated = Timestamp.from(Instant.now());
		this.updatedBy = UserLocalStorage.getUserID();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@Builder.Default
	@Column(name = "created", nullable = false)
	private Timestamp created = Timestamp.from(Instant.now());

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Builder.Default
	@Column(name = "updated", nullable = false)
	private Timestamp updated = Timestamp.from(Instant.now());

	@Column(name = "updated_by", nullable = false)
	private String updatedBy;

	@Column(name = "tenant_id", nullable = false, updatable = false)
	private String tenantID;

	@Builder.Default
	@Column(name = "actived", nullable = false)
	private Boolean actived = true;

	@Column(name = "type", length = 100, nullable = false)
	private String type;

	@Column(name = "description", length = 400, nullable = true)
	private String description;

	@Builder.Default
	@OneToMany(mappedBy = "productType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ProductItem> itens = new HashSet();

}