package com.avinfo.boleto.config;

import org.springframework.http.HttpHeaders;

import com.avinfo.boleto.client.dto.TecnospedHeaders;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TecnospedRestReqParams {
	
	private final String boletoAPIHost;
	private final HttpHeaders tecnospedHeaders;

	public final String getBoletoAPIHost() {
		return boletoAPIHost;
	}

	public final HttpHeaders getHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (tecnospedHeaders != null) {
			httpHeaders.addAll(tecnospedHeaders);
		}
		return httpHeaders;
	}
	
	public HttpHeaders addHeader(TecnospedHeaders headerKey, String value){
		HttpHeaders tecnospedHeaders = getHeaders();
		tecnospedHeaders.add(headerKey.getHeader(), value);
		return tecnospedHeaders;
	}

}
