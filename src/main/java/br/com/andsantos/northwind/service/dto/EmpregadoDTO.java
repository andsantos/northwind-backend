package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.andsantos.northwind.service.serialize.LocalDateDeserializer;
import br.com.andsantos.northwind.service.serialize.LocalDateSerializer;
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

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
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
