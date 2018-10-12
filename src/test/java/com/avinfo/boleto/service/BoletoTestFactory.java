package com.avinfo.boleto.service;

import java.time.LocalDate;

import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.Conta;
import com.avinfo.boleto.domain.Convenio;
import com.avinfo.boleto.domain.Sacado;

public class BoletoTestFactory {

	public Boleto buildBoleto() {
		
		Sacado sacado = Sacado.builder()
				.cpjcnpj("82441266713")
				.email("cliente.sacado@email.com")
				.enderecoNumero("328")
				.enderecoBairro("JARDIM PRIMAVERA")
				.enderecoCEP("25214415")
				.enderecoCidade("DUQUE DE CAXIAS")
				.enderecoComplemento("Loja B")
				.enderecoLogradouro("Avenida Visconde de Itaúna")
				.enderecoPais("BRASIL")
				.enderecoUF("RJ")
				.nome("Cliente de Teste")
				.telefone("")
				.celular("")
				.build();
		
		Conta conta = Conta.builder()
				.conta("24703")
				.contaDV("3")
				.codigoBanco("341")
				.build();
		
		Convenio convenio = Convenio.builder()
				.numeroConvenio("3788247033")
				.conta(conta)
				.build();
		
		Boleto boleto = Boleto.builder()
				.sacado(sacado)
				.convenio(convenio)
				.tituloNumeroDocumento("000001")
				.tituloDataDesconto(LocalDate.now())
				.tituloValorDesconto("50)00")
				.tituloDataEmissao(LocalDate.now())
				.tituloDataVencimento(LocalDate.now())
				.tituloValorJuros("5,96")
				.tituloPrazoProtesto("0")
				.tituloMensagem01("Não receber após o vencimento.")
				.tituloMensagem02("Boleto 1 de 1 referente a NF 1 de 11/04/2017 com chave 41170308187168000160553290000070551416403145")
				.tituloMensagem03("Se o boleto for pago até 14/04/2017) terá um desconto de 50)00")
				.tituloMensagem04("Mensagem 04")
				.tituloMensagem05("Mensagem 05")
				.tituloMensagem06("Mensagem 06")
				.tituloMensagem07("Mensagem 07")
				.tituloMensagem08("Mensagem 08")
				.tituloMensagem09("Mensagem 09")
				.tituloNossoNumero("1")
				.tituloValor("180,00")
				.tituloValorAbatimento("10,00")
				.tituloValorDescontoTaxa("10,00")
				.tituloValorMulta("0")
				.tituloValorMultaTaxa("")
				.tituloDataJuros(LocalDate.now())
				.tituloCodigoMulta("1")
				.tituloDataMulta(LocalDate.now())
				.tituloAceite("N")
				.tituloPrazoBaixa("0")
				.tituloSacadorAvalista("Avalista do Pagador")
				.tituloInscricaoSacadorAvalista("58946546379")
				.tituloCodBaixaDevolucao("2")
				.tituloCodProtesto("3")
				.tituloCodigoJuros("1")
				.tituloDocEspecie("01")
				.tituloCodDesconto("1")
				.tituloCodEmissaoBloqueto("1")
				.tituloOutrasDeducoes("0")
				.tituloOutrosAcrescimos("0")
				.tituloUsoBanco("Uso do Banco.")
				.tituloPagamentoMinimo("0")
				.tituloLocalPagamento("QUALQUER BANCO ATÉ O VENCIMENTO")
				.tituloForcarFatorVencimento("false")
				.tituloInformacoesAdicionais("Vendedor: 5 - Vendedor de teste")
				.tituloInstrucoes("Texto de Responsabilidade do Beneficiário")
				.tituloParcela("1/1")
				.tituloVariacaoCarteira("")
				.tituloCategoria("1")
				.tituloModalidade("1")
				.tituloCodCliente("")
				.tituloCodCedenteBarra("")
				.tituloIos("")
				.tituloCip("")
				.build();
		
		
		return boleto;
	}
	
	

}
