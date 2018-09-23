package com.avinfo.boleto.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinfo.boleto.client.ContaClient;
import com.avinfo.boleto.domain.Cedente;
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
	public Conta save(Conta conta) {
		Cedente cedente = conta.getCedente();
		String cpfCnpjCedente = cedente.getCpfCnpj();
		Long idIntegracao = conta.getIdIntegracao();
		Conta contaDTO = idIntegracao != null ? 
				contaClient.editar(conta, cpfCnpjCedente, idIntegracao) :
				contaClient.cadastrar(conta, cpfCnpjCedente);
		
		Conta contaAhSalvar = contaDTO.toBuilder()
									  .id(conta.getId())
									  .cedente(cedente)
									  .build();
				
		return contaRepository.save(contaAhSalvar);
	}

	@Override
	public Optional<Conta> findById(Long id) {
		return contaRepository.findById(id);
	}
	
}
