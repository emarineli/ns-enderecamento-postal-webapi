package br.com.ns.webapi.enderecamento.exception.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Responsável por tratar as mensagens de erro de forma global.
 * 
 * O Spring possui um modelo de resposta já elaborado para tratamento das
 * exceções lançadas durante o processamento da requisição / resposta. O intuito
 * desta classe é padronizar uma forma distinta deste tratamento.
 *  
 * @author Erico Marineli (emarineli@gmail.com)
 * @version 1.0, 09/06/2015
 *
 */
@ControllerAdvice
public class GlobalResourceExceptionHandler {

	/**
	 * Argumentos inválidos enviados ao recurso serão tratados como uma
	 * requisição inválida.
	 * 
	 * @throws IOException
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	private MensagemErro handleIllegalArgumentException(
			IllegalArgumentException e) throws IOException {

		return new MensagemErro(e.getMessage());
	}

	/**
	 * Argumentos inválidos enviados ao recurso serão tratados como uma
	 * requisição inválida.
	 * 
	 * @throws IOException
	 */
	@ExceptionHandler({ EnderecoNaoEncontradoException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	private MensagemErro handleRecursoNaoEncontrado(Exception e)
			throws IOException {

		return new MensagemErro(e.getMessage());
	}

	/**
	 * Representa a abstração de uma mensagem de erro que será enviada ao
	 * cliente da API.
	 * 
	 * @author emarineli
	 *
	 */
	private final class MensagemErro {
		private String mensagem;

		public MensagemErro(String mensagem) {
			this.mensagem = mensagem;
		}

		/**
		 * Obtém a mensagem de erro encapsulada.
		 * 
		 * @return mensagem de erro.
		 */
		@SuppressWarnings("unused")
		public String getMensagem() {
			return mensagem;
		}
	}
}
