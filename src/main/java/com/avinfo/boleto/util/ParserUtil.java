package com.avinfo.boleto.util;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonGenerator;

public class ParserUtil {

	public static LocalDateTime toLocalDateTime(String dateAsText) {
		if (dateAsText != null && dateAsText.isEmpty()) {
			Instant instant = Instant.parse(dateAsText);
			LocalDateTime parse = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
			return parse;
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

}
