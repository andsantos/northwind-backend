package br.com.andsantos.northwind.service.mapper;

import org.mapstruct.Mapper;

import br.com.andsantos.core.service.mapper.EntityMapper;
import br.com.andsantos.northwind.domain.Produto;
import br.com.andsantos.northwind.service.dto.ProdutoDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {

}
