package br.com.andsantos.northwind.service.mapper;

import org.mapstruct.Mapper;

import br.com.andsantos.core.service.mapper.EntityMapper;
import br.com.andsantos.northwind.domain.Empregado;
import br.com.andsantos.northwind.service.dto.EmpregadoDTO;

@Mapper(componentModel = "spring", uses = {})
public interface EmpregadoMapper extends EntityMapper<EmpregadoDTO, Empregado> {

}
