package br.com.andsantos.northwind.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.andsantos.core.service.CRUDService;
import br.com.andsantos.northwind.service.dto.EmpregadoDTO;

public interface EmpregadoService extends CRUDService<EmpregadoDTO> {

    Page<EmpregadoDTO> listar(String nomeEmpregado, Pageable pageable);
}
