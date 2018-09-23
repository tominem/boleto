package com.avinfo.boleto.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avinfo.boleto.client.ConvenioDTOSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonSerialize(using=ConvenioDTOSerializer.class)
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder(toBuilder=true)
@Entity
public class Convenio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private final Long id;
	private final Long idIntegracao;
    private final String numeroConvenio;
    private final String descricaoConvenio;
    private final String carteira;
    private final String especie;
    private final LocalDateTime criado;
    private final LocalDateTime atualizado;
    private final String padraoCNAB;
    private final Boolean utilizaVan;
    private final String numeroRemessa;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private final Conta conta;
    
    Convenio() {
    	id = null;                 
    	idIntegracao = null;     
    	numeroConvenio = null;   
    	descricaoConvenio = null;
    	carteira = null;         
    	especie = null;          
    	criado = null;           
    	atualizado = null;       
    	padraoCNAB = null;       
    	utilizaVan = null;       
    	numeroRemessa = null;
    	conta = null;
    }
    
}
