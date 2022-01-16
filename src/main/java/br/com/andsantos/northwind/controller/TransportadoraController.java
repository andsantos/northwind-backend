package br.com.andsantos.northwind.controller;

import java.net.URI;
import java.net.URISyntaxException;
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
import br.com.andsantos.northwind.service.TransportadoraService;
import br.com.andsantos.northwind.service.dto.TransportadoraDTO;
import br.com.andsantos.northwind.util.Pagina;
import br.com.andsantos.northwind.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class TransportadoraController {
    private final Logger log = LoggerFactory.getLogger(TransportadoraController.class);

    private TransportadoraService service;

    public TransportadoraController(TransportadoraService transportadoraService) {
        this.service = transportadoraService;
    }

    @GetMapping("/transportadoras")
    public ResponseEntity<Pagina<TransportadoraDTO>> listar(Pageable pageable,
            @RequestParam(required = false) String nome) {
        log.debug("Recuperando todas as transportadoras");
        Page<TransportadoraDTO> page = service.listar(nome, pageable);
        return ResponseEntity.ok().body(PaginationUtil.paginar(page));
    }

    @GetMapping("/transportadoras/{id}")
    public ResponseEntity<TransportadoraDTO> obter(@PathVariable Long id) {
        log.debug("Recuperando a transportadora com id {}", id);
        TransportadoraDTO dto = service.obter(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/transportadoras")
    public ResponseEntity<TransportadoraDTO> salvar(
            @Valid @RequestBody TransportadoraDTO dto)
            throws URISyntaxException {
        log.debug("Gravando transportadora {} ", dto);
        TransportadoraDTO result = service.salvar(dto);
        return ResponseEntity
                .created(new URI("/transportadoras/" + result.getId())).body(result);
    }

    @PutMapping("/transportadoras/{id}")
    public ResponseEntity<TransportadoraDTO> atualizar(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody TransportadoraDTO dto) throws URISyntaxException {
        log.debug("Atualizando transportadora {} ", dto);

        if (dto.getId() == null) {
            throw new BadRequestException("Requisição inválida.");
        }

        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestException("Requisição inválida.");
        }

        TransportadoraDTO result = service.atualizar(dto);
        return ResponseEntity
                .created(new URI("/transportadoras/" + result.getId())).body(result);
    }

    @DeleteMapping("/transportadoras/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.debug("Recuperando a transportadora com id {}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
