package com.avinfo.boleto.client;

import java.util.List;

import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.domain.Boleto;

public interface BoletoClient {
	
	BoletoListDTO incluir(List<Boleto> boletos, String cnpjCedente);

}
