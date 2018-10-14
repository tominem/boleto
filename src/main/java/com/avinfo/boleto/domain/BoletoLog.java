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
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class BoletoLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String erro;
	
	private BoletoSituacao situacao;
	
	@Basic
	private LocalDateTime dataHora = LocalDateTime.now();
	
	@ManyToOne
	private Boleto boleto;

	public BoletoLog() {}
	
	public BoletoLog(BoletoSituacao situacao){
		this.situacao = situacao;
	}
	
	public BoletoLog(String erro, BoletoSituacao situacao) {
		this.erro = erro;
		this.situacao = situacao;
	}
	
}
