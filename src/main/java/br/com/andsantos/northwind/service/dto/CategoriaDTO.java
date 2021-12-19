package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nomeCategoria;

    private String descricao;

    private String imagem;
}
