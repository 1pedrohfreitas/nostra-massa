package dev.pedrohfreitas.genericstore.models.demand;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "d_item_flavor")
public class DemandItemFlavor {
	
	public DemandItemFlavor() {
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_demand_item")
	private DemandItem demandItem;
	
	@Builder.Default
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price = new BigDecimal(0);
	
	@Builder.Default
	@OneToMany(mappedBy = "demandItemFlavor" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DemandItemFlavorRemoveIngredient> removeIngredient = new HashSet();
	
	@Builder.Default
	@OneToMany(mappedBy = "demandItemFlavor" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DemandItemFlavorAdded> addeds = new HashSet();
	
}
