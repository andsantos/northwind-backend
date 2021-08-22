package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    private String nomeEmpresa;

    private String nomeContato;

    private String tituloContato;

    private String endereco;

    private String cidade;

    private String regiao;

    private String numeroCEP;

    private String pais;

    private String telefone;

    private String fax;
}
