package br.com.andsantos.northwind.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Produto implements Serializable {
    private static final long serialVersionUID = -2900619894681475409L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String nomeProduto;

    @NotNull
    @Column(name = "quantity_per_unit")
    private String quantidadePorUnidade;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "units_in_stock")
    private Long unidadesEmEstoque;

    @NotNull
    @Column(name = "discontinued")
    private Boolean descontinuado;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Fornecedor fornecedor;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Categoria categoria;
}
