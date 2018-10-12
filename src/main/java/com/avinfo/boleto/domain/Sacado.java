package com.avinfo.boleto.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Getter
@Entity
public class Sacado {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private final Long id;
	private final String cpjcnpj;
	private final String email;
	private final String enderecoNumero;
	private final String enderecoBairro;
	private final String enderecoCEP;
	private final String enderecoCidade;
	private final String enderecoComplemento;
	private final String enderecoLogradouro;
	private final String enderecoPais;
	private final String enderecoUF;
	private final String nome;
	private final String telefone;
	private final String celular;

	Sacado(){
		id = null;
		cpjcnpj = null;
		email = null;
		enderecoNumero = null;
		enderecoBairro = null;
		enderecoCEP = null;
		enderecoCidade = null;
		enderecoComplemento = null;
		enderecoLogradouro = null;
		enderecoPais = null;
		enderecoUF = null;
		nome = null;
		telefone = null;
		celular = null;

	}
	
}
