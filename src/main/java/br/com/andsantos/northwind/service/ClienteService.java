package br.com.andsantos.northwind.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.andsantos.core.service.CRUDService;
import br.com.andsantos.northwind.service.dto.ClienteDTO;

public interface ClienteService extends CRUDService<ClienteDTO> {

    Page<ClienteDTO> listar(String companyName, Pageable pageable);
}
