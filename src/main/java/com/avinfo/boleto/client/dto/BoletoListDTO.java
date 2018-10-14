package com.avinfo.boleto.client.dto;

import java.util.HashMap;
import java.util.Map;

import com.avinfo.boleto.domain.Boleto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

@JsonDeserialize(using=BoletoListDTODeserializer.class)
@Getter
@Setter
public class BoletoListDTO {
	
	private Map<Long, Boleto> successeds = new HashMap<>();
	private Map<Long, Boleto> faileds = new HashMap<>();
	
	public void addSuccessed(Boleto boleto){
		successeds.put(boleto.getId(), boleto);
	}

	public void addFailed(Boleto boleto){
		faileds.put(boleto.getId(), boleto);
	}

}
