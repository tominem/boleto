package com.avinfo.boleto.domain;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class BoletoLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String erro;
	
	private BoletoSituacao falha;
	
	@Basic
	private LocalDateTime dataHora = LocalDateTime.now();
	
	@ManyToOne
	private Boleto boleto;

	private BoletoSituacao boletoSituacao;
	
	public BoletoLog() {}
	
	public BoletoLog(BoletoSituacao boletoSituacao){
		this.boletoSituacao = boletoSituacao;
	}
	
	public BoletoLog(String erro, BoletoSituacao falha) {
		this.erro = erro;
		this.falha = falha;
	}
	
}
