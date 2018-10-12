package com.avinfo.boleto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinfo.boleto.client.ConvenioClient;
import com.avinfo.boleto.domain.Convenio;
import com.avinfo.boleto.repository.ConvenioRepository;

@Service
public class ConvenioServiceImpl implements ConvenioService {

	@Autowired
	private ConvenioClient convenioClient;
	
	@Autowired
	private ConvenioRepository convenioRepository;
	
	@Override
	public Convenio save(Convenio convenio, String cnpjCedente) {
		Long idIntegracao = convenio.getIdIntegracao();
		Convenio convenioPersistedOrUpdated = idIntegracao != null ?
				convenioClient.editar(convenio, cnpjCedente, idIntegracao) :
				convenioClient.cadastrar(convenio, cnpjCedente);
		
		// settar o id para evitar que ele insira um convenio já cadastrado 
		// anteriormente
		Convenio convenioToPersist = convenioPersistedOrUpdated.toBuilder()
			.id(convenio.getId()).build();
				
		return convenioRepository.save(convenioToPersist);
	}

	@Override
	public Optional<Convenio> findById(Long id) {
		return convenioRepository.findById(id);
	}

}
