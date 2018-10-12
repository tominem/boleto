package com.avinfo.boleto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinfo.boleto.client.BoletoClient;
import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.repository.BoletoRepository;

@Service
public class BoletoServiceImpl implements BoletoService{

	private BoletoClient boletoClient;
	private BoletoRepository boletoRepository;

	@Autowired
	public BoletoServiceImpl(BoletoClient boletoClient, BoletoRepository boletoRepository) {
		this.boletoClient = boletoClient;
		this.boletoRepository = boletoRepository;
	}
	
	@Override
	public Boleto save(Boleto boleto){
		return boletoRepository.save(boleto);
	}
	
	@Override
	public List<Boleto> incluir(List<Boleto> boletos, String cpfCnpjCedente) {
//		List<Boleto> boletosIncluidos = boletoClient.incluir(boletos, cpfCnpjCedente);
//		return boletosIncluidos;
		
		//TODO implements
		
		return null;
		
	}

}
