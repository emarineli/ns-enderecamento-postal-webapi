package br.com.ns.webapi.recursos.testes;


import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import br.com.ns.webapi.enderecamento.core.EnderecamentoPostalService;
import br.com.ns.webapi.enderecamento.exception.RecursoNaoEncontradoException;
import br.com.ns.webapi.enderecamento.exception.handler.GlobalResourceExceptionHandler;
import br.com.ns.webapi.enderecamento.modelo.Endereco;
import br.com.ns.webapi.enderecamento.recursos.EnderecamentoPostalRecurso;

/**
 * Testes específicos para o recuros EnderecamentoPostal
 * 
 * @author Erico Marineli
 * @version 1.0, 10/06/2015
 * @since 1.0
 *
 */
@EnableWebMvc
public class EnderecamentoPostalRecursoTest {

	private static final String RESOURCE_URI = "/enderecamento/cep/{cep}";

	@Autowired
	private WebApplicationContext ctx;

	@Mock
	private EnderecamentoPostalService enderecamentoPostalService;

	@InjectMocks
	private EnderecamentoPostalRecurso enderecamentoPostalRecurso;

	private MockMvc mockMvc;

	/**
	 * Inicializa o contexto do mock.
	 */
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		/*
		 * Para testes standalone deve ser criado o exception resolver
		 * apropriadamente.
		 */
		mockMvc = MockMvcBuilders.standaloneSetup(enderecamentoPostalRecurso)
				.setHandlerExceptionResolvers(createExceptionResolver())
				.build();

		/* Inicializa alguns CEPs válidos */

		/*
		 * Este endereço é imcompleto, representa uma cidade que não tem a
		 * separação nos 3 últimos dígitos do CEP.
		 */
		when(
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento("14920000", false))
				.thenReturn(
						new Endereco("Nova Europa", "Sao Paulo", "14920000"));

		/* Endereço completo. */
		when(
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento("06250080", false))
				.thenReturn(
						new Endereco("Rua Fransico Regina", "Osasco",
								"Jardim Elvira", "Sao Paulo", "06250080"));

		/* Casos de exceção */

		when(
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento("149", false))
				.thenThrow(new IllegalArgumentException("CEP invalido"));

		when(
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento("14920A00", false))
				.thenThrow(new IllegalArgumentException("CEP invalido"));

		when(
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento("14900000", false))
				.thenThrow(
						new RecursoNaoEncontradoException(
								"CEP nao disponivel no repositorio"));

		/* Erro irrecuperável e unchecked */
		when(
				enderecamentoPostalService
						.obterEnderecoPeloCodigoEnderecamento("15920000", false))
				.thenThrow(
						new NullPointerException(
								"Erro qualquer de runtime"));
		
	}

	/**
	 * Testa um CEP válido. Este CEP trará dados de endereço imcompletos pois
	 * são apenas de Localidade.
	 * 
	 * @throws Exception
	 *             caso algum erro ocorra.
	 */
	@Test
	public void testCepValidoDadosIncompletos() throws Exception {

		mockMvc.perform(get(RESOURCE_URI, "14920000").accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.localidade", is("Nova Europa")))
				.andExpect(jsonPath("$.uf", is("Sao Paulo")))
				.andExpect(jsonPath("$.cep", is("14920000")))
				.andExpect(jsonPath("$.logradouro").doesNotExist())
				.andExpect(jsonPath("$.bairro").doesNotExist());

	}

	/**
	 * Testa um CEP válido. Este CEP trará dados de endereço imcompletos pois
	 * são apenas de Localidade.
	 * 
	 * @throws Exception
	 *             caso algum erro ocorra.
	 */
	@Test
	public void testCepValidoDadosCompletos() throws Exception {

		mockMvc.perform(get(RESOURCE_URI, "06250080").accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.localidade", is("Osasco")))
				.andExpect(jsonPath("$.logradouro", is("Rua Fransico Regina")))
				.andExpect(jsonPath("$.bairro", is("Jardim Elvira")))
				.andExpect(jsonPath("$.uf", is("Sao Paulo")))
				.andExpect(jsonPath("$.cep", is("06250080")));
	}

	/**
	 * Testa um CEP inválido, com numeração imcompleta.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCepInvalidoIncompleto() throws Exception {

		mockMvc.perform(get(RESOURCE_URI, "149").accept(APPLICATION_JSON))
				.andExpect(status().is(BAD_REQUEST.value()))
				.andExpect(jsonPath("$.mensagem", is("CEP invalido")));
	}

	/**
	 * Testa um CEP inválido com caracteres alfanuméricos.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCepInvalidoAlfanumerico() throws Exception {

		mockMvc.perform(get(RESOURCE_URI, "14920A00").accept(APPLICATION_JSON))
				.andExpect(status().is(BAD_REQUEST.value()))
				.andExpect(jsonPath("$.mensagem", is("CEP invalido")));
	}

	/**
	 * Testa um Cep não encontrado no repositórios, mesmo que sera realizado a
	 * substituição dos zeros da direita para esquerda.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCepNaoEncontradoRepositorio() throws Exception {

		mockMvc.perform(get(RESOURCE_URI, "14900000").accept(APPLICATION_JSON))
				.andExpect(status().is(NOT_FOUND.value()))
				.andExpect(
						jsonPath("$.mensagem",
								is("CEP nao disponivel no repositorio")));
	}

	/**
	 * Testa um CEP inválido, com numeração imcompleta.
	 *
	 * @throws Exception
	 */
	@Test
	public void testErroGeral() throws Exception {

		mockMvc.perform(get(RESOURCE_URI, "15920000").accept(APPLICATION_JSON))
				.andExpect(status().is(INTERNAL_SERVER_ERROR.value()));
	}
	
	/**
	 * Cria o Exception Resolver baseado na classe
	 * GlobalResourceExceptionHandler deste projeto.
	 * 
	 * @see GlobalResourceExceptionHandler#GlobalResourceExceptionHandler()
	 * 
	 * @return Exception Resolver.
	 */
	private ExceptionHandlerExceptionResolver createExceptionResolver() {
		ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
			@Override
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(
					HandlerMethod handlerMethod, Exception exception) {
				Method method = new ExceptionHandlerMethodResolver(
						GlobalResourceExceptionHandler.class)
						.resolveMethod(exception);
				return new ServletInvocableHandlerMethod(
						new GlobalResourceExceptionHandler(), method);
			}
		};
		exceptionResolver.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		exceptionResolver.afterPropertiesSet();
		return exceptionResolver;
	}
}
