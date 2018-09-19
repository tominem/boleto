package com.avinfo.boleto.client;

import static com.avinfo.boleto.util.ParserUtil.newHttpHeader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.avinfo.boleto.config.TecnospedRestReqParams;
import com.avinfo.boleto.domain.Conta;

@Component
public class ContaClient {
	
	private RestTemplate restTemplate;
	private TecnospedRestReqParams restReqParams;

	@Autowired
	public ContaClient(final RestTemplate restTemplate, final TecnospedRestReqParams restReqParams) {
		this.restTemplate = restTemplate;
		this.restReqParams = restReqParams;
	}
	
	public Conta cadastrarConta(Conta conta){
		HttpHeaders tecnospedHeaders = newHttpHeader(restReqParams.getTecnospedHeaders());
		tecnospedHeaders.add("cnpj-cedente", conta.getCedente().getCpfCnpj());

		HttpEntity<Conta> request = new HttpEntity<>(conta, tecnospedHeaders);
		ResponseEntity<Conta> exchange = restTemplate.postForEntity("/cedentes/contas", request, Conta.class);

		return exchange.getBody();
	}
	
}
