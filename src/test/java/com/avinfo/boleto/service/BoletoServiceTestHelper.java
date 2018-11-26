package com.avinfo.boleto.service;

import static com.avinfo.boleto.domain.BoletoSituacao.FALHA;
import static com.avinfo.boleto.domain.BoletoSituacao.SALVO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.avinfo.boleto.client.BoletoTestFactory;
import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.BoletoLog;
import com.avinfo.boleto.domain.BoletoSituacao;

public class BoletoServiceTestHelper {
	
	private EntityManager em;
	
	public BoletoServiceTestHelper(EntityManager em){
		this.em = em;
	}
	
	public BoletoServiceTestHelper() {}

	public boolean checkBoletoLog(BoletoLog boletoLog, BoletoSituacao situacao){
		boolean checkSituacao = boletoLog.getSituacao().equals(situacao);
		boolean checkIdGraterThanZero = boletoLog.getId() != null && boletoLog.getId() > 0L;
		boolean checkMsgErro = situacao == FALHA ? "Teste de erro".equals(boletoLog.getErro()) : true;
		
		return checkSituacao && checkIdGraterThanZero && checkMsgErro;
	}
	
	public boolean checkBoleto(Boleto boleto, BoletoSituacao situacao){
		boolean checkIdIntegracao = situacao == SALVO ? (boleto.getIdIntegracao() != null && boleto.getIdIntegracao().equals("idIntegracao" + boleto.getId())) : true;
		boolean checkBoletoSituacaoSalvo = boleto.getSituacao().equals(situacao);
		
		return checkIdIntegracao && checkBoletoSituacaoSalvo;
	}
	

	public BoletoListDTO createBoletoFalhaListDTO(List<Boleto> boletos) {
		return createBoletoListDTO(boletos, FALHA, "Teste de erro");
	}
	
	public BoletoListDTO createBoletoSalvosListDTO(List<Boleto> boletos) {
		return createBoletoListDTO(boletos, SALVO, null);
	}
	
	public BoletoListDTO createBoletoListDTO(List<Boleto> boletos, BoletoSituacao situacao, String erro) {
		
		BoletoListDTO boletoListDTO = new BoletoListDTO();
		
		for (Boleto boleto : boletos) {

			Boleto boletoTrans = boleto.toBuilder()
					.idIntegracao(situacao.equals(SALVO) ? "idIntegracao" + boleto.getId() : null)
					.tituloNumeroDocumento(boleto.getId() + "")
					.situacao(situacao)
					.build();
			
			// retirar objeto do entitymanager
			em.detach(boletoTrans);
			
			boletoTrans.setBoletoLog(new ArrayList<>());
			boletoTrans.addBoletoLog(new BoletoLog(erro, situacao));
			
			if (situacao == BoletoSituacao.SALVO) {
				boletoListDTO.addSuccessed(boletoTrans);
			} else{
				boletoListDTO.addFailed(boletoTrans);
			}
			
		}
		
		return boletoListDTO;
	}

	public Boleto buildBoleto() {
		return new BoletoTestFactory().buildBoleto();
	}

}
