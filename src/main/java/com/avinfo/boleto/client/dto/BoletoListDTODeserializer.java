package com.avinfo.boleto.client.dto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.BoletoLog;
import com.avinfo.boleto.domain.BoletoSituacao;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class BoletoListDTODeserializer extends JsonDeserializer<BoletoListDTO>{

	private BoletoDTODeserializer boletoDeserealizer = new BoletoDTODeserializer();
	
	@Override
	public BoletoListDTO deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		
		ObjectCodec codec = parser.getCodec();
		JsonNode main = codec.readTree(parser);
		JsonNode dados = main.get("_dados");
		JsonNode sucesso = dados.get("_sucesso");
		JsonNode falha = dados.get("_falha");
		
		Map<Long, Boleto> sucessoList = handleSucesso(sucesso);
		Map<Long, Boleto> falhaList = handleFalha(falha);
		
		BoletoListDTO result = new BoletoListDTO();
		result.setSuccesseds(sucessoList);
		result.setFaileds(falhaList);
		
		return result;
	}

	private Map<Long, Boleto> handleSucesso(JsonNode sucesso) {
		try {
			
			Map<Long, Boleto> sucessoList = new HashMap<>();
			
			if (sucesso != null) {
				Iterator<JsonNode> iterator = sucesso.iterator();
				while (iterator.hasNext()) {
					JsonNode node = iterator.next();
					Boleto boleto = boletoDeserealizer.deserialize(node);
					BoletoLog boletoLog = new BoletoLog(boleto.getSituacao());
					boleto.addBoletoLog(boletoLog);

					sucessoList.put(boleto.getId(), boleto);
				} 
			}
			return sucessoList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	private Map<Long, Boleto> handleFalha(JsonNode falha) {
		try {
			
			Map<Long, Boleto> falhaList = new HashMap<>();
			
			if (falha != null) {
				Iterator<JsonNode> iterator = falha.iterator();
				while (iterator.hasNext()) {
					JsonNode principal = iterator.next();
					JsonNode dados = principal.get("_dados");
					String erro = principal.get("_erro").toString();

					Boleto boleto = boletoDeserealizer.deserialize(dados);
					boleto.setSituacao(BoletoSituacao.FALHA);
					BoletoLog boletoLog = new BoletoLog(erro, BoletoSituacao.FALHA);
					boleto.addBoletoLog(boletoLog);

					falhaList.put(boleto.getId(), boleto);
				}
			}
			return falhaList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
}
