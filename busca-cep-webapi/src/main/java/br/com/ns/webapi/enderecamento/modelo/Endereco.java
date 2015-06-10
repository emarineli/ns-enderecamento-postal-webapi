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

	/**
	 * Construtor de um endereço para CEPs que dispôem apenas da informação de
	 * localidade e uf.
	 * 
	 * @param localidade
	 * @param uf
	 */
	public Endereco(String localidade, String uf) {
		this.localidade = localidade;
		this.uf = uf;
	}

	/**
	 * Construtor de um Endereço quando um CEP possui todos os atributos deste
	 * domínio.
	 * 
	 * @param logradouro
	 * @param localidade
	 * @param bairro
	 * @param uf
	 */
	public Endereco(String logradouro, String localidade, String bairro,
			String uf) {
		this.localidade = localidade;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.uf = uf;
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
}
