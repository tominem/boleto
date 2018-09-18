package com.avinfo.boleto.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.avinfo.boleto.client.CedenteDTODeserealizer;
import com.avinfo.boleto.client.CedenteDTOSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonSerialize(using=CedenteDTOSerializer.class)
@JsonDeserialize(using=CedenteDTODeserealizer.class)
@Builder(toBuilder=true)
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Entity
public class Cedente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private final Long id;
	private final Long idIntegracao;
	private final String razaosocial;
	private final String nomefantasia;
	private final String cpfCnpj;
	private final String logradouro;
	private final String numero;
	private final String complemento;
	private final String bairro;
	private final String cep;
	private final Integer idCidade;
	private final String telefone;
	private final String email;
	private final LocalDateTime criado;
	private final LocalDateTime atualizado;
	private final String tokenCedente;
	private final String tokenEsales;
	private final String situacao;
	private final Long idSoftwareHouse;
	private final String motivoInativacao;
	private final LocalDateTime dataAtivacao;
	private final LocalDateTime dataInativacao;
	private final String certificado;
	private final LocalDateTime dtvencimentoCertificado;
	private final String uf;
	private final String contas;
	private final Integer cidadeibge;
	private final String cidade;
	
	Cedente(){
		id = null;
		idIntegracao = null;
		razaosocial = null;
		nomefantasia = null;
		cpfCnpj = null;
		logradouro = null;
		numero = null;
		complemento = null;
		bairro = null;
		cep = null;
		idCidade = null;
		telefone = null;
		email = null;
		criado = null;
		atualizado = null;
		tokenCedente = null;
		tokenEsales = null;
		situacao = null;
		idSoftwareHouse = null;
		motivoInativacao = null;
		dataAtivacao = null;
		dataInativacao = null;
		certificado = null;
		dtvencimentoCertificado = null;
		uf = null;
		contas = null;
		cidadeibge = null;
		cidade = null;
	}
		
}
