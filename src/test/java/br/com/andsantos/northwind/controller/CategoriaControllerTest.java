package br.com.andsantos.northwind.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import br.com.andsantos.northwind.util.TestUtil;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class CategoriaControllerTest {
    private static final String ENTITY_API_URL = "/api/categorias";

    private static final String PADRAO_NOME_CATEGORIA = "Bebidas";
    private static final String PADRAO_DESCRICAO = "Refrigerantes, sucos, cervejas...";
    private static final String PADRAO_IMAGEM = "bebida.gif";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository repository;

    @Test
    @Transactional
    void salvarCategoria() throws Exception {
    	var dto = new CategoriaDTO();
        dto.setNomeCategoria(PADRAO_NOME_CATEGORIA);
        dto.setDescricao(PADRAO_DESCRICAO);
        dto.setImagem(PADRAO_IMAGEM);

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
}
