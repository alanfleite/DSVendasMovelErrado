package br.com.datasol.vo;

public class RecVO {
	private Integer cod;
	private String fantasia;
	private String razao;
	private String tot;
	private String cnpj;
	private String condpg;
	private String dataems;
	private String vendedor;
	
	public RecVO() {
	
	}

	public RecVO(Integer cod, String fantasia, String razao, String tot,
			String cnpj, String condpg, String dataems, String vendedor) {
		this.cod = cod;
		this.fantasia = fantasia;
		this.razao = razao;
		this.tot = tot;
		this.cnpj = cnpj;
		this.condpg = condpg;
		this.dataems = dataems;
		this.vendedor = vendedor;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getTot() {
		return tot;
	}

	public void setTot(String tot) {
		this.tot = tot;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCondpg() {
		return condpg;
	}

	public void setCondpg(String condpg) {
		this.condpg = condpg;
	}

	public String getDataems() {
		return dataems;
	}

	public void setDataems(String dataems) {
		this.dataems = dataems;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
}
