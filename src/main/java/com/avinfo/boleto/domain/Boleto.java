package com.avinfo.boleto.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avinfo.boleto.client.dto.BoletoDTOSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonSerialize(using=BoletoDTOSerializer.class)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Builder(toBuilder=true)
@Entity
public class Boleto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String idIntegracao;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Convenio convenio;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Sacado sacado;
	
	private LocalDate tituloDataDesconto;
	private String tituloValorDesconto;
	private LocalDate tituloDataEmissao;
	private LocalDate tituloDataVencimento;
	private String tituloValorJuros;
	private String tituloPrazoProtesto;
	private String tituloMensagem01;
	private String tituloMensagem02;
	private String tituloMensagem03;
	private String tituloMensagem04;
	private String tituloMensagem05;
	private String tituloMensagem06;
	private String tituloMensagem07;
	private String tituloMensagem08;
	private String tituloMensagem09;
	private String tituloNossoNumero;
	private String tituloNumeroDocumento;
	private String tituloValor;
	private String tituloLocalPagamento;
	private String tituloValorAbatimento;
	private String tituloValorDescontoTaxa;
	private String tituloValorMulta;
	private String tituloValorMultaTaxa;
	private LocalDate tituloDataJuros;
	private String tituloCodigoMulta;
	private LocalDate tituloDataMulta;
	private String tituloAceite;
	private String tituloPrazoBaixa;
	private String tituloSacadorAvalista;
	private String tituloInscricaoSacadorAvalista;
	private String tituloCodBaixaDevolucao;
	private String tituloCodProtesto;
	private String tituloCodigoJuros;
	private String tituloDocEspecie;
	private String tituloCodDesconto;
	private String tituloCodEmissaoBloqueto;
	private String tituloOutrasDeducoes;
	private String tituloOutrosAcrescimos;
	private String tituloUsoBanco;
	private String tituloPagamentoMinimo;
	private String tituloForcarFatorVencimento;
	private String tituloInformacoesAdicionais;
	private String tituloInstrucoes;
	private String tituloParcela;
	private String tituloVariacaoCarteira;
	private String tituloCategoria;
	private String tituloModalidade;
	private String tituloCodCliente;
	private String tituloCodCedenteBarra;
	private String tituloIos;
	private String tituloCip;
	private BoletoSituacao situacao;
	private String protocoloImpressao;
	
	@OneToMany(mappedBy="boleto", cascade=CascadeType.ALL, orphanRemoval=true)
	@Builder.Default
	private List<BoletoLog> boletoLog = new ArrayList<>();
	
	Boleto(){
		id = null;
		idIntegracao = null;
		convenio = null;
		sacado = null;
		tituloDataDesconto = null;
		tituloValorDesconto = null;
		tituloDataEmissao = null;
		tituloDataVencimento = null;
		tituloValorJuros = null;
		tituloPrazoProtesto = null;
		tituloMensagem01 = null;
		tituloMensagem02 = null;
		tituloMensagem03 = null;
		tituloMensagem04 = null;
		tituloMensagem05 = null;
		tituloMensagem06 = null;
		tituloMensagem07 = null;
		tituloMensagem08 = null;
		tituloMensagem09 = null;
		tituloNossoNumero = null;
		tituloNumeroDocumento = null;
		tituloValor = null;
		tituloLocalPagamento = null;
		tituloValorAbatimento = null;
		tituloValorDescontoTaxa = null;
		tituloValorMulta = null;
		tituloValorMultaTaxa = null;
		tituloDataJuros = null;
		tituloCodigoMulta = null;
		tituloDataMulta = null;
		tituloAceite = null;
		tituloPrazoBaixa = null;
		tituloSacadorAvalista = null;
		tituloInscricaoSacadorAvalista = null;
		tituloCodBaixaDevolucao = null;
		tituloCodProtesto = null;
		tituloCodigoJuros = null;
		tituloDocEspecie = null;
		tituloCodDesconto = null;
		tituloCodEmissaoBloqueto = null;
		tituloOutrasDeducoes = null;
		tituloOutrosAcrescimos = null;
		tituloUsoBanco = null;
		tituloPagamentoMinimo = null;
		tituloForcarFatorVencimento = null;
		tituloInformacoesAdicionais = null;
		tituloInstrucoes = null;
		tituloParcela = null;
		tituloVariacaoCarteira = null;
		tituloCategoria = null;
		tituloModalidade = null;
		tituloCodCliente = null;
		tituloCodCedenteBarra = null;
		tituloIos = null;
		tituloCip = null;
		situacao = null;
		protocoloImpressao = null;
	}
	
	public void addBoletoLog(BoletoLog boletoLog){
		this.boletoLog.add(boletoLog);
	}

	public void addBoletoLog(List<BoletoLog> boletoLogs) {
		if (boletoLogs != null) {
			boletoLogs.forEach(this::addBoletoLog);
		}
	}
	
}
