package com.avinfo.boleto.domain;

/**
 * Situação dos boletos ao interagir com a API
 * da tecnosped
 * 
 * @author tom
 * @see http://54.91.253.106/boleto/Situacao/
 *
 */
public enum BoletoSituacao {
	
	
	/**
	 * Esta situação indica que o boleto chegou até nossa API e ainda é 
	 * necessário aguardar a finalização do processamento. Com esta 
	 * Situação ainda não é possível imprimir, enviar e-mail ou gerar a remessa.
	 */
	SALVO, 
	
	
	/**
	 * Esta situação indica que o boleto passou pelas primeiras validações 
	 * e está pronto para seguir o fluxo de emissão. Com esta Situação 
	 * já é possível imprimir, enviar e-mail ou gerar a remessa.
	 */
	EMITIDO,
	
	
	/**
	 * Diferente de SALVO esta situação indica que ocorreu algum problema
	 * ao validar o boleto em nosso servidor. Sendo necessário consultar 
	 * o IDIntegracao para verificar a causa da falha, e em seguida 
	 * realizar o descarte, para emitir novamente o boleto com a correção.
	 */
	FALHA,
	
	
	/**
	 * Esta situação irá ocorrer quando for feito o upload do retorno e nele
	 * o banco rejeitar o boleto. Será possível verificar a causa da rejeição
	 * consultando o boleto através do IDItengracao e em seguida realizar
	 * o descarte, para emitir novamente o boleto com a correção.	
	 */
	REJEITADO,
	
	
	/**
	 * Esta situação irá ocorrer quando for feito o upload do retorno, e indica
	 * que o processo solicitado de baixa foi autorizado pelo banco.
	 */
	BAIXADO,
	
	
	/**
	 * Esta situação irá ocorrer quando for feito o upload do retorno, e indica 
	 * que o processo solicitado de registro de boleto foi autorizado pelo banco.
	 */
	REGISTRADO,
	
	
	/**
	 * Esta situação irá ocorrer quando for feito o upload do retorno, e indica 
	 * que o sacado realizou o pagamento do boleto.
	 */
	LIQUIDADO,
	
	/**
	 * Logo Após ser enviado o lote para impressão do boleto
	 */
	PROCESSANDO

}
