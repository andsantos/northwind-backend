package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.andsantos.northwind.domain.Categoria;
import br.com.andsantos.northwind.domain.Fornecedor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nomeProduto;

	private String quantidadePorUnidade;

	private BigDecimal precoUnitario;

	private Long unidadesEmEstoque;

	private Boolean descontinuado;

	private Fornecedor fornecedor;

	private Categoria categoria;
}