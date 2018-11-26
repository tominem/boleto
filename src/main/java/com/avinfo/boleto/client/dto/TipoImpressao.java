package com.avinfo.boleto.client.dto;

public enum TipoImpressao {

	IMPRESSAO_NORMAL(0, "Impressão normal"),
	IMPRESSAO_CARNE_DUPLO_PAISAGEM(1, "Impressão carnê duplo (paisagem)"),
	IMPRESSAO_CARNE_TRIPLO_RETRATO(2, "Impressão carnê triplo (retrato)"),
	IMPRESSAO_DUPLA_RETRATO(3, "Impressão dupla (retrato)"),
	IMPRESSAO_NORMAL_COM_MARCA_DAGUA(4, "Impressão normal (Com marca D'água)"),
	IMPRESSAO_PERSONALIZADA(99, "Impressão personalizada");
	
	private int id;
	private String label;
	
	private TipoImpressao(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}
	
	private String getLabel() {
		return label;
	}
	
}
