package com.avinfo.boleto.util;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;

public class ParserUtil {

	public static LocalDateTime toLocalDateTime(String dateAsText) {
		if (dateAsText != null && dateAsText.isEmpty()) {
			Instant instant = Instant.parse(dateAsText);
			LocalDateTime parse = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
			return parse;
		}
		
		return null;
	}
	
	public static String getAsString(JsonNode node, String fieldName){
		JsonNode jsonNode = node.get(fieldName);
		
		if (jsonNode != null) {
			return jsonNode.asText().trim();
		}
		
		return null;
	}

	public static void writeStringField(JsonGenerator gen, String field, String value) throws IOException {
		if (value != null) {
			gen.writeStringField(field, value);
		}
	}

	public static void writeNumberField(JsonGenerator gen, String field, Long value) throws IOException {
		if (value != null) {
			gen.writeNumberField(field, value);
		}
	}

	public static String dateFormat(String pattern, LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDate.format(formatter);
	}

	public static <R> R getAsObject(JsonNode node, String fieldName, Function<String, R> function) {
		JsonNode jsonNode = node.get(fieldName);
		if (jsonNode != null) {
			return function.apply(jsonNode.asText().trim());
		}
		return null;
	}

}
