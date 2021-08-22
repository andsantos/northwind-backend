package br.com.andsantos.northwind.service.mapper;

import org.mapstruct.Mapper;

import br.com.andsantos.core.service.mapper.EntityMapper;
import br.com.andsantos.northwind.domain.Categoria;
import br.com.andsantos.northwind.service.dto.CategoriaDTO;


@Mapper(componentModel = "spring", uses = {})
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {

}
