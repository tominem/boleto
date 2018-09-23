package com.avinfo.boleto.service;

import java.util.Optional;

import com.avinfo.boleto.domain.Cedente;

public interface CedenteService {

	Cedente save(Cedente cedente);

	Optional<Cedente> findById(Long id);

}
