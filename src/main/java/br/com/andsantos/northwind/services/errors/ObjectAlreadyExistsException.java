package br.com.andsantos.northwind.services.errors;

public class ObjectAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = -823762961269187612L;

	public ObjectAlreadyExistsException(String mensagem) {
		super(mensagem);
	}
}
