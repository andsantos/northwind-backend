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
import br.com.andsantos.northwind.service.EmpregadoService;
import br.com.andsantos.northwind.service.dto.EmpregadoDTO;

@RestController
@RequestMapping("/api")
public class EmpregadoController {
    private final Logger log = LoggerFactory.getLogger(EmpregadoController.class);

    private EmpregadoService service;

    public EmpregadoController(EmpregadoService empregadoService) {
        this.service = empregadoService;
    }

    @GetMapping("/empregados")
    public ResponseEntity<List<EmpregadoDTO>> listar(Pageable pageable, @RequestParam(required = false) String nome) {
        log.debug("Recuperando todos os empregados");
        Page<EmpregadoDTO> page = service.listar(nome, pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/empregados/{id}")
    public ResponseEntity<EmpregadoDTO> obter(@PathVariable Long id) {
        log.debug("Recuperando o empregado com id {}", id);
        EmpregadoDTO dto = service.obter(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/empregados")
    public ResponseEntity<EmpregadoDTO> salvar(@Valid @RequestBody EmpregadoDTO dto) throws URISyntaxException {
        log.debug("Gravando empregado {} ", dto);
        EmpregadoDTO result = service.salvar(dto);
        return ResponseEntity.created(new URI("/Empregados/" + result.getId())).body(result);
    }

    @PutMapping("/empregados/{id}")
    public ResponseEntity<EmpregadoDTO> atualizar(@PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody EmpregadoDTO dto) throws URISyntaxException {
        log.debug("Atualizando empregado {} ", dto);

        if (dto.getId() == null || dto.getId() == 0) {
            throw new BadRequestException("Requisição inválida.");
        }

        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestException("Requisição inválida.");
        }

        EmpregadoDTO result = service.atualizar(dto);
        return ResponseEntity.created(new URI("/empregados/" + result.getId())).body(result);
    }

    @DeleteMapping("/empregados/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.debug("Recuperando o Empregado com id {}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
