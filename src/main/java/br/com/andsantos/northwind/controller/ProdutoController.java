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
import br.com.andsantos.northwind.service.ProdutoService;
import br.com.andsantos.northwind.service.dto.ProdutoDTO;
import br.com.andsantos.northwind.util.Pagina;
import br.com.andsantos.northwind.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class ProdutoController {
    private final Logger log = LoggerFactory.getLogger(ProdutoController.class);

    private ProdutoService service;

    public ProdutoController(ProdutoService produtoService) {
        this.service = produtoService;
    }

    @GetMapping("/produtos")
    public ResponseEntity<Pagina<ProdutoDTO>> listar(Pageable pageable,
            @RequestParam(required = false) String nome) {
        log.debug("Recuperando todos os produtos");
        Page<ProdutoDTO> page = service.listar(nome, pageable);
        return ResponseEntity.ok().body(PaginationUtil.paginar(page));
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<ProdutoDTO> obter(@PathVariable Long id) {
        log.debug("Recuperando o produto com id {}", id);
        ProdutoDTO dto = service.obter(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/produtos")
    public ResponseEntity<ProdutoDTO> salvar(
            @Valid @RequestBody ProdutoDTO dto) throws URISyntaxException {
        log.debug("Gravando Produto {} ", dto);
        ProdutoDTO result = service.salvar(dto);
        return ResponseEntity
                .created(new URI("/produtos/" + result.getId())).body(result);
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ProdutoDTO dto) throws URISyntaxException {
        log.debug("Atualizando Produto {} ", dto);

        if (dto.getId() == null) {
            throw new BadRequestException("Requisi????o inv??lida.");
        }

        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestException("Requisi????o inv??lida.");
        }

        ProdutoDTO result = service.atualizar(dto);
        return ResponseEntity
                .created(new URI("/produtos/" + result.getId())).body(result);
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.debug("Recuperando produto com id {}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
