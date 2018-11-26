package com.avinfo.boleto.service;

import com.avinfo.boleto.client.dto.GeraRemessaRetDTO;
import com.avinfo.boleto.domain.Boleto;

public interface RemessaService {

	GeraRemessaRetDTO geraRemessa(Iterable<Boleto> boletos);

}
