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
import br.com.andsantos.northwind.domain.Transportadora;
import br.com.andsantos.northwind.repository.TransportadoraRepository;
import br.com.andsantos.northwind.service.dto.TransportadoraDTO;
import br.com.andsantos.northwind.service.mapper.TransportadoraMapper;
import br.com.andsantos.northwind.util.TestUtil;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class TransportadoraControllerTest {
    private static final String ENTITY_API_URL = "/api/transportadoras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final String NOME_TRANSPORTADORA = "AAAAAAAAAAAAAAA";
    private static final String TELEFONE = "(AA) AAAA-7777";

    private static final String NOME_TRANSPORTADORA_ALTERADO = "BBBBBBBBBBBBBBB";
    private static final String TELEFONE_ALTERADO = "(AA) BBBB-7777";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransportadoraRepository repository;

    @Autowired
    private TransportadoraMapper mapper;

    public Transportadora criarTransportadora() {
        var dto = new Transportadora();
        dto.setNomeTransportadora(NOME_TRANSPORTADORA);
        dto.setTelefone(TELEFONE);

        return dto;
    }

    @Test
    @Transactional
    void salvarTransportadora() throws Exception {
        TransportadoraDTO dto = mapper.toDto(criarTransportadora());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isCreated());

        List<Transportadora> lista = repository.findAll();
        Transportadora test = lista.get(lista.size() - 1);
        assertThat(test.getNomeTransportadora()).isEqualTo(NOME_TRANSPORTADORA);
        assertThat(test.getTelefone()).isEqualTo(TELEFONE);
    }

    @Test
    @Transactional
    void listarTransportadoras() throws Exception {
        Transportadora obj = repository.save(criarTransportadora());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeTransportadora").value(hasItem(NOME_TRANSPORTADORA)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(TELEFONE)));
    }

    @Test
    @Transactional
    void listarTransportadorasComFiltro() throws Exception {
        Transportadora obj = repository.save(criarTransportadora());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&nome=A"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeTransportadora").value(hasItem(NOME_TRANSPORTADORA)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(TELEFONE)));
    }

    @Test
    @Transactional
    void obterTransportadora() throws Exception {
        Transportadora obj = repository.save(criarTransportadora());

        mockMvc
            .perform(get(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obj.getId().intValue()))
            .andExpect(jsonPath("$.nomeTransportadora").value(NOME_TRANSPORTADORA))
            .andExpect(jsonPath("$.telefone").value(TELEFONE));
    }

    @Test
    @Transactional
    void testarTransportadoraNaoExistente() throws Exception {
        mockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void excluirTransportadora() throws Exception {
        Transportadora obj = repository.save(criarTransportadora());

        int qtdeAntesExclusao = repository.findAll().size();

        mockMvc
            .perform(delete(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isNoContent());

        List<Transportadora> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao - 1);
    }

    @Test
    @Transactional
    void atualizarTransportadora() throws Exception {
        Transportadora obj = repository.save(criarTransportadora());

        int qtdeAntesExclusao = repository.findAll().size();

        TransportadoraDTO dto = mapper.toDto(obj);

        dto.setNomeTransportadora(NOME_TRANSPORTADORA_ALTERADO);
        dto.setTelefone(TELEFONE_ALTERADO);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, dto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isCreated());

        List<Transportadora> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);

        Transportadora test = repository.findById(obj.getId()).get();

        assertThat(test.getNomeTransportadora()).isEqualTo(NOME_TRANSPORTADORA_ALTERADO);
        assertThat(test.getTelefone()).isEqualTo(TELEFONE_ALTERADO);
    }

    @Test
    @Transactional
    void atualizarTransportadoraNaoExistente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Transportadora obj = criarTransportadora();
        obj.setId(Long.MAX_VALUE);

        TransportadoraDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, obj.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isNotFound());

        List<Transportadora> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarTransportadoraComParametroIdDiferente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Transportadora obj = criarTransportadora();
        obj.setId(Long.MAX_VALUE);

        TransportadoraDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, Long.MAX_VALUE - 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isBadRequest());

        List<Transportadora> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarTransportadoraSemParametroId() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Transportadora obj = criarTransportadora();

        TransportadoraDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isMethodNotAllowed());

        List<Transportadora> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }
}
