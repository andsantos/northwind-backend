package br.com.andsantos.northwind.service.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransportadoraDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    private Long id;

    private String nomeTransportadora;

    private String telefone;
}
