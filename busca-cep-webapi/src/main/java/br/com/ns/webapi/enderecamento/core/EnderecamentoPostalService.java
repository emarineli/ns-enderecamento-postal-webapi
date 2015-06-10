package br.com.ns.webapi.enderecamento.core;

import br.com.ns.webapi.enderecamento.modelo.Endereco;

/**
 * Interface que expõe as operações de negócio relativas ao serviço de
 * endereçamento postal.
 * 
 * @author Erico Marineli
 * @version 1.0, 09/06/2015
 *
 */
public interface EnderecamentoPostalService {

	/**
	 * Realiza a busca dos dados de endereço pelo seu código de endereçamento
	 * postal.
	 * 
	 * @param cep
	 *            código de endereçamento.
	 * @return uma representação do endereço caso encontrado. Caso não uma
	 *         exceção será lançada.
	 * 
	 * @throws EnderecoNaoEncontradoException
	 *             caso o endereço não possa ser encontrado.
	 */
	public Endereco obterEnderecoPeloCodigoEnderecamento(String cep);

}