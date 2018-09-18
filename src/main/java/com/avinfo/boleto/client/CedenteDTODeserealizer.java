package com.avinfo.boleto.client;

import static com.avinfo.boleto.util.ParserUtil.toLocalDateTime;

import java.io.IOException;

import com.avinfo.boleto.domain.Cedente;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CedenteDTODeserealizer extends JsonDeserializer<Cedente>{

	@Override
	public Cedente deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		
		ObjectCodec codec = parser.getCodec();
		JsonNode main = codec.readTree(parser);
		JsonNode dados = main.get("_dados");
		
		return Cedente.builder()
					.idIntegracao(Long.parseLong(dados.get("id").asText()))
					.razaosocial(dados.get("razaosocial").asText())
		            .nomefantasia(dados.get("nomefantasia").asText())
		            .cpfCnpj(dados.get("cpf_cnpj").asText())
		            .logradouro(dados.get("logradouro").asText())
		            .numero(dados.get("numero").asText())
		            .complemento(dados.get("complemento").asText())
		            .bairro(dados.get("bairro").asText())
		            .cep(dados.get("cep").asText())
		            .idCidade(dados.get("id_cidade").asInt())
		            .telefone(dados.get("telefone").asText())
		            .email(dados.get("email").asText())
		            .criado(toLocalDateTime(dados.get("criado").asText()))
		            .atualizado(toLocalDateTime(dados.get("atualizado").asText()))
		            .tokenCedente(dados.get("token_cedente").asText())
		            .tokenEsales(dados.get("token_esales").asText())
		            .situacao(dados.get("situacao").asText())
		            .idSoftwareHouse(dados.get("id_software_house").asLong())
		            .motivoInativacao(dados.get("motivo_inativacao").asText())
		            .dataAtivacao(toLocalDateTime(dados.get("data_ativacao").asText()))
		            .dataInativacao(toLocalDateTime(dados.get("data_inativacao").asText()))
		            .certificado(dados.get("certificado").asText())
		            .dtvencimentoCertificado(toLocalDateTime(dados.get("dtvencimentocertificado").asText()))
		            .uf(dados.get("uf").asText())
		            .contas(null)  //TODO verificar se necessário
		            .cidadeibge(dados.get("cidadeibge").asInt())
		            .cidade(dados.get("cidade").asText())
		            .build();

	}

}
