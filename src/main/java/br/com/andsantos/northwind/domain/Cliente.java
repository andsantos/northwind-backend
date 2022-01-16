package br.com.andsantos.northwind.domain;

import java.io.Serializable;

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
@Table(name = "customers")
public class Cliente implements Serializable {

    private static final long serialVersionUID = -6649719408509790983L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String nomeEmpresa;

    @Column(name = "contact_name")
    private String nomeContato;

    @Column(name = "contact_title")
    private String tituloContato;

    @Column(name = "address")
    private String endereco;

    @Column(name = "city")
    private String cidade;

    @Column(name = "region")
    private String regiao;

    @Column(name = "postal_code")
    private String numeroCEP;

    @Column(name = "country")
    private String pais;

    @Column(name = "phone")
    private String telefone;

    @Column(name = "fax")
    private String fax;
}
