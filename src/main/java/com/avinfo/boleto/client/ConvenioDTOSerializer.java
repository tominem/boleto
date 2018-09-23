package com.avinfo.boleto.client;

import static com.avinfo.boleto.util.ParserUtil.writeNumberField;
import static com.avinfo.boleto.util.ParserUtil.writeStringField;

import java.io.IOException;

import com.avinfo.boleto.domain.Convenio;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ConvenioDTOSerializer extends JsonSerializer<Convenio>{

	@Override
	public void serialize(Convenio convenio, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		gen.writeStartObject();
		writeStringField(gen, "ConvenioNumero", convenio.getNumeroConvenio());
		writeStringField(gen, "ConvenioDescricao", convenio.getDescricaoConvenio());
		writeStringField(gen, "ConvenioCarteira", convenio.getCarteira());
		writeStringField(gen, "ConvenioEspecie", convenio.getEspecie());
		writeStringField(gen, "ConvenioPadraoCNAB", convenio.getPadraoCNAB());
		writeStringField(gen, "ConvenioNumeroRemessa", convenio.getNumeroRemessa());
		writeNumberField(gen, "Conta", convenio.getConta().getIdIntegracao());
		gen.writeEndObject();
		
	}

}
