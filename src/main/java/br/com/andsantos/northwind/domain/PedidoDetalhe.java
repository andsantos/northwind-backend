package br.com.andsantos.northwind.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_details")
public class PedidoDetalhe implements Serializable {
    private static final long serialVersionUID = 8397380661653769289L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantidade;

    @Column(name = "discount", precision = 21, scale = 2)
    private BigDecimal desconto;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;
}
