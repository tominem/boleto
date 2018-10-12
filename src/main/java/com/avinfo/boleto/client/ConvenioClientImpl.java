package com.avinfo.boleto.client;

import static com.avinfo.boleto.client.dto.TecnospedHeaders.CNPJ_CEDENTE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.avinfo.boleto.config.TecnospedRestReqParams;
import com.avinfo.boleto.domain.Convenio;

public class ConvenioClientImpl implements ConvenioClient {

	private static final String END_POINT = "/cedentes/contas/convenios";
	
	private RestTemplate restTemplate;
	private TecnospedRestReqParams restReqParams;

	@Autowired
	public ConvenioClientImpl(final RestTemplate restTemplate, final TecnospedRestReqParams restReqParams) {
		this.restTemplate = restTemplate;
		this.restReqParams = restReqParams;
	}
	
	@Override
	public Convenio cadastrar(Convenio convenio, String cnpjCedente) {
		HttpEntity<Convenio> request = new HttpEntity<>(convenio, restReqParams.addHeader(CNPJ_CEDENTE, cnpjCedente));
		ResponseEntity<Convenio> exchange = restTemplate.postForEntity(END_POINT, request, Convenio.class);

		return exchange.getBody();
	}

	@Override
	public Convenio editar(Convenio convenio, String cpfcnpjCedente, Long idIntegracao) {
		HttpEntity<Convenio> request = new HttpEntity<>(convenio, restReqParams.addHeader(CNPJ_CEDENTE, cpfcnpjCedente));
		ResponseEntity<Convenio> exchange = restTemplate.exchange(END_POINT + "/" + idIntegracao, HttpMethod.PUT, request, Convenio.class);

		return exchange.getBody();
	}

}
