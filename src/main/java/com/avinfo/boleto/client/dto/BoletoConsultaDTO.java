package com.avinfo.boleto.client.dto;

import java.util.HashMap;
import java.util.Map;

import com.avinfo.boleto.domain.Boleto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=BoletoConsultaDTODeserializer.class)
public class BoletoConsultaDTO {
	
	private Map<Long, Boleto> boletos = new HashMap<>();
	
	public void addBoleto(Boleto boleto){
		boletos.put(boleto.getId(), boleto);
	}
	
	public Map<Long, Boleto> getBoletos(){
		return boletos;
	}

}
