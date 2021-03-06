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
import br.com.andsantos.northwind.service.FornecedorService;
import br.com.andsantos.northwind.service.dto.FornecedorDTO;
import br.com.andsantos.northwind.util.Pagina;
import br.com.andsantos.northwind.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class FornecedorController {
    private final Logger log = LoggerFactory.getLogger(FornecedorController.class);

    private FornecedorService service;

    public FornecedorController(FornecedorService fornecedorService) {
        this.service = fornecedorService;
    }

    @GetMapping("/fornecedores")
    public ResponseEntity<Pagina<FornecedorDTO>> listar(Pageable pageable,
            @RequestParam(required = false) String nome) {
        log.debug("Recuperando todos os fornecedores");
        Page<FornecedorDTO> page = service.listar(nome, pageable);
        return ResponseEntity.ok().body(PaginationUtil.paginar(page));
    }

    @GetMapping("/fornecedores/{id}")
    public ResponseEntity<FornecedorDTO> obter(@PathVariable Long id) {
        log.debug("Recuperando o fornecedor com id {}", id);
        FornecedorDTO dto = service.obter(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/fornecedores")
    public ResponseEntity<FornecedorDTO> salvar(@Valid @RequestBody FornecedorDTO dto)
            throws URISyntaxException {
        log.debug("Gravando fornecedor {} ", dto);
        FornecedorDTO result = service.salvar(dto);
        return ResponseEntity
                .created(new URI("/fornecedores/" + result.getId())).body(result);
    }

    @PutMapping("/fornecedores/{id}")
    public ResponseEntity<FornecedorDTO> atualizar(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody FornecedorDTO dto) throws URISyntaxException {
        log.debug("Atualizando fornecedor {} ", dto);

        if (dto.getId() == null) {
            throw new BadRequestException("Requisi????o inv??lida.");
        }

        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestException("Requisi????o inv??lida.");
        }

        FornecedorDTO result = service.atualizar(dto);
        return ResponseEntity
                .created(new URI("/fornecedores/" + result.getId())).body(result);
    }

    @DeleteMapping("/fornecedores/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.debug("Recuperando o fornecedor com id {}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
