package dev.pedrohfreitas.genericstore.models.demand;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import dev.pedrohfreitas.genericstore.models.client.ClientData;
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
@Table(name = "demand")
public class Demand {
	
	public Demand() {
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
	
	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "demandId", nullable = false, updatable = false)
	private Long demandId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_client")
	private ClientData client;

	@Builder.Default
	@Column(name = "delivery", nullable = false)
	private Boolean delivery = true;

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Builder.Default
	@OneToMany(mappedBy = "demand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DemandItem> demandItens = new HashSet<>();

}
