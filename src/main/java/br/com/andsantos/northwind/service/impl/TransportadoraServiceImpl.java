package br.com.andsantos.northwind.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.andsantos.northwind.domain.Transportadora;
import br.com.andsantos.northwind.repository.TransportadoraRepository;
import br.com.andsantos.northwind.service.TransportadoraService;
import br.com.andsantos.northwind.service.dto.TransportadoraDTO;
import br.com.andsantos.northwind.service.mapper.TransportadoraMapper;
import br.com.andsantos.northwind.services.errors.NotFoundException;
import br.com.andsantos.northwind.services.errors.ObjectAlreadyExistsException;

@Service
@Transactional
public class TransportadoraServiceImpl implements TransportadoraService {
    private final Logger log = LoggerFactory.getLogger(TransportadoraServiceImpl.class);

    private final TransportadoraRepository repository;

    private final TransportadoraMapper mapper;

    public TransportadoraServiceImpl(TransportadoraRepository transportadorRepository,
            TransportadoraMapper transportadorMapper) {
        this.repository = transportadorRepository;
        this.mapper = transportadorMapper;
    }

    @Override
    public TransportadoraDTO salvar(TransportadoraDTO dto) {
        log.debug("Gravando Transportadora {} ", dto.getNomeTransportadora());

        if (repository.existsByNomeTransportadora(dto.getNomeTransportadora())) {
            throw new ObjectAlreadyExistsException("Transportadora já cadastrada.");
        }
        Transportadora category = mapper.toEntity(dto);
        category = repository.save(category);
        return mapper.toDto(category);
    }

    @Override
    public TransportadoraDTO atualizar(TransportadoraDTO dto) {
        return repository.findById(dto.getId())
                .map(existingCategory -> {
                    mapper.partialUpdate(existingCategory, dto);
                    return existingCategory;
                })
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Transportador não encontrada."));
    }

    @Override
    public void excluir(Long id) {
        log.debug("Excluindo Transportador com id {}", id);
        repository.deleteById(id);
    }

    @Override
    public TransportadoraDTO obter(Long id) {
        log.debug("Recuperando a Transportador com id {}", id);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Transportadora não encontrada."));
    }

    @Override
    public Page<TransportadoraDTO> listar(Pageable pageable) {
        log.debug("Recuperando todas as Transportadors");
        return repository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    public Page<TransportadoraDTO> listar(String nomeTransportadora, Pageable pageable) {
        if (nomeTransportadora == null) {
            return listar(pageable);
        } else {
            log.debug("Recuperando todas as Transportadors contendo {}", nomeTransportadora);
            return repository.findAllByNomeTransportadoraContaining(nomeTransportadora, pageable)
                    .map(mapper::toDto);
        }
    }
}
