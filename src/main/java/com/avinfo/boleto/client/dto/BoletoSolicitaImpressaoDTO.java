package com.avinfo.boleto.client.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.avinfo.boleto.domain.Boleto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using=BoletoSolicitaImpressaoDTOSerialize.class)
public class BoletoSolicitaImpressaoDTO {
	
	private TipoImpressao tipoImpressao;
	private Map<Long, Boleto> boletos;
	
	
	public BoletoSolicitaImpressaoDTO(List<Boleto> boletos, TipoImpressao tipoImpressao) {
		this.boletos = new HashMap<>(boletos.stream().collect(Collectors.toMap(Boleto::getId, Function.identity())));
		this.tipoImpressao = tipoImpressao;
	}

	public Map<Long, Boleto> getBoletos() {
		return boletos;
	}

	public TipoImpressao getTipoImpressao() {
		return tipoImpressao;
	}

	public void setTipoImpressao(TipoImpressao tipoImpressao) {
		this.tipoImpressao = tipoImpressao;
	}

}
