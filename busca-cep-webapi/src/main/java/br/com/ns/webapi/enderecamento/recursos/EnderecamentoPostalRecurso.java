package br.com.ns.webapi.enderecamento.recursos;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.ns.webapi.enderecamento.core.EnderecamentoPostalService;
import br.com.ns.webapi.enderecamento.modelo.Endereco;

/**
 * Representa o recurso de endereçamento postal e suas operações expostas.
 * 
 * O recurso irá agir apenas como fachada que delega sua invocação para a camada
 * de serviço que efetivamente irá tratar a solicitação.
 * 
 * @author Erico Marineli (emarineli@gmail.com)
 * @version 1.0, 09/06/2015
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/enderecamento")
public class EnderecamentoPostalRecurso {

	@Autowired
	private EnderecamentoPostalService enderecamentoPostalService;

	/**
	 * Operação responsável por localizar os dados de um endereço baseado no
	 * CEP.
	 * 
	 * @param CEP
	 *            código de endereçamento postal da consulta.
	 * 
	 * @param cepExactMatch
	 *            indica se na busca o cep precisa ser exatamente o passado.
	 *            Caso seja falso a busca irá tentar obter o primeiro endereço
	 *            substituindo o zero da direita para esquerda.
	 *            O valor padrão é verdadeiro (true).
	 *            
	 * @return retorna o payload que representa o endereço caso encontrado. Caso
	 *         contrário uma mensagem é enviada ao cliente, ambos respeitando os
	 *         códigos http pertinentes.
	 */
	@RequestMapping(produces = { APPLICATION_JSON_VALUE }, value = "/cep/{cep}", method = GET)
	public final @ResponseBody Endereco obterEnderecoPeloCodigoEnderecamento(
			@PathVariable String cep,
			@RequestHeader(value = "cepExactMatch", defaultValue = "false") boolean cepExactMatch) {

		return enderecamentoPostalService
				.obterEnderecoPeloCodigoEnderecamento(cep, cepExactMatch);
	}

}
