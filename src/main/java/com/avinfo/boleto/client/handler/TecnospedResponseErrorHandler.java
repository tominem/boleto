package com.avinfo.boleto.client.handler;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.avinfo.boleto.client.exception.NotFoundException;
import com.avinfo.boleto.client.exception.TecnospedRestClientError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TecnospedResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
        throws IOException {

        return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR 
          || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
        throws IOException {

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            //Handle SERVER_ERROR
        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {

        	//Handle CLIENT_ERROR
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException();
            }
            
            
            final ObjectMapper objectMapper = new ObjectMapper();
            JsonNode mainNode = objectMapper.readTree(httpResponse.getBody());
            String errorMessage = ofNullable(mainNode.get("_mensagem"))
            	.map(node -> node.asText())
            	.orElse("");
            
            StringBuilder dadosErro = new StringBuilder();
            Optional.ofNullable(mainNode.get("_dados"))
            	.ifPresent(node -> {
            		if (node.isArray()) {
						node.forEach(dadosErro::append);
					} else{
						dadosErro.append(node.toString());
					}
        	});
            
			String fullMessageError = format("Error_code: %d (%s) - %s: %s", 
					httpResponse.getRawStatusCode(), 
					httpResponse.getStatusText(), 
					errorMessage, 
					dadosErro);
			
            throw new TecnospedRestClientError(fullMessageError);
        }
    }
}
