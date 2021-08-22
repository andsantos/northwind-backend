package br.com.andsantos.northwind.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.andsantos.core.service.CRUDService;
import br.com.andsantos.northwind.service.dto.ProdutoDTO;

public interface ProdutoService extends CRUDService<ProdutoDTO> {

	public Page<ProdutoDTO> listar(String nomeProduto, Pageable pageable);
}
