package com.avinfo.boleto.service;

import java.util.Optional;

import com.avinfo.boleto.domain.Convenio;

public interface ConvenioService {
	
	Convenio save(Convenio convenio, String cnpjCedente);

	Optional<Convenio> findById(Long id);

}
