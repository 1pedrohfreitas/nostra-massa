package dev.pedrohfreitas.genericstore.util;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import dev.pedrohfreitas.genericstore.models.SysModelPath;
import dev.pedrohfreitas.genericstore.repository.system.SysModelPathRepository;
import jakarta.transaction.Transactional;

@Service
public class CRUDBasicUtil {

	private final ModelMapper mapper = new ModelMapper();
	private final ApplicationContext applicationContext;
	private final SysModelPathRepository sysModelPathRepository;

	public CRUDBasicUtil(
			ApplicationContext applicationContext,
			SysModelPathRepository sysModelPathRepository) {
		this.applicationContext = applicationContext;
		this.sysModelPathRepository = sysModelPathRepository;
	}

	public <DTO, D, E> DTO getByID(String modelClass,UUID id) {

		Example<E> example;
		try {
			Class<E> entityClass = getClassByName("MODEL",modelClass);
			Class<DTO> dtoClass = getClassByName("DTO",modelClass+"DTO");
			E entity = entityClass.getDeclaredConstructor().newInstance();

			entityClass.getMethod("setTenantId", String.class).invoke(entity, TenantLocalStorage.getTenantID());
			entityClass.getMethod("setId", UUID.class).invoke(entity, id);
			example = Example.of(entity);
			JpaRepository<E, UUID> repository = getRepository(entityClass);
			List<E> result = repository.findAll(example);
			
			if (!result.isEmpty()) {
				return mapper.map(result.stream().findFirst(), dtoClass);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public <DTO, D, E> DTO put(String modelClass,UUID id, DTO data) {

		Example<E> example;
		try {
			Class<E> entityClass = getClassByName("MODEL",modelClass);
			Class<DTO> dtoClass = getClassByName("DTO",modelClass+"DTO");
			E entity = entityClass.getDeclaredConstructor().newInstance();

			entityClass.getMethod("setTenantId", String.class).invoke(entity, TenantLocalStorage.getTenantID());
			entityClass.getMethod("setId", UUID.class).invoke(entity, id);
			ExampleMatcher matcher = ExampleMatcher.matching()
	                .withIgnorePaths("created")
	                .withIgnorePaths("createdBy")
	                .withIgnorePaths("updated")
	                .withIgnorePaths("updatedBy")
	                .withMatcher("tenantID", ExampleMatcher.GenericPropertyMatchers.exact());
			example = Example.of(entity,matcher);
			JpaRepository<E, UUID> repository = getRepository(entityClass);
			List<E> result = repository.findAll(example);

			if (!result.isEmpty()) {
				return mapper.map(result.stream().findFirst(), dtoClass);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public <DTO, E> Page<DTO> getAll(String modelClass,DTO dataDto, Pageable pageable) {
		if (pageable == null) {
			pageable = PageRequest.of(0, 50);
		}
		
		try {
			Class<E> entityClass = getClassByName("MODEL",modelClass);
			Class<DTO> dtoClass = getClassByName("DTO",modelClass+"DTO");
			Example<E> example;
			E entity = entityClass.getDeclaredConstructor().newInstance();
			if(dataDto != null) {
				entity = mapper.map(dataDto, entityClass);	
			}
			entityClass.getMethod("setTenantID", String.class).invoke(entity, TenantLocalStorage.getTenantID());
			ExampleMatcher matcher = ExampleMatcher.matching()
	                .withIgnorePaths("created")
	                .withIgnorePaths("createdBy")
	                .withIgnorePaths("updated")
	                .withIgnorePaths("updatedBy")
	                .withMatcher("tenantID", ExampleMatcher.GenericPropertyMatchers.exact());
			example = Example.of(entity,matcher);
			JpaRepository<E, UUID> repository = getRepository(entityClass);
			Page<E> entityPage = repository.findAll(example, pageable);
			List<DTO> dtoList = entityPage.getContent().stream().map(item -> mapper.map(item, dtoClass))
					.collect(Collectors.toList());

			return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Transactional
	public <DTO, E> DTO create(String modelClass,DTO dataDto) {
		Class<E> entityClass = getClassByName("MODEL",modelClass);
		Class<DTO> dtoClass = getClassByName("DTO",modelClass+"DTO");
		
		try {
			E entity = entityClass.getDeclaredConstructor().newInstance();
			entity = mapper.map(dataDto, entityClass);
			JpaRepository<E, UUID> repository = getRepository(entityClass);
			entity = repository.save(entity);
			return mapper.map(entity, dtoClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@Transactional
	public <E,DTO> UUID delete(String modelClass,UUID id) {
		Class<E> entityClass = getClassByName("MODEL",modelClass);
		JpaRepository<E, UUID> repository = getRepository(entityClass);
		repository.deleteById(id);
		return id;
	}

	 @SuppressWarnings("unchecked")
	private <E> Class<E> getClassByName(String type, String classDTOName) {
		 String[] classSplit = classDTOName.split("\\.");
		 String className = classSplit[classSplit.length -1]; 
		 Optional<SysModelPath> classNameFullData = sysModelPathRepository.findByTypeAndName(type,className);
	      if(classNameFullData.isPresent()) {
	    	  try {
		            return (Class<E>) Class.forName(classNameFullData.get().getModelPath() + "."+className);
		        } catch (ClassNotFoundException e) {
		            throw new RuntimeException("Class not found: " + classNameFullData.get().getName(), e);
		        }  
	      } else {
	          throw new RuntimeException("Class not mappied");
	      }
	        
	    }

	@SuppressWarnings("unchecked")
	public <E> JpaRepository<E, UUID> getRepository(Class<E> entityClass) {
		String repositoryBeanName = entityClass.getSimpleName() + "Repository";
		repositoryBeanName = Character.toLowerCase(repositoryBeanName.charAt(0)) + repositoryBeanName.substring(1);
		return (JpaRepository<E, UUID>) applicationContext.getBean(repositoryBeanName);
	}
	
}
