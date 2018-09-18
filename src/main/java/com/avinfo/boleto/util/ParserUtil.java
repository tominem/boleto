package com.avinfo.boleto.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ParserUtil {

	public static LocalDateTime toLocalDateTime(String dateAsText) {
		if (dateAsText != null && dateAsText.isEmpty()) {
			Instant instant = Instant.parse(dateAsText);
			LocalDateTime parse = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
			return parse;
		}
		
		return null;
	}

}
