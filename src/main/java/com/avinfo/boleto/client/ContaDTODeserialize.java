package com.avinfo.boleto.client;

import static com.avinfo.boleto.util.ParserUtil.toLocalDateTime;

import java.io.IOException;

import com.avinfo.boleto.domain.Cedente;
import com.avinfo.boleto.domain.Conta;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ContaDTODeserialize extends JsonDeserializer<Conta> {

	@Override
	public Conta deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
		
		ObjectCodec codec = parser.getCodec();
		JsonNode main = codec.readTree(parser);
		JsonNode dados = main.get("_dados");
		
		return Conta.builder()
					.idIntegracao(Long.parseLong(dados.get("id").asText()))
					.codigoBanco(dados.get("codigo_banco").asText())
					.agencia(dados.get("agencia").asText())
					.agenciaDV(dados.get("agencia_dv").asText())
					.conta(dados.get("conta").asText())
					.contaDV(dados.get("conta_dv").asText())
					.tipoConta(dados.get("tipo_conta").asText())
					.codBeneficiario(dados.get("cod_beneficiario").asText())
					.cedente(Cedente.builder().idIntegracao(dados.get("id_cedente").asLong()).build())
					.criado(toLocalDateTime(dados.get("criado").asText()))
					.atualizado(toLocalDateTime(dados.get("atualizado").asText()))
					.codEmpresa(dados.get("cod_empresa").asText())
					.build();
	}

}
