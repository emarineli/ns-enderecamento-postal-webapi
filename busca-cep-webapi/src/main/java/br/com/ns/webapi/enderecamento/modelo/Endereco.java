package br.com.ns.webapi.enderecamento.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Domínio de informação que representa um Endereço.
 * 
 * @author Erico Marineli (emarineli@gmail.com)
 * @version 1.0, 09/06/2015
 *
 */
@JsonInclude(Include.NON_NULL)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result
				+ ((localidade == null) ? 0 : localidade.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (localidade == null) {
			if (other.localidade != null)
				return false;
		} else if (!localidade.equals(other.localidade))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

	
}
