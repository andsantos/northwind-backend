package br.com.andsantos.northwind.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1342927251382599182L;

    public NotFoundException(String mensagem) {
        super(mensagem);
    }
}
