package dev.pedrohfreitas.genericstore.models.demand;

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
@Table(name = "d_item_remove_ingredient")
public class DemandItemFlavorRemoveIngredient {
	
	public DemandItemFlavorRemoveIngredient() {
		this.tenantID = TenantLocalStorage.getTenantID();
	}
	public void setTenantID(String tenantID) {		
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
	
	@Column(name = "id_ingredient", nullable = false, updatable = false)
	private Long ingredient;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_demand_item")
	private DemandItemFlavor demandItemFlavor;
	
}
