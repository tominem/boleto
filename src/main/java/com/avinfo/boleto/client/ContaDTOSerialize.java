package com.avinfo.boleto.client;

import java.io.IOException;

import com.avinfo.boleto.domain.Conta;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ContaDTOSerialize extends JsonSerializer<Conta> {

	@Override
	public void serialize(Conta conta, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		gen.writeStartObject();
		gen.writeStringField("ContaCodigoBanco", conta.getCodigoBanco());
		gen.writeStringField("ContaAgencia", conta.getAgenciaDV());
		gen.writeStringField("ContaAgenciaDV", conta.getAgenciaDV());
		gen.writeStringField("ContaNumero", conta.getConta());
		gen.writeStringField("ContaNumeroDV", conta.getContaDV());
		gen.writeStringField("ContaTipo", conta.getTipoConta());
		gen.writeStringField("ContaCodigoBeneficiario", conta.getCodBeneficiario());
		gen.writeEndObject();
		
	}

}
