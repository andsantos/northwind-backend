package br.com.andsantos.northwind.service.mapper;

import org.mapstruct.Mapper;

import br.com.andsantos.core.service.mapper.EntityMapper;
import br.com.andsantos.northwind.domain.Transportadora;
import br.com.andsantos.northwind.service.dto.TransportadoraDTO;

@Mapper(componentModel = "spring", uses = {})
public interface TransportadoraMapper extends EntityMapper<TransportadoraDTO, Transportadora> {

}
