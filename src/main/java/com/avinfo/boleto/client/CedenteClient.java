package com.avinfo.boleto.client;

import com.avinfo.boleto.domain.Cedente;

public interface CedenteClient {

	Cedente cadastrar(Cedente entity);

	Cedente editar(Cedente cedente, Long idIntegracao);

}