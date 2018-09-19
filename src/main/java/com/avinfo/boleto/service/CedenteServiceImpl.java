package com.avinfo.boleto.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinfo.boleto.client.CedenteClient;
import com.avinfo.boleto.domain.Cedente;
import com.avinfo.boleto.repository.CedenteRepository;

@Service
public class CedenteServiceImpl implements CedenteService{

	private final CedenteClient cedenteClient;
	private final CedenteRepository cedenteRepository;
	
	@Autowired
	public CedenteServiceImpl(final CedenteClient cedenteClient, final CedenteRepository cedenteRepository) {
		this.cedenteClient = cedenteClient;
		this.cedenteRepository = cedenteRepository;
	}
	
	@Override
	@Transactional
	public Cedente cadastrarCedente(Cedente cedente) {
		Cedente cedenteAhSerSalvo = cedenteClient.cadastrarCedente(cedente);
		return cedenteRepository.save(cedenteAhSerSalvo);
	}

	@Override
	public Optional<Cedente> findById(Long id) {
		return cedenteRepository.findById(id);
	}

	
	
}
