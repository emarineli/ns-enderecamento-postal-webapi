package br.com.ns.webapi.servico.testes;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.ns.webapi.enderecamento.core.EnderecamentoPostalService;
import br.com.ns.webapi.enderecamento.core.impl.EnderecamentoPostalServiceImpl;
import br.com.ns.webapi.enderecamento.exception.RecursoNaoEncontradoException;
import br.com.ns.webapi.enderecamento.modelo.Endereco;

/**
 * Classe de testes da camada de serviço da classe EnderecamentoPostalService.
 * 
 * @author Erico Marineli (emarineli@gmail.com)
 * @version 1.0, 10/06/2015
 *
 */
public class EnderecamentoPostalServiceTest {

	private EnderecamentoPostalService enderecamentoPostalService;

	private static Map<String, Endereco> enderecos = new HashMap<String, Endereco>();

	/**
	 * Inicializa o serviço.
	 */
	@Before
	public void setUp() {

		/* Para estes testes não será necessário carregar contexto do Spring */
		enderecamentoPostalService = new EnderecamentoPostalServiceImpl();

		/* Endereços de teste. */
		enderecos.put("06250080", new Endereco("Rua Fransico Regina", "Osasco",
				"Jardim Elvira", "São Paulo", "06250080"));

		enderecos.put("14920000", new Endereco("Nova Europa", "São Paulo",
				"14920000"));

	}

	/**
	 * Testa a passagem de um cep incompleto.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testeCepInvalidoIncompleto() {
		enderecamentoPostalService.obterEnderecoPeloCodigoEnderecamento("1230",
				true);
	}

	/**
	 * Testa a passagem de um cep vazio.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testeCepInvalidoVazio() {
		enderecamentoPostalService.obterEnderecoPeloCodigoEnderecamento("",
				true);
	}

	/**
	 * Testa a passagem de um cep vazio.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testeCepInvalidoNulo() {
		enderecamentoPostalService.obterEnderecoPeloCodigoEnderecamento(null,
				true);
	}

	/**
	 * Testa a passagem de um cep válido. Para esta localidade não é possível
	 * obter um endereço completo.
	 */
	@Test
	public void testeCepValioIncompleto() {

		String cepTeste = "14920000";

		Assert.assertEquals(enderecos.get(cepTeste), enderecamentoPostalService
				.obterEnderecoPeloCodigoEnderecamento(cepTeste, true));
	}

	/**
	 * Testa a passagem de um cep válido.
	 */
	@Test
	public void testeCepValioCompleto() {

		String cepTeste = "06250080";

		Assert.assertEquals(enderecos.get(cepTeste), enderecamentoPostalService
				.obterEnderecoPeloCodigoEnderecamento(cepTeste, true));
	}

	/**
	 * Testa a passagem de um cep válido. Para este contexto é parametrizado que
	 * a busca é exata, ou seja, o cep não será modificado para que seja refeita
	 * uma nova busca.
	 */
	@Test(expected = RecursoNaoEncontradoException.class)
	public void testeCepValioSubstituicaoZerosExactMatch() {

		enderecamentoPostalService.obterEnderecoPeloCodigoEnderecamento(
				"14920001", true);
	}

	/**
	 * Testa a passagem de um cep válido. Para este contexto é parametrizado que
	 * a busca não é exata, ou seja, o cep terá seus valores substituídos por
	 * zeros da direita para esquerda até que seja encontrado um CEP válido.
	 */
	@Test
	public void testeCepValioSubstituicaoZerosSemExactMatch_Indice1() {

		String cepTeste = "14920001";

		Assert.assertTrue(enderecos.get("14920000").equals(
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento(cepTeste, false)));
	}

	/**
	 * Testa a passagem de um cep válido. Para este contexto é parametrizado que
	 * a busca não é exata, ou seja, o cep terá seus valores substituídos por
	 * zeros da direita para esquerda até que seja encontrado um CEP válido.
	 */
	@Test
	public void testeCepValioSubstituicaoZerosSemExactMatch_Indice2() {

		String cepTeste = "14920011";

		Assert.assertEquals(enderecos.get("14920000"),
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento(cepTeste, false));
	}

	/**
	 * Testa a passagem de um cep válido. Para este contexto é parametrizado que
	 * a busca não é exata, ou seja, o cep terá seus valores substituídos por
	 * zeros da direita para esquerda até que seja encontrado um CEP válido.
	 */
	@Test
	public void testeCepValioSubstituicaoZerosSemExactMatch_Indice3() {

		String cepTeste = "14920111";

		Assert.assertEquals(enderecos.get("14920000"),
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento(cepTeste, false));
	}

	/**
	 * Testa a passagem de um cep válido. Para este contexto é parametrizado que
	 * a busca não é exata, ou seja, o cep terá seus valores substituídos por
	 * zeros da direita para esquerda até que seja encontrado um CEP válido.
	 * Porém, as tentativas se esgotam e nenhum cep é encontrado
	 */
	@Test(expected = RecursoNaoEncontradoException.class)
	public void testeCepValioSubstituicaoZerosSemExactMatchNaoEncontrado() {

		enderecamentoPostalService.obterEnderecoPeloCodigoEnderecamento(
				"14900011", true);
	}

}
