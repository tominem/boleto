package com.avinfo.boleto.service;

import org.springframework.stereotype.Service;

import com.avinfo.boleto.client.dto.GeraRemessaRetDTO;
import com.avinfo.boleto.domain.Boleto;

@Service
public class RemessaServiceImpl implements RemessaService{

	@Override
	public GeraRemessaRetDTO geraRemessa(Iterable<Boleto> boletos) {
		
		
		
		return null;
	}

}
