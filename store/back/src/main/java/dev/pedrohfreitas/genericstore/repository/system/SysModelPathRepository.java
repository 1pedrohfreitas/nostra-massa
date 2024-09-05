package dev.pedrohfreitas.genericstore.repository.system;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pedrohfreitas.genericstore.models.SysModelPath;

public interface SysModelPathRepository extends JpaRepository<SysModelPath, UUID>{

	Optional<SysModelPath> findByTypeAndName(String type, String className);

}
