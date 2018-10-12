package com.avinfo.boleto.client.dto;

import static com.avinfo.boleto.util.ParserUtil.getAsObject;
import static com.avinfo.boleto.util.ParserUtil.getAsString;
import static java.lang.Long.valueOf;

import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.BoletoSituacao;
import com.fasterxml.jackson.databind.JsonNode;

public class BoletoDTODeserializer {

	public Boleto deserialize(JsonNode node) {
		
		Boleto boleto = Boleto.builder()
			.idIntegracao(getAsString(node, "idintegracao"))
			.id(valueOf(node.get("TituloNumeroDocumento").asText().trim()))
			.tituloNumeroDocumento(node.get("TituloNumeroDocumento").asText().trim())
			.situacao(getAsObject(node, "situacao", BoletoSituacao::valueOf))
			.build();

	  return boleto;
	}

}
