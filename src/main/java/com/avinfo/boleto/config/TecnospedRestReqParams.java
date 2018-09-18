package com.avinfo.boleto.config;

import org.springframework.http.HttpHeaders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TecnospedRestReqParams {

	private final String boletoAPIHost;
	private final HttpHeaders tecnospedHeaders;
	

}
