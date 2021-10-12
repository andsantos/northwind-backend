package br.com.andsantos.northwind.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.andsantos.northwind.domain.Empregado;
import br.com.andsantos.northwind.repository.EmpregadoRepository;
import br.com.andsantos.northwind.service.EmpregadoService;
import br.com.andsantos.northwind.service.dto.EmpregadoDTO;
import br.com.andsantos.northwind.service.mapper.EmpregadoMapper;
import br.com.andsantos.northwind.services.errors.NotFoundException;
import br.com.andsantos.northwind.services.errors.ObjectAlreadyExistsException;

@Service
@Transactional
public class EmpregadoServiceImpl implements EmpregadoService {
    private final Logger log = LoggerFactory.getLogger(EmpregadoServiceImpl.class);

    private final EmpregadoRepository repository;

    private final EmpregadoMapper mapper;

    public EmpregadoServiceImpl(EmpregadoRepository empregadoRepository, EmpregadoMapper empregadoMapper) {
        this.repository = empregadoRepository;
        this.mapper = empregadoMapper;
    }

	@Override
	public EmpregadoDTO salvar(EmpregadoDTO dto) {
        log.debug("Gravando Empregado {} ", dto.getNomeEmpregado());

		if (repository.existsByNomeEmpregado(dto.getNomeEmpregado())) {
			throw new ObjectAlreadyExistsException("Empregado já cadastrada.");
		}
		Empregado category = mapper.toEntity(dto);
        category = repository.save(category);
        return mapper.toDto(category);
	}

	@Override
	public EmpregadoDTO atualizar(EmpregadoDTO dto) {
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
        		.orElseThrow(() -> new NotFoundException("Empregado não encontrada."));
	}

	@Override
	public void excluir(Long id) {
        log.debug("Excluindo Empregado com id {}", id);
        repository.deleteById(id);
	}

	@Override
	public EmpregadoDTO obter(Long id) {
        log.debug("Recuperando a Empregado com id {}", id);
        return repository.findById(id)
        		.map(mapper::toDto)
        		.orElseThrow(() -> new NotFoundException("Empregado não encontrada."));
	}

	@Override
	public Page<EmpregadoDTO> listar(Pageable pageable) {
        log.debug("Recuperando todas as Empregados");
        return repository.findAll(pageable).map(mapper::toDto);
	}

	@Override
	public Page<EmpregadoDTO> listar(String nomeEmpregado, Pageable pageable) {
		if (nomeEmpregado == null) {
			return listar(pageable);
		} else {
	        log.debug("Recuperando todas as Empregados contendo {}", nomeEmpregado);
	        return repository.findAllByNomeEmpregadoContaining(nomeEmpregado, pageable).map(mapper::toDto);
		}
	}
}
