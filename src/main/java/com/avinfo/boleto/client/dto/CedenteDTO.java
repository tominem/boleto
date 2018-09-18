package com.avinfo.boleto.client.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class CedenteDTO {

	private final String CedenteRazaoSocial;
	private final String CedenteNomeFantasia;
	private final String CedenteCPFCNPJ;
	private final String CedenteEnderecoLogradouro;
	private final String CedenteEnderecoNumero;
	private final String CedenteEnderecoComplemento;
	private final String CedenteEnderecoBairro;
	private final String CedenteEnderecoCEP;
	private final String CedenteEnderecoCidadeIBGE;
	private final String CedenteTelefone;
	private final String CedenteEmail;
	
	CedenteDTO() {
		this(null, null, null, null, null, null, null, null, null, null, null);
	}
	
	
}
