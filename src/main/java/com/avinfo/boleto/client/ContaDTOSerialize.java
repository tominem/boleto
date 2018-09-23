package com.avinfo.boleto.client;

import static com.avinfo.boleto.util.ParserUtil.writeStringField;

import java.io.IOException;

import com.avinfo.boleto.domain.Conta;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ContaDTOSerialize extends JsonSerializer<Conta> {

	@Override
	public void serialize(Conta conta, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		gen.writeStartObject();
		writeStringField(gen, "ContaCodigoBanco", conta.getCodigoBanco());
		writeStringField(gen, "ContaAgencia", conta.getAgencia());
		writeStringField(gen, "ContaAgenciaDV", conta.getAgenciaDV());
		writeStringField(gen, "ContaNumero", conta.getConta());
		writeStringField(gen, "ContaNumeroDV", conta.getContaDV());
		writeStringField(gen, "ContaTipo", conta.getTipoConta());
		writeStringField(gen, "ContaCodigoBeneficiario", conta.getCodBeneficiario());
		gen.writeEndObject();
		
	}

}
