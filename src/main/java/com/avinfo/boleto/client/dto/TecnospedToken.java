package com.avinfo.boleto.client.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class TecnospedToken {

	private final String cnpjSH;
	private final String tokenSH;
	
	TecnospedToken() {
		this(null, null);
	}
	
}
