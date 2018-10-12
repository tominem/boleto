package com.avinfo.boleto.service;

import java.util.List;

import com.avinfo.boleto.domain.Boleto;

public interface BoletoService {
	
	List<Boleto> incluir(List<Boleto> boletos, String cpfCnpjCedente);

	Boleto save(Boleto boleto);

}
