package com.avinfo.boleto.client;

import java.util.List;
import java.util.Map;

import com.avinfo.boleto.client.dto.BoletoConsultaDTO;
import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.client.dto.BoletoSolicitaImpressaoDTO;
import com.avinfo.boleto.domain.Boleto;

public interface BoletoClient {
	
	public static final String SITUACAO = "situacao";
	public static final String PROTOCOLO = "protocolo";
	
	public static final String END_POINT_CONSULTA 		 = "/boletos";
	public static final String END_POINT_LOTE 	  		 = "/boletos/lote";
	public static final String END_POINT_IMPRESSAO_LOTE  = "/boletos/impressao/lote";
	
	BoletoListDTO incluir(List<Boleto> boletos, String cnpjCedente);

	BoletoConsultaDTO consultaBoleto(Map<String, String> params, String cnpjCedente);

	Map<String, String> solicitaImpressao(BoletoSolicitaImpressaoDTO dto, String cnpjCedente);

	byte[] salvandoPDF(String procolo, String cnpjCedente);

}
