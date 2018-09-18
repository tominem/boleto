package com.avinfo.boleto.domain;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Transacao {
	
	private final Long id;
	private final Status status;
	private final LocalDateTime dataHora;
	
	public Transacao() {
		this(null, null, null);
	}

}
