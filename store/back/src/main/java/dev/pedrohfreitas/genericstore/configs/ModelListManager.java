package dev.pedrohfreitas.genericstore.configs;

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.stereotype.Component;

import dev.pedrohfreitas.genericstore.models.SysModelPath;
import dev.pedrohfreitas.genericstore.repository.system.SysModelPathRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;

@Component
public class ModelListManager {
	
	private final SysModelPathRepository sysModelPathRepository;
	
	public ModelListManager(SysModelPathRepository sysModelPathRepository) {
		this.sysModelPathRepository = sysModelPathRepository;
	}

	@PostConstruct
	public void getClassList() {
		sysModelPathRepository.deleteAll();
		if(sysModelPathRepository.count() == 0) {
			getModelList();
			getDtoList();
		}
		
	}

	
	private void getModelList() {
		String packageNameModel = "dev.pedrohfreitas.genericstore.models";

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packageNameModel)
                .setScanners(Scanners.TypesAnnotated)
                .filterInputsBy(input -> input.endsWith(".class")));
        
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        
        
        for (Class<?> clazz : entityClasses) {
        	SysModelPath sysModelPath = new SysModelPath();
        	sysModelPath.setModelPath(clazz.getPackageName());
        	sysModelPath.setType("MODEL");
        	sysModelPath.setName(clazz.getSimpleName());
        	sysModelPathRepository.save(sysModelPath);
        }
	}
	
	private void getDtoList() {
		String packageNameModel = "dev.pedrohfreitas.genericstore.dto";
		
//		Reflections reflections = new Reflections(new ConfigurationBuilder()
//		        .setUrls(ClasspathHelper.forPackage(packageNameModel)));
//		        .setScanners(Scanners.SubTypes, Scanners.TypesAnnotated));
//		        .filterInputsBy(new FilterBuilder().includePackage(packageNameModel))
//		    );
		   Reflections reflections = new Reflections(
		            new ConfigurationBuilder()
		            .setUrls(ClasspathHelper.forPackage(packageNameModel))
		                .forPackages(packageNameModel)  // Define o pacote base para busca
		                .setScanners(Scanners.SubTypes.filterResultsBy(s -> s.contains("java.lang.Object"))) // Scanneia subtipos e classes
		        );
		

//		   Reflections reflections = new Reflections(new ConfigurationBuilder()
//	                .forPackages(packageNameDTO)
//	                .setScanners(Scanners.SubTypes)
//	                .filterInputsBy(input -> true));
	        
	        
	        Set<Class<? extends Object>> dtoClasses = reflections.getSubTypesOf(Object.class);
        
        for (Class<?> clazz : dtoClasses) {
        	if(clazz.getPackageName().contains("dto")) {
        		SysModelPath sysModelPath = new SysModelPath();
            	sysModelPath.setModelPath(clazz.getPackageName());
            	sysModelPath.setType("DTO");
            	sysModelPath.setName(clazz.getSimpleName());
            	sysModelPathRepository.save(sysModelPath);	
        	}
        	
        }
	}
}
