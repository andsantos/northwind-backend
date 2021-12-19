package br.com.andsantos.northwind.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Empregado implements Serializable {
    private static final long serialVersionUID = -155753306950620360L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String sobrenome;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String nomeEmpregado;

    @Column(name = "title")
    private String titulo;

    @Column(name = "title_of_courtesy")
    private String tituloDeCortesia;

    @Column(name = "birth_date")
    private LocalDate dataNascimento;

    @Column(name = "hire_date")
    private LocalDate dataAdmissao;

    @Column(name = "address")
    private String endereco;

    @Column(name = "city")
    private String cidade;

    @Column(name = "region")
    private String regiao;

    @Column(name = "postal_code")
    private String numeroCEP;

    @Column(name = "country")
    private String siglaPais;

    @Column(name = "home_phone")
    private String telefone;

    @Column(name = "extension")
    private String ramal;

    @Column(name = "photo")
    private String foto;

    @Column(name = "notes")
    private String comentario;

    @Column(name = "reports_to")
    private Long supervisor;
}
