package com.avinfo.boleto.client;

import static com.avinfo.boleto.client.dto.TecnospedHeaders.CNPJ_CEDENTE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.config.TecnospedRestReqParams;
import com.avinfo.boleto.domain.Boleto;

@Component
public class BoletoClientImpl implements BoletoClient {

	public static final String END_POINT = "/boletos/lote";
	
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
		ResponseEntity<BoletoListDTO> exchange = restTemplate.exchange(END_POINT, HttpMethod.POST, request, BoletoListDTO.class);

		return exchange.getBody();
	}

}
