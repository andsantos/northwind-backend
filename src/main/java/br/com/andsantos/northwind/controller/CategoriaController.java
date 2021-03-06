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
import br.com.andsantos.northwind.service.CategoriaService;
import br.com.andsantos.northwind.service.dto.CategoriaDTO;
import br.com.andsantos.northwind.util.Pagina;
import br.com.andsantos.northwind.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class CategoriaController {
    private final Logger log = LoggerFactory.getLogger(CategoriaController.class);

    private CategoriaService service;

    public CategoriaController(CategoriaService categoriaService) {
        this.service = categoriaService;
    }

    @GetMapping("/categorias")
    public ResponseEntity<Pagina<CategoriaDTO>> listar(Pageable pageable,
            @RequestParam(required = false) String nome) {
        log.debug("Recuperando todas as categorias");
        Page<CategoriaDTO> page = service.listar(nome, pageable);
        return ResponseEntity.ok().body(PaginationUtil.paginar(page));
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> obter(@PathVariable Long id) {
        log.debug("Recuperando a categoria com id {}", id);
        CategoriaDTO dto = service.obter(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDTO> salvar(@Valid @RequestBody CategoriaDTO dto)
            throws URISyntaxException {
        log.debug("Gravando categoria {} ", dto);
        CategoriaDTO result = service.salvar(dto);
        return ResponseEntity
                .created(new URI("/categorias/" + result.getId()))
                .body(result);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody CategoriaDTO dto) throws URISyntaxException {
        log.debug("Atualizando categoria {} ", dto);

        if (dto.getId() == null) {
            throw new BadRequestException("Requisi????o inv??lida.");
        }

        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestException("Requisi????o inv??lida.");
        }

        CategoriaDTO result = service.atualizar(dto);
        return ResponseEntity
                .created(new URI("/categorias/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.debug("Recuperando a categoria com id {}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
