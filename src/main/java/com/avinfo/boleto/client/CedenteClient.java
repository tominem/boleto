package com.avinfo.boleto.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.avinfo.boleto.config.TecnospedRestReqParams;
import com.avinfo.boleto.domain.Cedente;

@Component
public class CedenteClient {
	
	private final RestTemplate restTemplate;
	private final TecnospedRestReqParams restReqParams;

	@Autowired
	public CedenteClient(final RestTemplate restTemplate, final TecnospedRestReqParams restReqParams) {
		this.restTemplate = restTemplate;
		this.restReqParams = restReqParams;
	}

	public Cedente cadastrarCedente(final Cedente cedente) {
		HttpEntity<Cedente> request = new HttpEntity<>(cedente, restReqParams.getTecnospedHeaders());
		ResponseEntity<Cedente> exchange = restTemplate.postForEntity("/cedentes", request, Cedente.class);

		return exchange.getBody();
	}

}
