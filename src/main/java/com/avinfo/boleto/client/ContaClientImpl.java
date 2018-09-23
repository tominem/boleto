package com.avinfo.boleto.client;

import static com.avinfo.boleto.client.dto.TecnospedHeaders.CNPJ_CEDENTE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.avinfo.boleto.config.TecnospedRestReqParams;
import com.avinfo.boleto.domain.Conta;

@Component
public class ContaClientImpl implements ContaClient {
	
	private RestTemplate restTemplate;
	private TecnospedRestReqParams restReqParams;

	@Autowired
	public ContaClientImpl(final RestTemplate restTemplate, final TecnospedRestReqParams restReqParams) {
		this.restTemplate = restTemplate;
		this.restReqParams = restReqParams;
	}
	
	@Override
	public Conta cadastrar(Conta conta, String cnpjCedente){
		HttpEntity<Conta> request = new HttpEntity<>(conta, restReqParams.addHeader(CNPJ_CEDENTE, cnpjCedente));
		ResponseEntity<Conta> exchange = restTemplate.postForEntity("/cedentes/contas", request, Conta.class);

		return exchange.getBody();
	}

	@Override
	public Conta editar(Conta conta, String cpfcnpjCedente, Long idIntegracao){
		HttpEntity<Conta> request = new HttpEntity<>(conta, restReqParams.addHeader(CNPJ_CEDENTE, cpfcnpjCedente));
		ResponseEntity<Conta> exchange = restTemplate.exchange("/cedentes/contas/"+idIntegracao, HttpMethod.PUT, request, Conta.class);
		
		return exchange.getBody();
	}

}
