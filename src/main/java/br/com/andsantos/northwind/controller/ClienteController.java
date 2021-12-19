package br.com.andsantos.northwind.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.andsantos.northwind.exception.BadRequestException;
import br.com.andsantos.northwind.service.ClienteService;
import br.com.andsantos.northwind.service.dto.ClienteDTO;

@RestController
@RequestMapping("/api")
public class ClienteController {
    private final Logger log = LoggerFactory.getLogger(ClienteController.class);

    private ClienteService service;

    public ClienteController(ClienteService clienteService) {
        this.service = clienteService;
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> listar(Pageable pageable,
            @RequestParam(required = false) String nome) {
        log.debug("Recuperando todos os clientes");
        Page<ClienteDTO> page = service.listar(nome, pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> obter(@PathVariable Long id) {
        log.debug("Recuperando o cliente com id {}", id);
        ClienteDTO dto = service.obter(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteDTO dto)
            throws URISyntaxException {
        log.debug("Gravando cliente {} ", dto);
        ClienteDTO result = service.salvar(dto);
        return ResponseEntity
                .created(new URI("/clientes/" + result.getId())).body(result);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> atualizar(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ClienteDTO dto) throws URISyntaxException {
        log.debug("Atualizando Cliente {} ", dto);

        if (dto.getId() == null || dto.getId() == 0) {
            throw new BadRequestException("Requisição inválida.");
        }

        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestException("Requisição inválida.");
        }

        ClienteDTO result = service.atualizar(dto);
        return ResponseEntity
                .created(new URI("/clientes/" + result.getId())).body(result);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.debug("Excluindo cliente com id {}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
