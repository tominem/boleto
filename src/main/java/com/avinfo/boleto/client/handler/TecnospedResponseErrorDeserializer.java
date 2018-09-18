package com.avinfo.boleto.client.handler;

import java.io.IOException;

import com.avinfo.boleto.client.dto.ErroMessageDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TecnospedResponseErrorDeserializer extends JsonDeserializer<ErroMessageDTO>{

	@Override
	public ErroMessageDTO deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
