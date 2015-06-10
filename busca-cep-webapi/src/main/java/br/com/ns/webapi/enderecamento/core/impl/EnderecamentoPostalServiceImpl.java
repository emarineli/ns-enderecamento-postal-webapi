package br.com.ns.webapi.enderecamento.core.impl;

import org.springframework.stereotype.Service;

import br.com.ns.webapi.enderecamento.core.EnderecamentoPostalService;
import br.com.ns.webapi.enderecamento.modelo.Endereco;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * Classe responsável pela execução do processamento de negócio.
 * 
 * @author Erico Marineli
 * @version 1.0, 09/06/2015
 *
 */
@Service
public class EnderecamentoPostalServiceImpl implements
		EnderecamentoPostalService {

	/**
	 * @see {@link #obterEnderecoPeloCodigoEnderecamento(String)}
	 */
	@HystrixCommand
	public final Endereco obterEnderecoPeloCodigoEnderecamento(String cep) {

		return new Endereco("Rua 9 de Julho", "Nova Europa", "Centro", "SP", cep);
	}

}
