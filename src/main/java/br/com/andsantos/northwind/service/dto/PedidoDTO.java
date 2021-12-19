package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.andsantos.northwind.domain.Cliente;
import br.com.andsantos.northwind.domain.Empregado;
import br.com.andsantos.northwind.domain.PedidoDetalhe;
import br.com.andsantos.northwind.domain.Transportadora;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate dataPedido;

    private LocalDate dataSolicitacao;

    private LocalDate dataEnvio;

    private BigDecimal valorFrete;

    private String nomeNavio;

    private String endereco;

    private String cidade;

    private String shipRegion;

    private String numeroCEP;

    private String siglaPais;

    private Set<PedidoDetalhe> pedidoDetalhe = new HashSet<>();

    private Cliente cliente;

    private Empregado empregado;

    private Transportadora transportador;
}
