package br.com.andsantos.northwind.controller.errors;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus httpStatus, String mensagem) {
        super();
        this.status = httpStatus;
        this.message = mensagem;
    }

    public ApiError(HttpStatus httpStatus, String mensagem, List<String> erros) {
        super();
        this.status = httpStatus;
        this.message = mensagem;
        this.errors = erros;
    }

    public ApiError(HttpStatus httpStatus, String mensagem, String error) {
        super();
        this.status = httpStatus;
        this.message = mensagem;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus httpStatus) {
        this.status = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mensagem) {
        this.message = mensagem;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> erros) {
        this.errors = erros;
    }
}
