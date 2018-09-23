package com.avinfo.boleto.client;

import static com.avinfo.boleto.util.ParserUtil.toLocalDateTime;

import java.io.IOException;

import com.avinfo.boleto.domain.Convenio;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ConvenioDTODeserializer extends JsonDeserializer<Convenio> {

	@Override
	public Convenio deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec codec = parser.getCodec();
		JsonNode main = codec.readTree(parser);
		JsonNode dados = main.get("_dados");
		
		return Convenio.builder()
					.idIntegracao(dados.get("id").asLong())
				    .numeroConvenio(dados.get("numero_convenio").asText())
				    .descricaoConvenio(dados.get("descricao_convenio").asText())
				    .carteira(dados.get("carteira").asText())
				    .especie(dados.get("especie").asText())
				    .criado(toLocalDateTime(dados.get("criado").asText()))
				    .atualizado(toLocalDateTime(dados.get("atualizado").asText()))
				    .padraoCNAB(dados.get("padraoCNAB").asText())
				    .utilizaVan(dados.get("utiliza_van").asBoolean())
				    .numeroRemessa(dados.get("numero_remessa").asText())
				    .build();
	}

}
