package com.avinfo.boleto.client;

import com.avinfo.boleto.domain.Convenio;

public interface ConvenioClient {
	
	Convenio cadastrar(Convenio convenio, String cnpjCedente);
	
	Convenio editar(Convenio convenio, String cnpjCedente, Long idIntegracao);

}
