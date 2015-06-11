package br.com.ns.webapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.ns.webapi.recursos.testes.EnderecamentoPostalRecursoTest;
import br.com.ns.webapi.servico.testes.EnderecamentoPostalServiceTest;

/**
 * Suite de teste.
 * 
 * @author Erico Marineli (emarineli@gmail.com)
 * @version 1.0, 09/06/2015
 * @since 1.0
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ EnderecamentoPostalRecursoTest.class,
		EnderecamentoPostalServiceTest.class })
public class TestSuite {
	// vazio
}