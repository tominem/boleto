package com.avinfo.boleto.service;

import java.util.Optional;

import com.avinfo.boleto.domain.Conta;

public interface ContaService {
	
	Conta cadastrarConta(Conta conta);

	Optional<Conta> findById(Long id);


}
