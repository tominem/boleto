package com.avinfo.boleto.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.Remessa;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeraRemessaRetDTO {

	private List<Remessa> remessas = new ArrayList<>();
	private List<Boleto> falhas = new ArrayList<>();
	
	
}
