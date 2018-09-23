package com.avinfo.boleto.client;

import com.avinfo.boleto.domain.Conta;

public interface ContaClient {

	Conta cadastrar(Conta conta, String cnpjCedente);

	Conta editar(Conta conta, String cnpjCedente, Long idIntegracao);
	
}