package com.strive.labs.extjs.practicum;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public final class Utils {
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final Logger LOGGER = Logger.getLogger(Utils.class);
	
	public static final String toJson(Object o) throws Exception {
		return JSON_MAPPER.writeValueAsString(o);
	}

	public static <E, T extends Collection<E>> T fromJson(String jsonStr, Class<T> collectionType, Class<E> elementType) {
	    ObjectMapper mapper = new ObjectMapper();
	    try {
	        return mapper.readValue(jsonStr, 
	        		TypeFactory.defaultInstance().constructCollectionType(collectionType, elementType));
	    } catch (Exception ex) {
	    	LOGGER.error("Error converting JSON to collection", ex);
	    }
	    return null; 
	}
}
