package com.strive.labs.extjs.practicum;

import org.codehaus.jackson.map.ObjectMapper;

public final class Utils {
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	
	public static final String toJson(Object o) throws Exception {
		return JSON_MAPPER.writeValueAsString(o);
	}
}
