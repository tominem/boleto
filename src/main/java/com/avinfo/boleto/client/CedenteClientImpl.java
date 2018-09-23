package com.avinfo.boleto.client;

import static com.avinfo.boleto.client.dto.TecnospedHeaders.CNPJ_CEDENTE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.avinfo.boleto.config.TecnospedRestReqParams;
import com.avinfo.boleto.domain.Cedente;

@Component
public class CedenteClientImpl implements CedenteClient {
	
	private final RestTemplate restTemplate;
	private final TecnospedRestReqParams restReqParams;

	@Autowired
	public CedenteClientImpl(final RestTemplate restTemplate, final TecnospedRestReqParams restReqParams) {
		this.restTemplate = restTemplate;
		this.restReqParams = restReqParams;
	}

	@Override
	public Cedente cadastrar(final Cedente cedente) {
		HttpEntity<Cedente> request = new HttpEntity<>(cedente, restReqParams.getHeaders());
		ResponseEntity<Cedente> exchange = restTemplate.postForEntity("/cedentes", request, Cedente.class);

		return exchange.getBody();
	}
	
	@Override
	public Cedente editar(Cedente cedente, Long idIntegracao){
		HttpEntity<Cedente> request = new HttpEntity<>(cedente, restReqParams.addHeader(CNPJ_CEDENTE, cedente.getCpfCnpj()));
		ResponseEntity<Cedente> exchange = restTemplate.exchange("/cedentes/"+idIntegracao, HttpMethod.PUT, request, Cedente.class);

		return exchange.getBody();
	}

}
