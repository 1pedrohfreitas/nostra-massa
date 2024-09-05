package dev.pedrohfreitas.genericstore.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sys_model_path")
public class SysModelPath {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "name", nullable = false,length = 100)
	private String name;
	
	@Column(name = "type", nullable = false,length = 50)
	private String type;
	
	@Column(name = "model-path", nullable = false,length = 400)
	private String modelPath;

}
