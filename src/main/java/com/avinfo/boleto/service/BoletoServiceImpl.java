package com.avinfo.boleto.service;

import static com.avinfo.boleto.domain.BoletoSituacao.SALVO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinfo.boleto.client.BoletoClient;
import com.avinfo.boleto.client.dto.BoletoConsultaDTO;
import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.client.dto.BoletoSolicitaImpressaoDTO;
import com.avinfo.boleto.client.dto.TipoImpressao;
import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.BoletoSituacao;
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
	@Transactional
	public Boleto save(Boleto boleto){
		return boletoRepository.save(boleto);
	}
	
	@Transactional
	@Override
	public List<Boleto> incluir(List<Boleto> boletos, String cpfCnpjCedente) {
		BoletoListDTO boletoListDTO = boletoClient.incluir(boletos, cpfCnpjCedente);
		
		Map<Long, Boleto> successeds = boletoListDTO.getSuccesseds();
		Map<Long, Boleto> faileds = boletoListDTO.getFaileds();
		
		Map<Long, Boleto> boletosTransmitidos = new HashMap<>();
		boletosTransmitidos.putAll(successeds);
		boletosTransmitidos.putAll(faileds);
		
		bindInformation(boletos, boletosTransmitidos);
		
		boletoRepository.saveAll(boletos);
		
		return boletos;
	}

	private void bindInformation(List<Boleto> boletos, Map<Long, Boleto> boletosTransmitidos) {
		for (Boleto boleto : boletos) {
			Boleto boletoTrans = boletosTransmitidos.get(boleto.getId());
			
			if (boletoTrans == null)
				throw new RuntimeException("Número do documento: "+ boleto.getId() +" transmitido não encontrado");
			
			if (boletoTrans.getSituacao().equals(SALVO)) {
				boleto.setIdIntegracao(boletoTrans.getIdIntegracao());
				boleto.setSituacao(boletoTrans.getSituacao());
			} else if(boletoTrans.getSituacao().equals(BoletoSituacao.FALHA)){
				boleto.setSituacao(boletoTrans.getSituacao());
			}
			
			boleto.addBoletoLog(boletoTrans.getBoletoLog());
		}
	}

	@Override
	@Transactional
	public List<Boleto> save(List<Boleto> boletos) {
		return boletoRepository.saveAll(boletos);
	}

	@Override
	public List<Boleto> findByIds(Long... ids) {
		return boletoRepository.findAllById(Arrays.asList(ids));
	}

	@Override
	public List<Boleto> findByIds(List<Long> ids) {
		return boletoRepository.findAllById(ids);
	}

	@Override
	public List<Boleto> getFromWS(Map<String, String> params, String cpfCnpjCedente) {
		BoletoConsultaDTO boletoConsultaDTO = boletoClient.consultaBoleto(params, cpfCnpjCedente);
		Set<Long> keySet = boletoConsultaDTO.getBoletos().keySet();
		return boletoRepository.findAllById(keySet);
	}

	@Transactional
	@Override
	public List<Boleto> solicitaImpressao(List<Boleto> boletos, TipoImpressao tipoImpressao, String cpfCnpjCedente) {
		BoletoSolicitaImpressaoDTO dto = new BoletoSolicitaImpressaoDTO(boletos, tipoImpressao);
		Map<String, String> resp = boletoClient.solicitaImpressao(dto, cpfCnpjCedente);
		
		String protocolo = resp.get(BoletoClient.PROTOCOLO);
		BoletoSituacao boletoSituacao = BoletoSituacao.valueOf(resp.get(BoletoClient.SITUACAO));
		
		boletos.stream().forEach(b -> {
			b.setSituacao(boletoSituacao);
			b.setProtocoloImpressao(protocolo);
		});
		
		return boletoRepository.saveAll(boletos);
	}
	
	@Override
	public byte[] salvarPDF(String procolo, String cnpjCedente){
		return boletoClient.salvandoPDF(procolo, cnpjCedente);
	}

}
