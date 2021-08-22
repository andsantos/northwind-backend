package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.andsantos.northwind.domain.Pedido;
import br.com.andsantos.northwind.domain.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDetalheDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal precoUnitario;

    private BigDecimal quantidade;

    private BigDecimal desconto;

    private Pedido pedido;

    private Produto produto;
}
