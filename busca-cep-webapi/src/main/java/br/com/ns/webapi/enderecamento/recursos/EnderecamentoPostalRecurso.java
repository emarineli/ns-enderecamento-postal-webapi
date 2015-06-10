package br.com.ns.webapi.enderecamento.recursos;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.ns.webapi.enderecamento.core.EnderecamentoPostalService;
import br.com.ns.webapi.enderecamento.modelo.Endereco;

/**
 * Representa o recurso de endereçamento postal e suas operações expostas.
 * 
 * @author Erico Marineli (emarineli@gmail.com)
 * @version 1.0, 09/06/2015
 *
 */
@Controller
@RequestMapping("/enderecamento")
public class EnderecamentoPostalRecurso {

	@Autowired
	private EnderecamentoPostalService enderecamentoPostalService;

	/*
	 * Expressao regular para validaçao de um cep.
	 */
	private static final String CEP_REGEX = "\\d{8}";

	/**
	 * Operação responsável por localizar os dados de um endereço baseado no
	 * CEP.
	 * 
	 * @param CEP
	 *            código de endereçamento postal da consulta.
	 * @return retorna o payload que representa o endereço caso encontrado. Caso
	 *         contrário uma mensagem é enviada ao cliente, ambos respeitando os
	 *         códigos http pertinentes.
	 */
	@RequestMapping(produces = { APPLICATION_JSON_VALUE }, value = "/cep/{cep}", method = GET)
	public final @ResponseBody Endereco obterEnderecoPeloCodigoEnderecamento(
			@PathVariable String cep) {

		if (!cep.matches(CEP_REGEX)) {
			throw new IllegalArgumentException("CEP Inválido");
		}

		return enderecamentoPostalService
				.obterEnderecoPeloCodigoEnderecamento(cep);
	}

}
