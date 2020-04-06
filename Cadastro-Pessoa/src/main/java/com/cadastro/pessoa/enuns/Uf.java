package com.cadastro.pessoa.enuns;

public enum Uf {

	AMAZONAS("AM"), ALAGOAS("AL"), ACRE("AC"),
	AMAPA("AP"), BAHIA("BA"), PARA("PA"),
	MATO_GROSSO("MT"), MINAS_GERAIS("MG"),
	MATO_GROSSO_DO_SUL("MS"), GOIAS("GO"),
	MARANHAO("MA"), RIO_GRANDE_DO_SUL("RS"),
	TOCANTINS("TO"), PIAUI("PI"), SAO_PAULO("SP"),
	RONDONIA("RO"), RORAIMA("RR"),
	PARANA("PR"), CEARA("CE"), PERNAMBUCO("PE"),
	SANTA_CATARINA("SC"), PARAIBA("PB"),
	RIO_GRANDE_DO_NORTE("RN"), ESPIRITO_SANTO("ES"),
	RIO_DE_JANEIRO("RJ"), SERGIPE("SE"),
	DISTRITO_FEDERAL("DF");

	private String sigla;

	Uf(final String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
