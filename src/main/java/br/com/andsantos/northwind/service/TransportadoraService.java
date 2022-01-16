package br.com.andsantos.northwind.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.andsantos.core.service.CRUDService;
import br.com.andsantos.northwind.service.dto.TransportadoraDTO;

public interface TransportadoraService extends CRUDService<TransportadoraDTO> {

    Page<TransportadoraDTO> listar(String nomeTransportadora, Pageable pageable);
}
