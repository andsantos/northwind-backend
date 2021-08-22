package br.com.andsantos.northwind.service.mapper;

import org.mapstruct.Mapper;

import br.com.andsantos.core.service.mapper.EntityMapper;
import br.com.andsantos.northwind.domain.Fornecedor;
import br.com.andsantos.northwind.service.dto.FornecedorDTO;

@Mapper(componentModel = "spring", uses = {})
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {

}
