package com.vten.gedeon.api.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.vten.gedeon.api.search.GedSearch;
import com.vten.gedeon.exception.GedeonRuntimeException;

public class APIGedFactory {
	
	private static final String BASE_PACKAGE = "com/vten/gedeon";

	/**
	 * Create an instance of GedSearch to perform search on the given objectstore
	 *  - Use of SearchBuilder is recommended to create search
	 * @return GedSearch object
	 */
	public static GedSearch createEmptySearch() {
		Constructor<?> constructor = retrieveConstructor(GedSearch.class);
		try {
			return (GedSearch) constructor.newInstance();
		} catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
			throw new GedeonRuntimeException("Fatal Error in API : default constructor for OESearch not available in api implementation." );
		}
	}
	
	/**
	 * Retrieve the constructor for the given interface implementation 
	 * @param clazz
	 * @return the reflected constructor
	 * @throws GedRuntimeException when no constructor can't be found
	 */
	protected static Constructor<?>  retrieveConstructor(Class<?> clazz) {
		try {
			List<Class<?>> res = getAllExtendedOrImplementedTypesRecursively2(clazz);
		
			return res.get(0).getConstructor();
		} catch (NoSuchMethodException|SecurityException|ClassNotFoundException|IndexOutOfBoundsException e) {
			throw new GedeonRuntimeException("Fatal Error in API : default constructor for OESearch not available in api implementation." );
		}
	}
	
	/**
	 * Retrieve all the implementation of the given class
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 */
	protected static List<Class<?>> getAllExtendedOrImplementedTypesRecursively2(Class<?> clazz) throws ClassNotFoundException {
	    List<Class<?>> res = new ArrayList<>();
	    
	    ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
	    provider.addIncludeFilter(new AssignableTypeFilter(clazz));

	    // scan in org.example.package
	    Set<BeanDefinition> components = provider.findCandidateComponents(BASE_PACKAGE);
	    for (BeanDefinition component : components) {
	    	res.add(Class.forName(component.getBeanClassName()));
	    }

	    return res;
	}
	
}
