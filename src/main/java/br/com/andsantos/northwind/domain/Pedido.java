package br.com.andsantos.northwind.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Pedido implements Serializable {
    private static final long serialVersionUID = -8130705109674493631L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private LocalDate dataPedido;

    @Column(name = "required_date")
    private LocalDate dataSolicitacao;

    @Column(name = "shipped_date")
    private LocalDate dataEnvio;

    @Column(name = "freight", precision = 21, scale = 2)
    private BigDecimal valorFrete;

    @Column(name = "ship_name")
    private String nomeNavio;

    @Column(name = "ship_address")
    private String endereco;

    @Column(name = "ship_city")
    private String cidade;

    @Column(name = "ship_region")
    private String shipRegion;

    @Column(name = "ship_postal_code")
    private String numeroCEP;

    @Column(name = "ship_country")
    private String siglaPais;

    @OneToMany(mappedBy = "pedido")
    private Set<PedidoDetalhe> pedidoDetalhe = new HashSet<>();

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Empregado empregado;

    @ManyToOne
    private Transportadora transportador;
}
