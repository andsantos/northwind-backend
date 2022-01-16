package br.com.andsantos.northwind.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.andsantos.northwind.domain.Categoria;
import br.com.andsantos.northwind.exception.NotFoundException;
import br.com.andsantos.northwind.exception.ObjectAlreadyExistsException;
import br.com.andsantos.northwind.repository.CategoriaRepository;
import br.com.andsantos.northwind.service.CategoriaService;
import br.com.andsantos.northwind.service.dto.CategoriaDTO;
import br.com.andsantos.northwind.service.mapper.CategoriaMapper;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {
    private static final String CATEGORIA_NOT_FOUND = "Categoria não encontrada.";

    private final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    private final CategoriaRepository repository;

    private final CategoriaMapper mapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository,
            CategoriaMapper categoriaMapper) {
        this.repository = categoriaRepository;
        this.mapper = categoriaMapper;
    }

    @Override
    public CategoriaDTO salvar(CategoriaDTO dto) {
        log.debug("Gravando categoria {} ", dto.getNomeCategoria());

        if (repository.existsByNomeCategoria(dto.getNomeCategoria())) {
            throw new ObjectAlreadyExistsException("Categoria já cadastrada.");
        }
        Categoria obj = mapper.toEntity(dto);
        return mapper.toDto(repository.save(obj));
    }

    @Override
    public CategoriaDTO atualizar(CategoriaDTO dto) {
        return repository
                .findById(dto.getId())
                .map(existingCategory -> {
                    mapper.partialUpdate(existingCategory, dto);
                    return existingCategory;})
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException(CATEGORIA_NOT_FOUND));
    }

    @Override
    public void excluir(Long id) {
        log.debug("Excluindo categoria com id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException(CATEGORIA_NOT_FOUND);
        }
    }

    @Override
    public CategoriaDTO obter(Long id) {
        log.debug("Recuperando a categoria com id {}", id);
        return repository
                .findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException(CATEGORIA_NOT_FOUND));
    }

    @Override
    public Page<CategoriaDTO> listar(Pageable pageable) {
        log.debug("Recuperando todas as categorias");
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public Page<CategoriaDTO> listar(String nomeCategoria, Pageable pageable) {
        if (nomeCategoria == null) {
            return listar(pageable);
        } else {
            log.debug("Recuperando todas as categorias contendo {}", nomeCategoria);
            return repository.findAllByNomeCategoriaContaining(nomeCategoria, pageable)
                    .map(mapper::toDto);
        }
    }
}
