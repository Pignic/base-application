package com.pignic.basicapp.tool;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONHelper {

	public static interface JSONObject {

	}

	public static <T extends JSONObject> T deserialize(final Class<T> clazz, final String json) {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonFactory jsonFactory = mapper.getFactory();
		final JsonParser jsonParser;
		final T object;
		try {
			jsonParser = jsonFactory.createParser(json);
			object = jsonParser.readValueAs(clazz);
			return object;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T extends JSONObject> String serialize(final T object) {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonFactory jsonFactory = mapper.getFactory();
		JsonGenerator jsonGenerator = null;
		final Writer stringWriter = new StringWriter();
		try {
			jsonGenerator = jsonFactory.createGenerator(stringWriter);
			jsonGenerator.writeObject(object);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}
}
