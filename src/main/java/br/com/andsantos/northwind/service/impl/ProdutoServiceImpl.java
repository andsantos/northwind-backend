package br.com.andsantos.northwind.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.andsantos.northwind.domain.Categoria;
import br.com.andsantos.northwind.domain.Fornecedor;
import br.com.andsantos.northwind.domain.Produto;
import br.com.andsantos.northwind.exception.NotFoundException;
import br.com.andsantos.northwind.exception.ObjectAlreadyExistsException;
import br.com.andsantos.northwind.repository.CategoriaRepository;
import br.com.andsantos.northwind.repository.FornecedorRepository;
import br.com.andsantos.northwind.repository.ProdutoRepository;
import br.com.andsantos.northwind.service.ProdutoService;
import br.com.andsantos.northwind.service.dto.ProdutoDTO;
import br.com.andsantos.northwind.service.mapper.ProdutoMapper;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {
    private static final String PRODUTO_NOT_FOUND = "Produto não encontrado.";

    private final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    private final ProdutoRepository repository;

    private final CategoriaRepository categoriaRepository;

    private final FornecedorRepository fornecedorRepository;

    private final ProdutoMapper mapper;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, CategoriaRepository categoryRepository,
            FornecedorRepository supplierRepository, ProdutoMapper produtoMapper) {
        this.repository = produtoRepository;
        this.mapper = produtoMapper;
        this.categoriaRepository = categoryRepository;
        this.fornecedorRepository = supplierRepository;
    }

    @Override
    public ProdutoDTO salvar(ProdutoDTO dto) {
        log.debug("Gravando Produto {} ", dto.getNomeProduto());

        if (repository.existsByNomeProduto(dto.getNomeProduto())) {
            throw new ObjectAlreadyExistsException("Produto já cadastrado.");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada."));

        Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado."));

        Produto obj = mapper.toEntity(dto);
        obj.setCategoria(categoria);
        obj.setFornecedor(fornecedor);

        return mapper.toDto(repository.save(obj));
    }

    @Override
    public ProdutoDTO atualizar(ProdutoDTO dto) {
        return repository.findById(dto.getId())
                .map(existingCategory -> {
                    mapper.partialUpdate(existingCategory, dto);
                    return existingCategory;
                    })
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NOT_FOUND));
    }

    @Override
    public void excluir(Long id) {
        log.debug("Excluindo Produto com id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException(PRODUTO_NOT_FOUND);
        }
    }

    @Override
    public ProdutoDTO obter(Long id) {
        log.debug("Recuperando a Produto com id {}", id);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NOT_FOUND));
    }

    @Override
    public Page<ProdutoDTO> listar(Pageable pageable) {
        log.debug("Recuperando todas as Produtos");
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public Page<ProdutoDTO> listar(String nomeProduto, Pageable pageable) {
        if (nomeProduto == null) {
            return listar(pageable);
        } else {
            log.debug("Recuperando todas as Produtos contendo {}", nomeProduto);
            return repository.findAllByNomeProdutoContaining(nomeProduto, pageable)
                    .map(mapper::toDto);
        }
    }

}
