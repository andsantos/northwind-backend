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
import br.com.andsantos.northwind.domain.Fornecedor;
import br.com.andsantos.northwind.repository.FornecedorRepository;
import br.com.andsantos.northwind.service.dto.FornecedorDTO;
import br.com.andsantos.northwind.service.mapper.FornecedorMapper;
import br.com.andsantos.northwind.util.TestUtil;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class FornecedorControllerTest {
    private static final String ENTITY_API_URL = "/api/fornecedores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final String NOME_FORNECEDOR = "AAAAAAAAAAAAAAAA";
    private static final String NOME_CONTATO = "AAAAAAAAAAAAAAAA";
    private static final String TITULO = "AAAAAAAAAAAAAAAA";
    private static final String ENDERECO = "AAAAAAAAAAAAAAAA";
    private static final String CIDADE = "AAAAAAAAAAAAAAAA";
    private static final String REGIAO = "AAAAAAAAAAAAAAAA";
    private static final String NUMERO_CEP = "00000-000";
    private static final String SIGLA_PAIS = "AA";
    private static final String TELEFONE = "(AA) AAAA-AAAA";
    private static final String FAX = "(AA) AAAA-AAAA";
    private static final String HOMEPAGE = "AAAAAAAAAAAAAAAA";

    private static final String NOME_FORNECEDOR_ALTERADO = "BBBBBBBBBBBBBBBA";
    private static final String NOME_CONTATO_ALTERADO = "BBBBBBBBBBBBBBBA";
    private static final String TITULO_ALTERADO = "BBBBBBBBBBBBBBBA";
    private static final String ENDERECO_ALTERADO = "BBBBBBBBBBBBBBBA";
    private static final String CIDADE_ALTERADO = "BBBBBBBBBBBBBBBA";
    private static final String REGIAO_ALTERADO = "BBBBBBBBBBBBBBBA";
    private static final String NUMERO_CEP_ALTERADO = "00000-000";
    private static final String SIGLA_PAIS_ALTERADO = "AA";
    private static final String TELEFONE_ALTERADO = "(AA) BBBA-BBBA";
    private static final String FAX_ALTERADO = "(AA) BBBA-BBBA";
    private static final String HOMEPAGE_ALTERADO = "BBBBBBBBBBBBBBBA";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FornecedorRepository repository;

    @Autowired
    private FornecedorMapper mapper;

    public Fornecedor criarFornecedor() {
        var dto = new Fornecedor();
        dto.setNomeFornecedor(NOME_FORNECEDOR);
        dto.setNomeContato(NOME_CONTATO);
        dto.setTitulo(TITULO);
        dto.setEndereco(ENDERECO);
        dto.setCidade(CIDADE);
        dto.setRegiao(REGIAO);
        dto.setNumeroCEP(NUMERO_CEP);
        dto.setSiglaPais(SIGLA_PAIS);
        dto.setTelefone(TELEFONE);
        dto.setFax(FAX);
        dto.setHomePage(HOMEPAGE);

        return dto;
    }

    @Test
    @Transactional
    void salvarFornecedor() throws Exception {
        FornecedorDTO dto = mapper.toDto(criarFornecedor());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isCreated());

        List<Fornecedor> lista = repository.findAll();
        Fornecedor test = lista.get(lista.size() - 1);
        assertThat(test.getNomeFornecedor()).isEqualTo(NOME_FORNECEDOR);
        assertThat(test.getNomeContato()).isEqualTo(NOME_CONTATO);
        assertThat(test.getTitulo()).isEqualTo(TITULO);
        assertThat(test.getEndereco()).isEqualTo(ENDERECO);
        assertThat(test.getCidade()).isEqualTo(CIDADE);
        assertThat(test.getRegiao()).isEqualTo(REGIAO);
        assertThat(test.getNumeroCEP()).isEqualTo(NUMERO_CEP);
        assertThat(test.getSiglaPais()).isEqualTo(SIGLA_PAIS);
        assertThat(test.getTelefone()).isEqualTo(TELEFONE);
        assertThat(test.getFax()).isEqualTo(FAX);
        assertThat(test.getHomePage()).isEqualTo(HOMEPAGE);
    }

    @Test
    @Transactional
    void salvarCategoriaRepetida() throws Exception {
        repository.save(criarFornecedor());

        FornecedorDTO dto = mapper.toDto(criarFornecedor());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    void listarFornecedores() throws Exception {
        Fornecedor obj = repository.save(criarFornecedor());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].nomeFornecedor").value(hasItem(NOME_FORNECEDOR)))
            .andExpect(jsonPath("$.results[*].nomeContato").value(hasItem(NOME_CONTATO)))
            .andExpect(jsonPath("$.results[*].titulo").value(hasItem(TITULO)))
            .andExpect(jsonPath("$.results[*].endereco").value(hasItem(ENDERECO)))
            .andExpect(jsonPath("$.results[*].cidade").value(hasItem(CIDADE)))
            .andExpect(jsonPath("$.results[*].regiao").value(hasItem(REGIAO)))
            .andExpect(jsonPath("$.results[*].numeroCEP").value(hasItem(NUMERO_CEP)))
            .andExpect(jsonPath("$.results[*].siglaPais").value(hasItem(SIGLA_PAIS)))
            .andExpect(jsonPath("$.results[*].telefone").value(hasItem(TELEFONE)))
            .andExpect(jsonPath("$.results[*].fax").value(hasItem(FAX)))
            .andExpect(jsonPath("$.results[*].homePage").value(hasItem(HOMEPAGE)));
    }

    @Test
    @Transactional
    void listarFornecedoresComFiltro() throws Exception {
        Fornecedor obj = repository.save(criarFornecedor());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&nome=A"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].nomeFornecedor").value(hasItem(NOME_FORNECEDOR)))
            .andExpect(jsonPath("$.results[*].nomeContato").value(hasItem(NOME_CONTATO)))
            .andExpect(jsonPath("$.results[*].titulo").value(hasItem(TITULO)))
            .andExpect(jsonPath("$.results[*].endereco").value(hasItem(ENDERECO)))
            .andExpect(jsonPath("$.results[*].cidade").value(hasItem(CIDADE)))
            .andExpect(jsonPath("$.results[*].regiao").value(hasItem(REGIAO)))
            .andExpect(jsonPath("$.results[*].numeroCEP").value(hasItem(NUMERO_CEP)))
            .andExpect(jsonPath("$.results[*].siglaPais").value(hasItem(SIGLA_PAIS)))
            .andExpect(jsonPath("$.results[*].telefone").value(hasItem(TELEFONE)))
            .andExpect(jsonPath("$.results[*].fax").value(hasItem(FAX)))
            .andExpect(jsonPath("$.results[*].homePage").value(hasItem(HOMEPAGE)));
    }

    @Test
    @Transactional
    void obterFornecedor() throws Exception {
        Fornecedor obj = repository.save(criarFornecedor());

        mockMvc
            .perform(get(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.nomeFornecedor").value(NOME_FORNECEDOR))
            .andExpect(jsonPath("$.nomeContato").value(NOME_CONTATO))
            .andExpect(jsonPath("$.titulo").value(TITULO))
            .andExpect(jsonPath("$.endereco").value(ENDERECO))
            .andExpect(jsonPath("$.cidade").value(CIDADE))
            .andExpect(jsonPath("$.regiao").value(REGIAO))
            .andExpect(jsonPath("$.numeroCEP").value(NUMERO_CEP))
            .andExpect(jsonPath("$.siglaPais").value(SIGLA_PAIS))
            .andExpect(jsonPath("$.telefone").value(TELEFONE))
            .andExpect(jsonPath("$.fax").value(FAX))
            .andExpect(jsonPath("$.homePage").value(HOMEPAGE));
    }

    @Test
    @Transactional
    void testarFornecedorNaoExistente() throws Exception {
        mockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void excluirFornecedor() throws Exception {
        Fornecedor obj = repository.save(criarFornecedor());

        int qtdeAntesExclusao = repository.findAll().size();

        mockMvc
            .perform(delete(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isNoContent());

        List<Fornecedor> lista = repository.findAll();
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
    void atualizarFornecedor() throws Exception {
        Fornecedor obj = repository.save(criarFornecedor());

        int qtdeAntesExclusao = repository.findAll().size();

        FornecedorDTO dto = mapper.toDto(obj);

        dto.setNomeFornecedor(NOME_FORNECEDOR_ALTERADO);
        dto.setNomeContato(NOME_CONTATO_ALTERADO);
        dto.setTitulo(TITULO_ALTERADO);
        dto.setEndereco(ENDERECO_ALTERADO);
        dto.setCidade(CIDADE_ALTERADO);
        dto.setRegiao(REGIAO_ALTERADO);
        dto.setNumeroCEP(NUMERO_CEP_ALTERADO);
        dto.setSiglaPais(SIGLA_PAIS_ALTERADO);
        dto.setTelefone(TELEFONE_ALTERADO);
        dto.setFax(FAX_ALTERADO);
        dto.setHomePage(HOMEPAGE_ALTERADO);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, dto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isCreated());

        List<Fornecedor> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);

        Fornecedor test = repository.findById(obj.getId()).get();

        assertThat(test.getNomeFornecedor()).isEqualTo(NOME_FORNECEDOR_ALTERADO);
        assertThat(test.getNomeContato()).isEqualTo(NOME_CONTATO_ALTERADO);
        assertThat(test.getTitulo()).isEqualTo(TITULO_ALTERADO);
        assertThat(test.getEndereco()).isEqualTo(ENDERECO_ALTERADO);
        assertThat(test.getCidade()).isEqualTo(CIDADE_ALTERADO);
        assertThat(test.getRegiao()).isEqualTo(REGIAO_ALTERADO);
        assertThat(test.getNumeroCEP()).isEqualTo(NUMERO_CEP_ALTERADO);
        assertThat(test.getSiglaPais()).isEqualTo(SIGLA_PAIS_ALTERADO);
        assertThat(test.getTelefone()).isEqualTo(TELEFONE_ALTERADO);
        assertThat(test.getFax()).isEqualTo(FAX_ALTERADO);
        assertThat(test.getHomePage()).isEqualTo(HOMEPAGE_ALTERADO);
    }

    @Test
    @Transactional
    void atualizarFornecedorNaoExistente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Fornecedor obj = criarFornecedor();
        obj.setId(Long.MAX_VALUE);

        FornecedorDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, obj.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isNotFound());

        List<Fornecedor> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarFornecedorComParametroIdDiferente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Fornecedor obj = criarFornecedor();
        obj.setId(Long.MAX_VALUE);

        FornecedorDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, Long.MAX_VALUE - 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isBadRequest());

        List<Fornecedor> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarFornecedorSemParametroId() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Fornecedor obj = criarFornecedor();

        FornecedorDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isMethodNotAllowed());

        List<Fornecedor> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }
}
