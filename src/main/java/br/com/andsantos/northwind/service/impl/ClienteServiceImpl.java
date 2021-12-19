package br.com.andsantos.northwind.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.andsantos.northwind.domain.Cliente;
import br.com.andsantos.northwind.exception.NotFoundException;
import br.com.andsantos.northwind.exception.ObjectAlreadyExistsException;
import br.com.andsantos.northwind.repository.ClienteRepository;
import br.com.andsantos.northwind.service.ClienteService;
import br.com.andsantos.northwind.service.dto.ClienteDTO;
import br.com.andsantos.northwind.service.mapper.ClienteMapper;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
    private static final String CLIENTE_NOT_FOUND = "Cliente não encontrado.";

	private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	private final ClienteRepository repository;

	private final ClienteMapper mapper;

	public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
		this.repository = clienteRepository;
		this.mapper = clienteMapper;
	}

	@Override
	public ClienteDTO salvar(ClienteDTO dto) {
		log.debug("Gravando Cliente {} ", dto.getNomeEmpresa());

		if (repository.existsByNomeEmpresa(dto.getNomeEmpresa())) {
			throw new ObjectAlreadyExistsException("Cliente já cadastrada.");
		}

		Cliente obj = mapper.toEntity(dto);
		return mapper.toDto(repository.save(obj));
	}

	@Override
	public ClienteDTO atualizar(ClienteDTO dto) {
		return repository.findById(dto.getId())
				.map(existingCategory -> {
					mapper.partialUpdate(existingCategory, dto);
					return existingCategory;
				})
				.map(repository::save)
				.map(mapper::toDto)
				.orElseThrow(() -> new NotFoundException(CLIENTE_NOT_FOUND));
	}

	@Override
	public void excluir(Long id) {
		log.debug("Excluindo Cliente com id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException(CLIENTE_NOT_FOUND);
        }
	}

	@Override
	public ClienteDTO obter(Long id) {
		log.debug("Recuperando a Cliente com id {}", id);
		return repository.findById(id)
				.map(mapper::toDto)
				.orElseThrow(() -> new NotFoundException(CLIENTE_NOT_FOUND));
	}

	@Override
	public Page<ClienteDTO> listar(Pageable pageable) {
		log.debug("Recuperando todas as Clientes");
		return repository.findAll(pageable).map(mapper::toDto);
	}

	@Override
	public Page<ClienteDTO> listar(String nomeEmpresa, Pageable pageable) {
		if (nomeEmpresa == null) {
			return listar(pageable);
		} else {
			log.debug("Recuperando todas as Clientes contendo {}", nomeEmpresa);
			return repository.findAllByNomeEmpresaContaining(nomeEmpresa, pageable).map(mapper::toDto);
		}
	}
}
