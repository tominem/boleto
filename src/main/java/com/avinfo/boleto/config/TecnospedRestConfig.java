package com.avinfo.boleto.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.avinfo.boleto.client.dto.TecnospedToken;
import com.avinfo.boleto.client.handler.TecnospedResponseErrorHandler;

@Configuration
public class TecnospedRestConfig {
	
	/**
	 * Injeção do host da API Tecnosped
	 * 
	 * @param hostBase
	 * @param version
	 * @return
	 */
	@Bean
	public String boletoAPIHost(
			@Value("${tecnosped.boleto.api.host}") String hostBase, 
			@Value("${tecnosped.boleto.api.version}") String version){
		return new StringBuilder(hostBase)
				.append("v")
				.append(version)
				.toString();
	}
	
	/**
	 * Injeção do token da API do Tecnosped
	 * 
	 * @param environment
	 * @param cnpjSH
	 * @param tokenSH
	 * @return
	 */
	@Bean
	public TecnospedToken tecnospedToken(
			Environment environment,
			@Value("${tecnosped.boleto.api.cnpjsh:}") String cnpjSH,
			@Value("${tecnosped.boleto.api.tokensh:}") String tokenSH){
		for (String activeProfile : environment.getActiveProfiles()) {
			if (activeProfile.equals("homo")) {
				return new TecnospedToken(cnpjSH, tokenSH);
			}
		}
		
		//TODO buscar do banco de dados
		return new TecnospedToken(cnpjSH, tokenSH);
	}
	
	@Bean
	public RestTemplateBuilder restTemplateBuilder(String boletoAPIHost) {
		return new RestTemplateBuilder()
	            .rootUri(boletoAPIHost);
	}
	
	@Bean
	public HttpHeaders tecnospedHeaders(TecnospedToken token){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("cnpj-sh", token.getCnpjSH());
		httpHeaders.add("token-sh", token.getTokenSH());
		
		return httpHeaders;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.errorHandler(new TecnospedResponseErrorHandler()).build();
	}
	
	@Bean
	public TecnospedRestReqParams restReqParams(String boletoAPIHost, HttpHeaders tecnospedHeaders){
		return new TecnospedRestReqParams(boletoAPIHost, tecnospedHeaders);
	}

}
