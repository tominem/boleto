package com.avinfo.boleto.client.dto;

import java.util.List;

import com.avinfo.boleto.domain.Boleto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize(using=GeraRemessaDTOSerializer.class)
public class GeraRemessaReqDTO {

	private List<Boleto> boletos;
	
}
