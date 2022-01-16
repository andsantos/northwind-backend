package br.com.andsantos.northwind.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.com.andsantos.core.controller.IntegrationTest;
import br.com.andsantos.northwind.domain.Categoria;
import br.com.andsantos.northwind.repository.CategoriaRepository;
import br.com.andsantos.northwind.service.dto.CategoriaDTO;
import br.com.andsantos.northwind.service.mapper.CategoriaMapper;
import br.com.andsantos.northwind.util.TestUtil;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class CategoriaControllerTest {
    private static final String ENTITY_API_URL = "/api/categorias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final String PADRAO_NOME_CATEGORIA = "AAAAAAAAAAAAA";
    private static final String PADRAO_DESCRICAO = "AAAAAAAAAAAAAAAAAA";
    private static final String PADRAO_IMAGEM = "AAAAAAAAAAAA";

    private static final String NOME_CATEGORIA_ALTERADO = "BBBBBBBBBBBBBBBBB";
    private static final String DESCRICAO_ALTERADO = "BBBBBBBBBBBBBBBB";
    private static final String IMAGEM_ALTERADO = "BBBBBBBBBBBB";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private CategoriaMapper mapper;

    public Categoria criarCategoria() {
        var dto = new Categoria();
        dto.setNomeCategoria(PADRAO_NOME_CATEGORIA);
        dto.setDescricao(PADRAO_DESCRICAO);
        dto.setImagem(PADRAO_IMAGEM);

        return dto;
    }

    @Test
    @Transactional
    void salvarCategoria() throws Exception {
        CategoriaDTO dto = mapper.toDto(criarCategoria());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isCreated());

        List<Categoria> lista = repository.findAll();
        Categoria test = lista.get(lista.size() - 1);
        assertThat(test.getNomeCategoria()).isEqualTo(PADRAO_NOME_CATEGORIA);
        assertThat(test.getDescricao()).isEqualTo(PADRAO_DESCRICAO);
        assertThat(test.getImagem()).isEqualTo(PADRAO_IMAGEM);
    }

    @Test
    @Transactional
    void salvarCategoriaRepetida() throws Exception {
        repository.save(criarCategoria());

        CategoriaDTO dto = mapper.toDto(criarCategoria());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    void listarCategorias() throws Exception {
        Categoria obj = repository.save(criarCategoria());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].nomeCategoria").value(hasItem(PADRAO_NOME_CATEGORIA)))
            .andExpect(jsonPath("$.results[*].descricao").value(hasItem(PADRAO_DESCRICAO)))
            .andExpect(jsonPath("$.results[*].imagem").value(hasItem(PADRAO_IMAGEM)));
    }

    @Test
    @Transactional
    void listarCategoriasComFiltro() throws Exception {
        Categoria obj = repository.save(criarCategoria());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&nome=A"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].nomeCategoria").value(hasItem(PADRAO_NOME_CATEGORIA)))
            .andExpect(jsonPath("$.results[*].descricao").value(hasItem(PADRAO_DESCRICAO)))
            .andExpect(jsonPath("$.results[*].imagem").value(hasItem(PADRAO_IMAGEM)));
    }

    @Test
    @Transactional
    void listarCategoriasPaginadas() throws Exception {
        var cat = new Categoria();
        cat.setNomeCategoria("Livros");
        repository.save(cat);

        Categoria obj = repository.save(criarCategoria());

        var novaCat = new Categoria();
        novaCat.setNomeCategoria("Jogos");
        repository.save(novaCat);

        mockMvc
            .perform(get(ENTITY_API_URL + "?page=1&size=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].nomeCategoria").value(hasItem(PADRAO_NOME_CATEGORIA)))
            .andExpect(jsonPath("$.results[*].descricao").value(hasItem(PADRAO_DESCRICAO)))
            .andExpect(jsonPath("$.results[*].imagem").value(hasItem(PADRAO_IMAGEM)));
    }

    @Test
    @Transactional
    void obterCategoria() throws Exception {
        Categoria obj = repository.save(criarCategoria());

        mockMvc
            .perform(get(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obj.getId().intValue()))
            .andExpect(jsonPath("$.nomeCategoria").value(PADRAO_NOME_CATEGORIA))
            .andExpect(jsonPath("$.descricao").value(PADRAO_DESCRICAO))
            .andExpect(jsonPath("$.imagem").value(PADRAO_IMAGEM));
    }

    @Test
    @Transactional
    void testarCategoriaNaoExistente() throws Exception {
        mockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void excluirCategoria() throws Exception {
        Categoria obj = repository.save(criarCategoria());

        int qtdeAntesExclusao = repository.findAll().size();

        mockMvc
            .perform(delete(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isNoContent());

        List<Categoria> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao - 1);
    }

    @Test
    @Transactional
    void excluirNaoExistente() throws Exception {
        mockMvc
            .perform(delete(ENTITY_API_URL_ID, 0))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void excluirSemId() throws Exception {
        mockMvc
            .perform(delete(ENTITY_API_URL))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @Transactional
    void atualizarCategoria() throws Exception {
        Categoria obj = repository.save(criarCategoria());

        int qtdeAntesExclusao = repository.findAll().size();

        CategoriaDTO dto = mapper.toDto(obj);

        dto.setNomeCategoria(NOME_CATEGORIA_ALTERADO);
        dto.setDescricao(DESCRICAO_ALTERADO);
        dto.setImagem(IMAGEM_ALTERADO);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, dto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isCreated());

        List<Categoria> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);

        Categoria test = repository.findById(obj.getId()).get();

        assertThat(test.getNomeCategoria()).isEqualTo(NOME_CATEGORIA_ALTERADO);
        assertThat(test.getDescricao()).isEqualTo(DESCRICAO_ALTERADO);
        assertThat(test.getImagem()).isEqualTo(IMAGEM_ALTERADO);
    }

    @Test
    @Transactional
    void atualizarCategoriaNaoExistente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Categoria obj = criarCategoria();
        obj.setId(Long.MAX_VALUE);

        CategoriaDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, obj.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isNotFound());

        List<Categoria> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarCategoriaComParametroIdDiferente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Categoria obj = criarCategoria();
        obj.setId(Long.MAX_VALUE);

        CategoriaDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, Long.MAX_VALUE - 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isBadRequest());

        List<Categoria> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarCategoriaSemParametroId() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Categoria obj = criarCategoria();

        CategoriaDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isMethodNotAllowed());

        List<Categoria> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }
}
