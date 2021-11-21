package br.com.andsantos.northwind.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.andsantos.northwind.domain.Fornecedor;
import br.com.andsantos.northwind.repository.FornecedorRepository;
import br.com.andsantos.northwind.service.FornecedorService;
import br.com.andsantos.northwind.service.dto.FornecedorDTO;
import br.com.andsantos.northwind.service.mapper.FornecedorMapper;
import br.com.andsantos.northwind.services.errors.NotFoundException;
import br.com.andsantos.northwind.services.errors.ObjectAlreadyExistsException;

@Service
@Transactional
public class FornecedorServiceImpl implements FornecedorService {
    private final Logger log = LoggerFactory.getLogger(FornecedorServiceImpl.class);

    private final FornecedorRepository repository;

    private final FornecedorMapper mapper;

    public FornecedorServiceImpl(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.repository = fornecedorRepository;
        this.mapper = fornecedorMapper;
    }

	@Override
	public FornecedorDTO salvar(FornecedorDTO dto) {
        log.debug("Gravando Fornecedor {} ", dto.getNomeFornecedor());

		if (repository.existsByNomeFornecedor(dto.getNomeFornecedor())) {
			throw new ObjectAlreadyExistsException("Fornecedor já cadastrada.");
		}

		Fornecedor obj = mapper.toEntity(dto);
        return mapper.toDto(repository.save(obj));
	}

	@Override
	public FornecedorDTO atualizar(FornecedorDTO dto) {
        return repository
        		.findById(dto.getId())
                .map(
                    existingCategory -> {
                        mapper.partialUpdate(existingCategory, dto);
                        return existingCategory;
                    }
                )
                .map(repository::save)
                .map(mapper::toDto)
        		.orElseThrow(() -> new NotFoundException("Fornecedor não encontrada."));
	}

	@Override
	public void excluir(Long id) {
        log.debug("Excluindo Fornecedor com id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("Fornecedor não encontrado.");
        }
	}

	@Override
	public FornecedorDTO obter(Long id) {
        log.debug("Recuperando a Fornecedor com id {}", id);
        return repository.findById(id)
        		.map(mapper::toDto)
        		.orElseThrow(() -> new NotFoundException("Fornecedor não encontrada."));
	}

	@Override
	public Page<FornecedorDTO> listar(Pageable pageable) {
        log.debug("Recuperando todas as Fornecedores");
        return repository.findAll(pageable).map(mapper::toDto);
	}

	@Override
	public Page<FornecedorDTO> listar(String nomeEmpresa, Pageable pageable) {
		if (nomeEmpresa == null) {
			return listar(pageable);
		} else {
	        log.debug("Recuperando todas as Fornecedores contendo {}", nomeEmpresa);
	        return repository.findAllByNomeFornecedorContaining(nomeEmpresa, pageable).map(mapper::toDto);
		}
	}
}
