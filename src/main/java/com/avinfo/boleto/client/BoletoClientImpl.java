package com.avinfo.boleto.client;

import static com.avinfo.boleto.client.dto.TecnospedHeaders.CNPJ_CEDENTE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.avinfo.boleto.client.dto.BoletoConsultaDTO;
import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.client.dto.BoletoSolicitaImpressaoDTO;
import com.avinfo.boleto.config.TecnospedRestReqParams;
import com.avinfo.boleto.domain.Boleto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BoletoClientImpl implements BoletoClient {

	private RestTemplate restTemplate;
	private TecnospedRestReqParams restReqParams;

	@Autowired
	public BoletoClientImpl(RestTemplate restTemplate, TecnospedRestReqParams restReqParams) {
		this.restTemplate = restTemplate;
		this.restReqParams = restReqParams;
	}
	
	@Override
	public BoletoListDTO incluir(List<Boleto> boletos, String cnpjCedente) {
		HttpEntity<List<Boleto>> request = new HttpEntity<>(boletos, restReqParams.addHeader(CNPJ_CEDENTE, cnpjCedente));
		ResponseEntity<BoletoListDTO> exchange = restTemplate.exchange(END_POINT_LOTE, HttpMethod.POST, request, BoletoListDTO.class);

		return exchange.getBody();
	}

	@Override
	public BoletoConsultaDTO consultaBoleto(Map<String, String> params, String cnpjCedente) {
		
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		
		if (params != null) {
			params.forEach(builder::queryParam);
			UriComponents uri = builder.build();
			HttpEntity<?> request = new HttpEntity<>(restReqParams.addHeader(CNPJ_CEDENTE, cnpjCedente));
			ResponseEntity<BoletoConsultaDTO> exchange = restTemplate.exchange(END_POINT_CONSULTA + uri.toUriString(), HttpMethod.GET, request, BoletoConsultaDTO.class);
			return exchange.getBody();
		}
		
		return null;
	}

	@Override
	public Map<String, String> solicitaImpressao(BoletoSolicitaImpressaoDTO dto, String cnpjCedente) {
		try {
			HttpEntity<BoletoSolicitaImpressaoDTO> request = new HttpEntity<>(dto, restReqParams.addHeader(CNPJ_CEDENTE, cnpjCedente));
			ResponseEntity<String> exchange = restTemplate.exchange(END_POINT_IMPRESSAO_LOTE, HttpMethod.POST, request, String.class);

			String response = exchange.getBody();
			JsonNode dados = new ObjectMapper().readTree(response).get("_dados");
			
			Map<String, String> res = new HashMap<>();
			
			res.put(PROTOCOLO, dados.get(PROTOCOLO).asText());
			res.put(SITUACAO, dados.get(SITUACAO).asText());
			
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] salvandoPDF(String protocolo, String cnpjCedente){
		restReqParams.addHeader(CNPJ_CEDENTE, cnpjCedente);
		restReqParams.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_PDF));
		
		HttpEntity<?> request = new HttpEntity<>(restReqParams.getHeaders());
		ResponseEntity<byte[]> response = restTemplate.exchange(END_POINT_IMPRESSAO_LOTE + "/" + protocolo, HttpMethod.GET, request, byte[].class);
		
		return response.getBody();
	}

}
