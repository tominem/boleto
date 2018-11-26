package com.avinfo.boleto.client.dto;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class GeraRemessaDTOSerializer extends JsonSerializer<GeraRemessaReqDTO> {

	@Override
	public void serialize(GeraRemessaReqDTO value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		
		gen.writeStartObject();
		gen.writeStartArray();
		
		Optional.of(value.getBoletos())
			.ifPresent(boletos -> boletos.forEach(b -> {
			
				try {
					gen.writeString(b.getIdIntegracao());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
			}));
		
		gen.writeEndArray();
		gen.writeEndObject();
		
	}


}
