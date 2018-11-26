package com.avinfo.boleto.client.dto;

import java.io.IOException;

import com.avinfo.boleto.domain.Boleto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BoletoSolicitaImpressaoDTOSerialize extends JsonSerializer<BoletoSolicitaImpressaoDTO>{

	@Override
	public void serialize(BoletoSolicitaImpressaoDTO dto, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		
		gen.writeStartObject();
		gen.writeStringField("TipoImpressao", String.valueOf(dto.getTipoImpressao().getId()));
		gen.writeArrayFieldStart("Boletos");
	
		for(Boleto boleto : dto.getBoletos().values()){
			gen.writeString(boleto.getIdIntegracao());
		}
		
		gen.writeEndArray();
		gen.writeEndObject();
		
	}

}
