package br.com.andsantos.northwind.services.errors;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 6481067862610165319L;

	public BadRequestException(String mensagem) {
		super(mensagem);
	}
}
