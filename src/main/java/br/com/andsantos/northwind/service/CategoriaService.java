package br.com.andsantos.northwind.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.andsantos.core.service.CRUDService;
import br.com.andsantos.northwind.service.dto.CategoriaDTO;

public interface CategoriaService extends CRUDService<CategoriaDTO> {

    Page<CategoriaDTO> listar(String nomeCategoria, Pageable pageable);
}
