package br.com.ns.webapi.enderecamento.exception.handler;

import br.com.ns.webapi.enderecamento.exception.RecursoNaoEncontradoException;

/**
 * Exceção que indica que um CEP não pode ser encontrado. Nao checada pois o
 * tratamento nao e obrigatorio.
 * 
 * @author Erico Marineli
 * @version 1.0, 09/06/2015
 *
 */
public class EnderecoNaoEncontradoException extends
		RecursoNaoEncontradoException {

	/**
	 * Default serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor padrão da exceção.
	 * 
	 * @param message
	 *            mensagem de erro.
	 */
	public EnderecoNaoEncontradoException(String message) {
		super(message);
	}

}
