package com.avinfo.boleto.service;

import java.util.List;
import java.util.Map;

import com.avinfo.boleto.client.dto.TipoImpressao;
import com.avinfo.boleto.domain.Boleto;

public interface BoletoService {
	
	List<Boleto> incluir(List<Boleto> boletos, String cpfCnpjCedente);

	Boleto save(Boleto boleto);

	List<Boleto> save(List<Boleto> boletos);

	List<Boleto> findByIds(Long ... ids);

	List<Boleto> findByIds(List<Long> collect);

	List<Boleto> getFromWS(Map<String, String> params, String cnpjCpfCedente);

	List<Boleto> solicitaImpressao(List<Boleto> boletos, TipoImpressao tipoImpressao, String cpfCnpjCedente);

	byte[] salvarPDF(String procolo, String cnpjCedente);

}
