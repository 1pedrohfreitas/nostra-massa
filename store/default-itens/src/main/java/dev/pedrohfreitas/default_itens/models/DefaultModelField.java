package dev.pedrohfreitas.default_itens.models;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultModelField {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "created", nullable = false, updatable = false)
	private Timestamp created = Timestamp.from(Instant.now());
	
	@Column(name = "created_by", nullable = false, updatable = false)
	private String createdBy;
	
	@Column(name = "updated", nullable = false, updatable = false)
	private Timestamp updated = Timestamp.from(Instant.now());
	
	@Column(name = "updated_By", nullable = false, updatable = false)
	private String updatedBy;
	
	@Column(name = "tenant_id", nullable = false, updatable = false)
	private String tenantID;
}
