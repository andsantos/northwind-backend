package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpregadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    private String sobrenome;

    private String nomeEmpregado;

    private String titulo;

    private String tituloDeCortesia;

    private LocalDate dataNascimento;

    private LocalDate dataAdmissao;

    private String endereco;

    private String cidade;

    private String regiao;

    private String numeroCEP;

    private String siglaPais;

    private String telefone;

    private String ramal;

    private String foto;

    private String comentario;

    private Long supervisor;
}
