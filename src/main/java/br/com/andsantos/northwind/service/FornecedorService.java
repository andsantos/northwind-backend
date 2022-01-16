package br.com.andsantos.northwind.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.andsantos.core.service.CRUDService;
import br.com.andsantos.northwind.service.dto.FornecedorDTO;

public interface FornecedorService extends CRUDService<FornecedorDTO> {

    Page<FornecedorDTO> listar(String nomeFornecedor, Pageable pageable);
}
