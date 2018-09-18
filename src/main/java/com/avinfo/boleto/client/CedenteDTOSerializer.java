package com.avinfo.boleto.client;

import java.io.IOException;

import com.avinfo.boleto.domain.Cedente;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CedenteDTOSerializer extends JsonSerializer<Cedente>{

	@Override
	public void serialize(Cedente cedente, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		gen.writeStartObject();
		gen.writeStringField("CedenteRazaoSocial", cedente.getRazaosocial());
		gen.writeStringField("CedenteNomeFantasia", cedente.getNomefantasia());
		gen.writeStringField("CedenteCPFCNPJ", cedente.getCpfCnpj());
		gen.writeStringField("CedenteEnderecoLogradouro", cedente.getLogradouro());
		gen.writeStringField("CedenteEnderecoNumero", cedente.getNumero());
		gen.writeStringField("CedenteEnderecoComplement", cedente.getComplemento());
		gen.writeStringField("CedenteEnderecoBairro", cedente.getBairro());
		gen.writeStringField("CedenteEnderecoCEP", cedente.getCep());
		gen.writeStringField("CedenteEnderecoCidadeIBGE", cedente.getCidadeibge().toString());
		gen.writeStringField("CedenteTelefone", cedente.getTelefone());
		gen.writeStringField("CedenteEmail", cedente.getEmail());
		gen.writeEndObject();
		
	}

}
