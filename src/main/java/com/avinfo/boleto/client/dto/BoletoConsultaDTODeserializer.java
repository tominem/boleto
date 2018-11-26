package com.avinfo.boleto.client.dto;

import java.io.IOException;
import java.util.Iterator;

import com.avinfo.boleto.domain.Boleto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class BoletoConsultaDTODeserializer extends JsonDeserializer<BoletoConsultaDTO> {

	private BoletoDTODeserializer boletoDeserealizer = new BoletoDTODeserializer();
	
	@Override
	public BoletoConsultaDTO deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		
		BoletoConsultaDTO boletoConsultaDTO = new BoletoConsultaDTO();
		
		ObjectCodec codec = parser.getCodec();
		JsonNode main = codec.readTree(parser);
		JsonNode dados = main.get("_dados");
		
		handleBoletos(boletoConsultaDTO, dados);
		
		return boletoConsultaDTO;
	}

	private void handleBoletos(BoletoConsultaDTO boletoConsultaDTO, JsonNode dados) {
		if (dados != null) {
			Iterator<JsonNode> iterator = dados.iterator();
			while (iterator.hasNext()) {
				JsonNode node = iterator.next();
				Boleto boleto = boletoDeserealizer.deserialize(node);
				boletoConsultaDTO.addBoleto(boleto);
			} 
		}
	}

}
