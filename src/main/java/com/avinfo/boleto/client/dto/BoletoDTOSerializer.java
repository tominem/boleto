package com.avinfo.boleto.client.dto;

import static com.avinfo.boleto.util.ParserUtil.dateFormat;

import java.io.IOException;

import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.Conta;
import com.avinfo.boleto.domain.Convenio;
import com.avinfo.boleto.domain.Sacado;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BoletoDTOSerializer extends JsonSerializer<Boleto>{

	private static final String DATE_FORMAT = "dd/MM/yyyy";

	@Override
	public void serialize(Boleto boleto, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		Sacado sacado = boleto.getSacado();
		Convenio convenio = boleto.getConvenio();
		Conta conta = convenio.getConta();
		
		gen.writeStartObject();
	    gen.writeStringField("SacadoCPFCNPJ", sacado.getCpjcnpj()); 
	    gen.writeStringField("SacadoEmail", sacado.getEmail());
	    gen.writeStringField("SacadoEnderecoNumero", sacado.getEnderecoNumero());
	    gen.writeStringField("SacadoEnderecoBairro", sacado.getEnderecoBairro());
	    gen.writeStringField("SacadoEnderecoCEP", sacado.getEnderecoCEP());
	    gen.writeStringField("SacadoEnderecoCidade", sacado.getEnderecoCidade());
	    gen.writeStringField("SacadoEnderecoComplemento", sacado.getEnderecoComplemento());
	    gen.writeStringField("SacadoEnderecoLogradouro", sacado.getEnderecoLogradouro());
	    gen.writeStringField("SacadoEnderecoPais", sacado.getEnderecoPais());
	    gen.writeStringField("SacadoEnderecoUF", sacado.getEnderecoUF());
	    gen.writeStringField("SacadoNome", sacado.getNome());
	    gen.writeStringField("SacadoTelefone", sacado.getTelefone());
	    gen.writeStringField("SacadoCelular", sacado.getCelular());
	    gen.writeStringField("CedenteContaNumero", conta.getConta()); 
	    gen.writeStringField("CedenteContaNumeroDV", conta.getContaDV());
	    gen.writeStringField("CedenteConvenioNumero", convenio.getNumeroConvenio());
	    gen.writeStringField("CedenteContaCodigoBanco", conta.getCodigoBanco());
	    gen.writeStringField("TituloNumeroDocumento", String.valueOf(boleto.getId()));
	    gen.writeStringField("TituloDataDesconto", dateFormat(DATE_FORMAT, boleto.getTituloDataDesconto()));
	    gen.writeStringField("TituloValorDesconto", boleto.getTituloValorDesconto());
	    gen.writeStringField("TituloDataEmissao", dateFormat(DATE_FORMAT, boleto.getTituloDataEmissao()));
	    gen.writeStringField("TituloDataVencimento", dateFormat(DATE_FORMAT, boleto.getTituloDataVencimento()));
	    gen.writeStringField("TituloValorJuros", boleto.getTituloValorJuros());
	    gen.writeStringField("TituloPrazoProtesto", boleto.getTituloPrazoProtesto());
	    gen.writeStringField("TituloMensagem01", boleto.getTituloMensagem01());
	    gen.writeStringField("TituloMensagem02", boleto.getTituloMensagem02());
	    gen.writeStringField("TituloMensagem03", boleto.getTituloMensagem03());
	    gen.writeStringField("TituloMensagem04", boleto.getTituloMensagem04());
	    gen.writeStringField("TituloMensagem05", boleto.getTituloMensagem05());
	    gen.writeStringField("TituloMensagem06", boleto.getTituloMensagem06());
	    gen.writeStringField("TituloMensagem07", boleto.getTituloMensagem07());
	    gen.writeStringField("TituloMensagem08", boleto.getTituloMensagem08());
	    gen.writeStringField("TituloMensagem09", boleto.getTituloMensagem09());
	    gen.writeStringField("TituloNossoNumero", boleto.getTituloNossoNumero());
	    gen.writeStringField("TituloValor", boleto.getTituloValor());
	    gen.writeStringField("TituloValorAbatimento", boleto.getTituloValorAbatimento());
	    gen.writeStringField("TituloValorDescontoTaxa", boleto.getTituloValorDescontoTaxa());
	    gen.writeStringField("TituloValorMulta", boleto.getTituloValorMulta());
	    gen.writeStringField("TituloValorMultaTaxa", boleto.getTituloValorMultaTaxa());
	    gen.writeStringField("TituloDataJuros", dateFormat(DATE_FORMAT, boleto.getTituloDataJuros()));
	    gen.writeStringField("TituloCodigoMulta", boleto.getTituloCodigoMulta());
	    gen.writeStringField("TituloDataMulta", dateFormat(DATE_FORMAT, boleto.getTituloDataMulta()));
	    gen.writeStringField("TituloAceite", boleto.getTituloAceite());
	    gen.writeStringField("TituloPrazoBaixa", boleto.getTituloPrazoBaixa());
	    gen.writeStringField("TituloSacadorAvalista", boleto.getTituloSacadorAvalista());
	    gen.writeStringField("TituloInscricaoSacadorAvalista", boleto.getTituloInscricaoSacadorAvalista());
	    gen.writeStringField("TituloCodBaixaDevolucao", boleto.getTituloCodBaixaDevolucao());
	    gen.writeStringField("TituloCodProtesto", boleto.getTituloCodProtesto());
	    gen.writeStringField("TituloCodigoJuros", boleto.getTituloCodigoJuros());
	    gen.writeStringField("TituloDocEspecie", boleto.getTituloCodDesconto());
	    gen.writeStringField("TituloCodDesconto", boleto.getTituloCodDesconto());
	    gen.writeStringField("TituloCodEmissaoBloqueto", boleto.getTituloCodEmissaoBloqueto());
	    gen.writeStringField("TituloOutrasDeducoes", boleto.getTituloOutrasDeducoes());
	    gen.writeStringField("TituloOutrosAcrescimos", boleto.getTituloOutrosAcrescimos());
	    gen.writeStringField("TituloUsoBanco", boleto.getTituloUsoBanco());
	    gen.writeStringField("TituloPagamentoMinimo", boleto.getTituloPagamentoMinimo());
	    gen.writeStringField("TituloLocalPagamento", boleto.getTituloLocalPagamento());
	    gen.writeStringField("TituloForcarFatorVencimento", boleto.getTituloForcarFatorVencimento());
	    gen.writeStringField("TituloInformacoesAdicionais", boleto.getTituloInformacoesAdicionais());
	    gen.writeStringField("TituloInstrucoes", boleto.getTituloInstrucoes());
	    gen.writeStringField("TituloParcela", boleto.getTituloParcela());
	    gen.writeStringField("TituloVariacaoCarteira", boleto.getTituloVariacaoCarteira());
	    gen.writeStringField("TituloCategoria", boleto.getTituloCategoria());
	    gen.writeStringField("TituloModalidade", boleto.getTituloModalidade());
	    gen.writeStringField("TituloCodCliente", boleto.getTituloCodCliente());
	    gen.writeStringField("TituloCodCedenteBarra", boleto.getTituloCodCedenteBarra());
	    gen.writeStringField("TituloIos", boleto.getTituloIos());
	    gen.writeStringField("TituloCip", boleto.getTituloCip());
	    gen.writeEndObject();
	    
	}

}
