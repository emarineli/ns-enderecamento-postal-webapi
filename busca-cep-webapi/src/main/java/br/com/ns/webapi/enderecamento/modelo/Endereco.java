package br.com.ns.webapi.enderecamento.modelo;

/**
 * Domínio de informação que representa um Endereço.
 * 
 * @author Erico Marineli (emarineli@gmail.com)
 * @version 1.0, 09/06/2015
 *
 */
public class Endereco {

	private String logradouro;

	private String localidade;

	private String bairro;

	private String uf;
	
	private String cep;

	/**
	 * Construtor de um endereço para CEPs que dispôem apenas da informação de
	 * localidade e uf.
	 * 
	 * @param localidade
	 * @param uf
	 * @param cep
	 */
	public Endereco(String localidade, String uf, String cep) {
		this.localidade = localidade;
		this.uf = uf;
		this.cep = cep;
	}

	/**
	 * Construtor de um Endereço quando um CEP possui todos os atributos deste
	 * domínio.
	 * 
	 * @param logradouro
	 * @param localidade
	 * @param bairro
	 * @param uf
	 * @param cep
	 */
	public Endereco(String logradouro, String localidade, String bairro,
			String uf, String cep) {
		this.localidade = localidade;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.uf = uf;
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public String getBairro() {
		return bairro;
	}

	public String getUf() {
		return uf;
	}
	
	public String getCep() {
		return cep;
	}
}
