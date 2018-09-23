package com.avinfo.boleto.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avinfo.boleto.client.ContaDTODeserialize;
import com.avinfo.boleto.client.ContaDTOSerialize;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonSerialize(using=ContaDTOSerialize.class)
@JsonDeserialize(using=ContaDTODeserialize.class)
@JsonInclude(Include.NON_NULL)
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(toBuilder=true)
@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private final Long   id;
	private final Long idIntegracao;
    private final String codigoBanco;
    private final String agencia;
    private final String agenciaDV;
    private final String conta;
    private final String contaDV;
    private final String tipoConta;
    private final String codBeneficiario;
    private final String idCedente;
    private final LocalDateTime criado;
    private final LocalDateTime atualizado;
    private final String codEmpresa;
    
    @ManyToOne(cascade=CascadeType.PERSIST)
    private final Cedente cedente;
    
    Conta() {
    	id = null;            
    	idIntegracao = null;    
    	codigoBanco = null;    
    	agencia = null;         
    	agenciaDV = null;      
    	conta = null;           
    	contaDV = null;        
    	tipoConta = null;      
    	codBeneficiario = null;
    	idCedente = null;      
    	criado = null;          
    	atualizado = null;      
    	codEmpresa = null;
    	cedente = null;
    }
	
}
