package dev.pedrohfreitas.genericstore.models.delivery;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import dev.pedrohfreitas.genericstore.models.client.Address;
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
@Table(name = "delivery")
public class Delivery {
	
	public Delivery() {
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

	@Column(name = "number",length = 100, nullable = false)
	private Long number;
	
	@Column(name = "observation",length = 500, nullable = true)
	private String observation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_address")
	private Address address;
}