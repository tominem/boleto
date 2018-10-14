package com.avinfo.boleto.service;

import java.util.List;

import com.avinfo.boleto.domain.Boleto;

public interface BoletoService {
	
	List<Boleto> incluir(List<Boleto> boletos, String cpfCnpjCedente);

	Boleto save(Boleto boleto);

	List<Boleto> save(List<Boleto> boletos);

	List<Boleto> findByIds(Long ... ids);

	List<Boleto> findByIds(List<Long> collect);

}
