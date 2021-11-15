package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.andsantos.northwind.service.serialize.BigDecimalDeserializer;
import br.com.andsantos.northwind.service.serialize.BigDecimalSerializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nomeProduto;

	private String quantidadePorUnidade;

    @JsonSerialize(using = BigDecimalSerializer.class)
    @JsonDeserialize(using = BigDecimalDeserializer.class)
	private BigDecimal precoUnitario;

	private Long unidadesEmEstoque;

	private Boolean descontinuado;

	private Long fornecedorId;

	private String nomeFornecedor;

	private Long categoriaId;

	private String nomeCategoria;
}