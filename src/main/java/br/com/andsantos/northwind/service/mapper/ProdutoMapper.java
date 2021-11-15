package br.com.andsantos.northwind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.andsantos.core.service.mapper.EntityMapper;
import br.com.andsantos.northwind.domain.Produto;
import br.com.andsantos.northwind.service.dto.ProdutoDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {

    @Mappings({
        @Mapping(target="categoriaId", source = "categoria.id"),
        @Mapping(target="nomeCategoria", source = "categoria.nomeCategoria"),
        @Mapping(target="fornecedorId", source = "fornecedor.id"),
        @Mapping(target="nomeFornecedor", source = "fornecedor.nomeFornecedor")
    })
    ProdutoDTO toDto(Produto entity);

    @Mappings({
        @Mapping(target="categoria.id", source = "categoriaId"),
        @Mapping(target="categoria.nomeCategoria", source = "nomeCategoria"),
        @Mapping(target="fornecedor.id", source = "fornecedorId"),
        @Mapping(target="fornecedor.nomeFornecedor", source = "nomeFornecedor")
    })
    Produto toEntity(ProdutoDTO dto);
}
