package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FornecedorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    private Long id;

    private String nomeFornecedor;

    private String nomeContato;

    private String titulo;

    private String endereco;

    private String cidade;

    private String regiao;

    private String numeroCEP;

    private String siglaPais;

    private String telefone;

    private String fax;

    private String homePage;
}
