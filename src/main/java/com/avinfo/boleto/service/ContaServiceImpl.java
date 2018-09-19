package com.avinfo.boleto.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinfo.boleto.client.ContaClient;
import com.avinfo.boleto.domain.Conta;
import com.avinfo.boleto.repository.ContaRepository;

@Service
public class ContaServiceImpl implements ContaService {

	private ContaClient contaClient;
	private ContaRepository contaRepository;

	@Autowired
	public ContaServiceImpl(ContaClient contaClient, ContaRepository contaRepository) {
		this.contaClient = contaClient;
		this.contaRepository = contaRepository;
	}
	
	@Override
	@Transactional
	public Conta cadastrarConta(Conta conta) {
		Conta contaCadastrada = contaClient.cadastrarConta(conta);
		return contaRepository.save(contaCadastrada);
	}

	@Override
	public Optional<Conta> findById(Long id) {
		return contaRepository.findById(id);
	}

}
