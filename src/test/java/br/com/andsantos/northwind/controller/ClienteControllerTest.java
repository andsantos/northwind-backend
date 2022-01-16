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
import br.com.andsantos.northwind.domain.Cliente;
import br.com.andsantos.northwind.repository.ClienteRepository;
import br.com.andsantos.northwind.service.dto.ClienteDTO;
import br.com.andsantos.northwind.service.mapper.ClienteMapper;
import br.com.andsantos.northwind.util.TestUtil;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class ClienteControllerTest {
    private static final String ENTITY_API_URL = "/api/clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final String NOME_EMPRESA = "AAAAAAAAAAAAAAAA";
    private static final String NOME_CONTATO = "AAAAAAAAAAAAA";
    private static final String TITULO_CONTATO = "AAA";
    private static final String ENDERECO = "AAAAA";
    private static final String CIDADE = "AAAAAAAA";
    private static final String REGIAO = "AA";
    private static final String NUMEROCEP = "00000000";
    private static final String PAIS = "BR";
    private static final String TELEFONE = "(AA) AAAA-AAAA";
    private static final String FAX = "(AA) AAAA-AAAA";

    private static final String NOME_EMPRESA_ALTERADO = "BBBBBBBBBBBBBBBBBB";
    private static final String NOME_CONTATO_ALTERADO = "BBBBBBBBBBBBBBBBBB";
    private static final String TITULO_CONTATO_ALTERADO = "BBB";
    private static final String ENDERECO_ALTERADO = "BBBB";
    private static final String CIDADE_ALTERADO = "BBBBBBBB";
    private static final String REGIAO_ALTERADO = "BB";
    private static final String NUMEROCEP_ALTERADO = "99999999";
    private static final String PAIS_ALTERADO = "BB";
    private static final String TELEFONE_ALTERADO = "(BB) BBBB-BBBB";
    private static final String FAX_ALTERADO = "(BB) BBBB-BBBB";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteMapper mapper;

    public Cliente criarCliente() {
        var dto = new Cliente();
        dto.setNomeEmpresa(NOME_EMPRESA);
        dto.setNomeContato(NOME_CONTATO);
        dto.setTituloContato(TITULO_CONTATO);
        dto.setEndereco(ENDERECO);
        dto.setCidade(CIDADE);
        dto.setRegiao(REGIAO);
        dto.setNumeroCEP(NUMEROCEP);
        dto.setPais(PAIS);
        dto.setTelefone(TELEFONE);
        dto.setFax(FAX);

        return dto;
    }

    @Test
    @Transactional
    void salvarCliente() throws Exception {
        ClienteDTO dto = mapper.toDto(criarCliente());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isCreated());

        List<Cliente> lista = repository.findAll();
        Cliente test = lista.get(lista.size() - 1);
        assertThat(test.getNomeEmpresa()).isEqualTo(NOME_EMPRESA);
        assertThat(test.getNomeContato()).isEqualTo(NOME_CONTATO);
        assertThat(test.getTituloContato()).isEqualTo(TITULO_CONTATO);
        assertThat(test.getEndereco()).isEqualTo(ENDERECO);
        assertThat(test.getCidade()).isEqualTo(CIDADE);
        assertThat(test.getRegiao()).isEqualTo(REGIAO);
        assertThat(test.getNumeroCEP()).isEqualTo(NUMEROCEP);
        assertThat(test.getPais()).isEqualTo(PAIS);
        assertThat(test.getTelefone()).isEqualTo(TELEFONE);
        assertThat(test.getFax()).isEqualTo(FAX);
    }

    @Test
    @Transactional
    void salvarCategoriaRepetida() throws Exception {
        repository.save(criarCliente());

        ClienteDTO dto = mapper.toDto(criarCliente());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    void listarClientes() throws Exception {
        Cliente obj = repository.save(criarCliente());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].nomeEmpresa").value(hasItem(NOME_EMPRESA)))
            .andExpect(jsonPath("$.results[*].nomeContato").value(hasItem(NOME_CONTATO)))
            .andExpect(jsonPath("$.results[*].tituloContato").value(hasItem(TITULO_CONTATO)))
            .andExpect(jsonPath("$.results[*].endereco").value(hasItem(ENDERECO)))
            .andExpect(jsonPath("$.results[*].cidade").value(hasItem(CIDADE)))
            .andExpect(jsonPath("$.results[*].regiao").value(hasItem(REGIAO)))
            .andExpect(jsonPath("$.results[*].numeroCEP").value(hasItem(NUMEROCEP)))
            .andExpect(jsonPath("$.results[*].pais").value(hasItem(PAIS)))
            .andExpect(jsonPath("$.results[*].telefone").value(hasItem(TELEFONE)))
            .andExpect(jsonPath("$.results[*].fax").value(hasItem(FAX)));
    }

    @Test
    @Transactional
    void listarClientesComFiltro() throws Exception {
        Cliente obj = repository.save(criarCliente());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&nome=A"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].nomeEmpresa").value(hasItem(NOME_EMPRESA)))
            .andExpect(jsonPath("$.results[*].nomeContato").value(hasItem(NOME_CONTATO)))
            .andExpect(jsonPath("$.results[*].tituloContato").value(hasItem(TITULO_CONTATO)))
            .andExpect(jsonPath("$.results[*].endereco").value(hasItem(ENDERECO)))
            .andExpect(jsonPath("$.results[*].cidade").value(hasItem(CIDADE)))
            .andExpect(jsonPath("$.results[*].regiao").value(hasItem(REGIAO)))
            .andExpect(jsonPath("$.results[*].numeroCEP").value(hasItem(NUMEROCEP)))
            .andExpect(jsonPath("$.results[*].pais").value(hasItem(PAIS)))
            .andExpect(jsonPath("$.results[*].telefone").value(hasItem(TELEFONE)))
            .andExpect(jsonPath("$.results[*].fax").value(hasItem(FAX)));
    }

    @Test
    @Transactional
    void obterCliente() throws Exception {
        Cliente obj = repository.save(criarCliente());

        mockMvc
            .perform(get(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obj.getId().intValue()))
            .andExpect(jsonPath("$.nomeEmpresa").value(NOME_EMPRESA))
            .andExpect(jsonPath("$.nomeContato").value(NOME_CONTATO))
            .andExpect(jsonPath("$.tituloContato").value(TITULO_CONTATO))
            .andExpect(jsonPath("$.endereco").value(ENDERECO))
            .andExpect(jsonPath("$.cidade").value(CIDADE))
            .andExpect(jsonPath("$.regiao").value(REGIAO))
            .andExpect(jsonPath("$.numeroCEP").value(NUMEROCEP))
            .andExpect(jsonPath("$.pais").value(PAIS))
            .andExpect(jsonPath("$.telefone").value(TELEFONE))
            .andExpect(jsonPath("$.fax").value(FAX));
    }

    @Test
    @Transactional
    void testarClienteNaoExistente() throws Exception {
        mockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void excluirCliente() throws Exception {
        Cliente obj = repository.save(criarCliente());

        int qtdeAntesExclusao = repository.findAll().size();

        mockMvc
            .perform(delete(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isNoContent());

        List<Cliente> lista = repository.findAll();
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
    void atualizarCliente() throws Exception {
        Cliente obj = repository.save(criarCliente());

        int qtdeAntesExclusao = repository.findAll().size();

        ClienteDTO dto = mapper.toDto(obj);

        dto.setNomeEmpresa(NOME_EMPRESA_ALTERADO);
        dto.setNomeContato(NOME_CONTATO_ALTERADO);
        dto.setTituloContato(TITULO_CONTATO_ALTERADO);
        dto.setEndereco(ENDERECO_ALTERADO);
        dto.setCidade(CIDADE_ALTERADO);
        dto.setRegiao(REGIAO_ALTERADO);
        dto.setNumeroCEP(NUMEROCEP_ALTERADO);
        dto.setPais(PAIS_ALTERADO);
        dto.setTelefone(TELEFONE_ALTERADO);
        dto.setFax(FAX_ALTERADO);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, dto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isCreated());

        List<Cliente> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);

        Cliente test = repository.findById(obj.getId()).get();

        assertThat(test.getNomeEmpresa()).isEqualTo(NOME_EMPRESA_ALTERADO);
        assertThat(test.getNomeContato()).isEqualTo(NOME_CONTATO_ALTERADO);
        assertThat(test.getTituloContato()).isEqualTo(TITULO_CONTATO_ALTERADO);
        assertThat(test.getEndereco()).isEqualTo(ENDERECO_ALTERADO);
        assertThat(test.getCidade()).isEqualTo(CIDADE_ALTERADO);
        assertThat(test.getRegiao()).isEqualTo(REGIAO_ALTERADO);
        assertThat(test.getNumeroCEP()).isEqualTo(NUMEROCEP_ALTERADO);
        assertThat(test.getPais()).isEqualTo(PAIS_ALTERADO);
        assertThat(test.getTelefone()).isEqualTo(TELEFONE_ALTERADO);
        assertThat(test.getFax()).isEqualTo(FAX_ALTERADO);
    }

    @Test
    @Transactional
    void atualizarClienteNaoExistente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Cliente obj = criarCliente();
        obj.setId(Long.MAX_VALUE);

        ClienteDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, obj.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isNotFound());

        List<Cliente> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarClienteComParametroIdDiferente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Cliente obj = criarCliente();
        obj.setId(Long.MAX_VALUE);

        ClienteDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, Long.MAX_VALUE - 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isBadRequest());

        List<Cliente> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarClienteSemParametroId() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Cliente obj = criarCliente();

        ClienteDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isMethodNotAllowed());

        List<Cliente> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }
}
