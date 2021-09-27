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
import com.vten.gedeon.exception.OERuntimeException;

public class OEFactory {

	public static GedSearch createEmptySearch() {
		Constructor<?> constructor = retrieveConstructor(GedSearch.class);
		try {
			return (GedSearch) constructor.newInstance();
		} catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
			throw new OERuntimeException("Fatal Error in API : default constructor for OESearch not available in api implementation." );
		}
	}
	
	protected static Constructor<?>  retrieveConstructor(Class<?> clazz) {
		System.out.println("getAllExtendedOrImplementedTypesRecursively "+clazz.getName());
		try {
			List<Class<?>> res = getAllExtendedOrImplementedTypesRecursively2(clazz);
		
			return res.get(0).getConstructor();
		} catch (NoSuchMethodException|SecurityException|ClassNotFoundException|IndexOutOfBoundsException e) {
			throw new OERuntimeException("Fatal Error in API : default constructor for OESearch not available in api implementation." );
		}
	}
	
	protected static List<Class<?>> getAllExtendedOrImplementedTypesRecursively2(Class<?> clazz) throws ClassNotFoundException {
	    List<Class<?>> res = new ArrayList<>();
	    
	    ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
	    provider.addIncludeFilter(new AssignableTypeFilter(clazz));

	    // scan in org.example.package
	    Set<BeanDefinition> components = provider.findCandidateComponents("com/vten/gedeon");
	    for (BeanDefinition component : components)
	    {
	    	res.add(Class.forName(component.getBeanClassName()));
	    }

	    return res;
	}
	
}
