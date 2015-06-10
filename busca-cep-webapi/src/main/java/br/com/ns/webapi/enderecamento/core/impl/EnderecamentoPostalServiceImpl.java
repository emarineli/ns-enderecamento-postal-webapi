package br.com.ns.webapi.enderecamento.core.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.ns.webapi.enderecamento.core.EnderecamentoPostalService;
import br.com.ns.webapi.enderecamento.exception.RecursoNaoEncontradoException;
import br.com.ns.webapi.enderecamento.modelo.Endereco;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * Classe responsável pela execução do processamento de negócio.
 * 
 * @author Erico Marineli
 * @version 1.0, 09/06/2015
 * @since 1.0
 *
 */
@Service
public class EnderecamentoPostalServiceImpl implements
		EnderecamentoPostalService {

	/* Para o exercício, simula um repositório dos endereços */
	private static Map<String, Endereco> enderecos = new HashMap<String, Endereco>();

	private static final String CEP_FINAL = "00000000";

	/**
	 * Construtor padrão que irá popular o mapa com os endereços.
	 */
	public EnderecamentoPostalServiceImpl() {
		enderecos.put("06250080", new Endereco("Rua Fransico Regina", "Osasco",
				"Jardim Elvira", "São Paulo", "06250080"));

		enderecos.put("14920000", new Endereco("Nova Europa", "São Paulo",
				"14920000"));

	}

	/*
	 * Expressao regular para validaçao de um cep.
	 */
	private static final String CEP_REGEX = "\\d{8}";
	private static final int INDICE_INICIAL = 0;

	/**
	 * A princípio não existe fallback para este comando.
	 * 
	 * O timeout foi acrescido em 4s apenas para este exercício pois o deploy no
	 * Heroku tem um webdyno simples.
	 * 
	 * @see {@link #obterEnderecoPeloCodigoEnderecamento(String)}
	 * 
	 *      Deve-se atentar as configuraçoes.
	 * 
	 * @see https 
	 *      ://github.com/Netflix/Hystrix/wiki/Configuration#CommandCircuitBreaker
	 */
	@HystrixCommand(ignoreExceptions = { IllegalArgumentException.class,
			RecursoNaoEncontradoException.class }, commandProperties = { @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public final Endereco obterEnderecoPeloCodigoEnderecamento(String cep,
			boolean cepExactMatch) {

		if (cep == null || !cep.trim().matches(CEP_REGEX)) {
			throw new IllegalArgumentException("CEP Invalido");
		}

		return obterEnderecoPeloCodigoEnderecamento(cep, cepExactMatch,
				INDICE_INICIAL);
	}

	/**
	 * Obtém um endereço pelo seu código de endereçamento. Caso não seja
	 * possível obtê-lo e o parâmetro cepExactMatch não for verdadeiro, será
	 * feita um ajuste no código e outra tentativa será realizada até que se
	 * esgotem as possibilidades.
	 * 
	 * @param cep
	 *            código de endereçamento.
	 * @param cepExactMatch
	 *            se a busca deve ser exata ou não. Caso verdadeiro e um
	 *            endereço não for encontrado, uma exceção será lançada
	 *            indicando o caso.
	 * @param indexBuscaCep
	 *            índice de controle.
	 * 
	 * @return um endereço por seu códig ode endereçamento.
	 * 
	 * @throws RecursoNaoEncontradoException
	 *             caso o endereço não for encontrado.
	 */
	private Endereco obterEnderecoPeloCodigoEnderecamento(String cep,
			boolean cepExactMatch, int indexBuscaCep) {
		if (enderecos.containsKey(cep)) {
			return enderecos.get(cep);
		} else {

			/*
			 * Será realiza tentativas para obter o cep substituindo os zeros da
			 * direita para esquerda até encontrá-lo.
			 */
			if (!cepExactMatch) {
				String novoCep = atribuirZeroCep(cep, ++indexBuscaCep);

				if (novoCep != null) {
					return obterEnderecoPeloCodigoEnderecamento(novoCep,
							cepExactMatch);
				}
			}

			throw new RecursoNaoEncontradoException(
					"CEP nao disponivel no repositorio");
		}
	}

	/**
	 * Realiza um tratamento no CEP para substituir os zeros da direita para a
	 * esquerda.
	 * 
	 * @param cep
	 *            código do endereçamento.
	 * @param index
	 *            índice de substituição, da direção direita para esquerda.
	 * @return cep modificado.
	 */
	private String atribuirZeroCep(String cep, int index) {

		char[] strCep = cep.toCharArray();

		/* Verifica se ainda e possivel fazer alguma shift no cep. */
		if (CEP_FINAL.equals(cep)) {
			return null;
		}

		/*
		 * Caso no indice a ser substituido ja exista o valor 0, deve-se seguir
		 * para o proximo.
		 */
		if ('0' == strCep[cep.length() - index]) {
			return atribuirZeroCep(cep, ++index);
		}

		strCep[cep.length() - index] = '0';
		return String.valueOf(strCep);
	}

}
