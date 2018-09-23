package com.avinfo.boleto.client.dto;

public enum TecnospedHeaders {
	
	CNPJ_CEDENTE("cnpj-cedente");
	
	private String header;

	TecnospedHeaders(String header){
		this.header = header;
	}
	
	public String getHeader(){
		return header;
	}

}
