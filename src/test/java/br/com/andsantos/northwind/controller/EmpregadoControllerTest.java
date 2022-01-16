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

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.com.andsantos.core.controller.IntegrationTest;
import br.com.andsantos.northwind.domain.Empregado;
import br.com.andsantos.northwind.repository.EmpregadoRepository;
import br.com.andsantos.northwind.service.dto.EmpregadoDTO;
import br.com.andsantos.northwind.service.mapper.EmpregadoMapper;
import br.com.andsantos.northwind.util.TestUtil;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class EmpregadoControllerTest {
    private static final String ENTITY_API_URL = "/api/empregados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private String SOBRENOME = "QWWERTY";
    private String NOME_EMPREGADO = "QWERTY";
    private String TITULO = "QW";
    private String TITULO_DE_CORTESIA = "ERT";
    private LocalDate DATA_NASCIMENTO = LocalDate.of(1990, 1, 1);
    private LocalDate DATA_ADMISSAO = LocalDate.of(2020, 1, 1);
    private String DATA_NASCIMENTO_STRING = "01/01/1990";
    private String DATA_ADMISSAO_STRING = "01/01/2020";
    private String ENDERECO = "ASDFGHJKL";
    private String CIDADE = "ASDFGHJKL";
    private String REGIAO = "ZXCVBNM";
    private String NUMERO_CEP = "00000000";
    private String SIGLA_PAIS = "BR";
    private String TELEFONE = "(21) 7777-7777";
    private String RAMAL = "123";
    private String FOTO = "qwerty.png";
    private String COMENTARIO = "ASDFGHJKKL";
    private Long SUPERVISOR = Long.valueOf(1L);

    private String SOBRENOME_ALTERADO = "BBBBBBBBBB";
    private String NOME_EMPREGADO_ALTERADO = "BBBBBB";
    private String TITULO_ALTERADO = "BB";
    private String TITULO_DE_CORTESIA_ALTERADO = "BBB";
    private LocalDate DATA_NASCIMENTO_ALTERADO = LocalDate.of(1990, 1, 2);
    private LocalDate DATA_ADMISSAO_ALTERADO = LocalDate.of(2020, 1, 2);
    private String ENDERECO_ALTERADO = "BBBBBB";
    private String CIDADE_ALTERADO = "BBBBBB";
    private String REGIAO_ALTERADO = "BBBBB";
    private String NUMERO_CEP_ALTERADO = "999999999";
    private String SIGLA_PAIS_ALTERADO = "BB";
    private String TELEFONE_ALTERADO = "(21) 00000-00000";
    private String RAMAL_ALTERADO = "890";
    private String FOTO_ALTERADO = "BBBBB.png";
    private String COMENTARIO_ALTERADO = "BBBBBBB";
    private Long SUPERVISOR_ALTERADO = Long.valueOf(9L);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpregadoRepository repository;

    @Autowired
    private EmpregadoMapper mapper;

    public Empregado criarEmpregado() {
        var dto = new Empregado();
        dto.setSobrenome(SOBRENOME);
        dto.setNomeEmpregado(NOME_EMPREGADO);
        dto.setTitulo(TITULO);
        dto.setTituloDeCortesia(TITULO_DE_CORTESIA);
        dto.setDataNascimento(DATA_NASCIMENTO);
        dto.setDataAdmissao(DATA_ADMISSAO);
        dto.setEndereco(ENDERECO);
        dto.setCidade(CIDADE);
        dto.setRegiao(REGIAO);
        dto.setNumeroCEP(NUMERO_CEP);
        dto.setSiglaPais(SIGLA_PAIS);
        dto.setTelefone(TELEFONE);
        dto.setRamal(RAMAL);
        dto.setFoto(FOTO);
        dto.setComentario(COMENTARIO);
        dto.setSupervisor(SUPERVISOR);

        return dto;
    }

    @Test
    @Transactional
    void salvarEmpregado() throws Exception {
        EmpregadoDTO dto = mapper.toDto(criarEmpregado());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isCreated());

        List<Empregado> lista = repository.findAll();
        Empregado test = lista.get(lista.size() - 1);
        assertThat(test.getSobrenome()).isEqualTo(SOBRENOME);
        assertThat(test.getNomeEmpregado()).isEqualTo(NOME_EMPREGADO);
        assertThat(test.getTitulo()).isEqualTo(TITULO);
        assertThat(test.getTituloDeCortesia()).isEqualTo(TITULO_DE_CORTESIA);
        assertThat(test.getDataNascimento()).isEqualTo(DATA_NASCIMENTO);
        assertThat(test.getDataAdmissao()).isEqualTo(DATA_ADMISSAO);
        assertThat(test.getEndereco()).isEqualTo(ENDERECO);
        assertThat(test.getCidade()).isEqualTo(CIDADE);
        assertThat(test.getRegiao()).isEqualTo(REGIAO);
        assertThat(test.getNumeroCEP()).isEqualTo(NUMERO_CEP);
        assertThat(test.getSiglaPais()).isEqualTo(SIGLA_PAIS);
        assertThat(test.getTelefone()).isEqualTo(TELEFONE);
        assertThat(test.getRamal()).isEqualTo(RAMAL);
        assertThat(test.getFoto()).isEqualTo(FOTO);
        assertThat(test.getComentario()).isEqualTo(COMENTARIO);
        assertThat(test.getSupervisor()).isEqualTo(SUPERVISOR);
    }

    @Test
    @Transactional
    void salvarCategoriaRepetida() throws Exception {
        repository.save(criarEmpregado());

        EmpregadoDTO dto = mapper.toDto(criarEmpregado());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    void listarEmpregados() throws Exception {
        Empregado obj = repository.save(criarEmpregado());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].sobrenome").value(hasItem(SOBRENOME)))
            .andExpect(jsonPath("$.results[*].nomeEmpregado").value(hasItem(NOME_EMPREGADO)))
            .andExpect(jsonPath("$.results[*].titulo").value(hasItem(TITULO)))
            .andExpect(jsonPath("$.results[*].tituloDeCortesia").value(hasItem(TITULO_DE_CORTESIA)))
            .andExpect(jsonPath("$.results[*].dataNascimento").value(hasItem(DATA_NASCIMENTO_STRING)))
            .andExpect(jsonPath("$.results[*].dataAdmissao").value(hasItem(DATA_ADMISSAO_STRING)))
            .andExpect(jsonPath("$.results[*].endereco").value(hasItem(ENDERECO)))
            .andExpect(jsonPath("$.results[*].cidade").value(hasItem(CIDADE)))
            .andExpect(jsonPath("$.results[*].regiao").value(hasItem(REGIAO)))
            .andExpect(jsonPath("$.results[*].numeroCEP").value(hasItem(NUMERO_CEP)))
            .andExpect(jsonPath("$.results[*].siglaPais").value(hasItem(SIGLA_PAIS)))
            .andExpect(jsonPath("$.results[*].telefone").value(hasItem(TELEFONE)))
            .andExpect(jsonPath("$.results[*].ramal").value(hasItem(RAMAL)))
            .andExpect(jsonPath("$.results[*].foto").value(hasItem(FOTO)))
            .andExpect(jsonPath("$.results[*].comentario").value(hasItem(COMENTARIO)))
            .andExpect(jsonPath("$.results[*].supervisor").value(hasItem(SUPERVISOR.intValue())));
    }

    @Test
    @Transactional
    void listarEmpregadosComFiltro() throws Exception {
        Empregado obj = repository.save(criarEmpregado());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&nome=Q"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.results[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.results[*].sobrenome").value(hasItem(SOBRENOME)))
            .andExpect(jsonPath("$.results[*].nomeEmpregado").value(hasItem(NOME_EMPREGADO)))
            .andExpect(jsonPath("$.results[*].titulo").value(hasItem(TITULO)))
            .andExpect(jsonPath("$.results[*].tituloDeCortesia").value(hasItem(TITULO_DE_CORTESIA)))
            .andExpect(jsonPath("$.results[*].dataNascimento").value(hasItem(DATA_NASCIMENTO_STRING)))
            .andExpect(jsonPath("$.results[*].dataAdmissao").value(hasItem(DATA_ADMISSAO_STRING)))
            .andExpect(jsonPath("$.results[*].endereco").value(hasItem(ENDERECO)))
            .andExpect(jsonPath("$.results[*].cidade").value(hasItem(CIDADE)))
            .andExpect(jsonPath("$.results[*].regiao").value(hasItem(REGIAO)))
            .andExpect(jsonPath("$.results[*].numeroCEP").value(hasItem(NUMERO_CEP)))
            .andExpect(jsonPath("$.results[*].siglaPais").value(hasItem(SIGLA_PAIS)))
            .andExpect(jsonPath("$.results[*].telefone").value(hasItem(TELEFONE)))
            .andExpect(jsonPath("$.results[*].ramal").value(hasItem(RAMAL)))
            .andExpect(jsonPath("$.results[*].foto").value(hasItem(FOTO)))
            .andExpect(jsonPath("$.results[*].comentario").value(hasItem(COMENTARIO)))
            .andExpect(jsonPath("$.results[*].supervisor").value(hasItem(SUPERVISOR.intValue())));
    }

    @Test
    @Transactional
    void obterEmpregado() throws Exception {
        Empregado obj = repository.save(criarEmpregado());

        mockMvc
            .perform(get(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obj.getId().intValue()))
            .andExpect(jsonPath("$.sobrenome").value(SOBRENOME))
            .andExpect(jsonPath("$.nomeEmpregado").value(NOME_EMPREGADO))
            .andExpect(jsonPath("$.titulo").value(TITULO))
            .andExpect(jsonPath("$.tituloDeCortesia").value(TITULO_DE_CORTESIA))
            .andExpect(jsonPath("$.dataNascimento").value(DATA_NASCIMENTO_STRING))
            .andExpect(jsonPath("$.dataAdmissao").value(DATA_ADMISSAO_STRING))
            .andExpect(jsonPath("$.endereco").value(ENDERECO))
            .andExpect(jsonPath("$.cidade").value(CIDADE))
            .andExpect(jsonPath("$.regiao").value(REGIAO))
            .andExpect(jsonPath("$.numeroCEP").value(NUMERO_CEP))
            .andExpect(jsonPath("$.siglaPais").value(SIGLA_PAIS))
            .andExpect(jsonPath("$.telefone").value(TELEFONE))
            .andExpect(jsonPath("$.ramal").value(RAMAL))
            .andExpect(jsonPath("$.foto").value(FOTO))
            .andExpect(jsonPath("$.comentario").value(COMENTARIO))
            .andExpect(jsonPath("$.supervisor").value(SUPERVISOR));
    }

    @Test
    @Transactional
    void testarEmpregadoNaoExistente() throws Exception {
        mockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void excluirEmpregado() throws Exception {
        Empregado obj = repository.save(criarEmpregado());

        int qtdeAntesExclusao = repository.findAll().size();

        mockMvc
            .perform(delete(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isNoContent());

        List<Empregado> lista = repository.findAll();
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
    void atualizarEmpregado() throws Exception {
        Empregado obj = repository.save(criarEmpregado());

        int qtdeAntesExclusao = repository.findAll().size();

        EmpregadoDTO dto = mapper.toDto(obj);

        dto.setSobrenome(SOBRENOME_ALTERADO);
        dto.setNomeEmpregado(NOME_EMPREGADO_ALTERADO);
        dto.setTitulo(TITULO_ALTERADO);
        dto.setTituloDeCortesia(TITULO_DE_CORTESIA_ALTERADO);
        dto.setDataNascimento(DATA_NASCIMENTO_ALTERADO);
        dto.setDataAdmissao(DATA_ADMISSAO_ALTERADO);
        dto.setEndereco(ENDERECO_ALTERADO);
        dto.setCidade(CIDADE_ALTERADO);
        dto.setRegiao(REGIAO_ALTERADO);
        dto.setNumeroCEP(NUMERO_CEP_ALTERADO);
        dto.setSiglaPais(SIGLA_PAIS_ALTERADO);
        dto.setTelefone(TELEFONE_ALTERADO);
        dto.setRamal(RAMAL_ALTERADO);
        dto.setFoto(FOTO_ALTERADO);
        dto.setComentario(COMENTARIO_ALTERADO);
        dto.setSupervisor(SUPERVISOR_ALTERADO);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, dto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isCreated());

        List<Empregado> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);

        Empregado test = repository.findById(obj.getId()).get();

        assertThat(test.getSobrenome()).isEqualTo(SOBRENOME_ALTERADO);
        assertThat(test.getNomeEmpregado()).isEqualTo(NOME_EMPREGADO_ALTERADO);
        assertThat(test.getTitulo()).isEqualTo(TITULO_ALTERADO);
        assertThat(test.getTituloDeCortesia()).isEqualTo(TITULO_DE_CORTESIA_ALTERADO);
        assertThat(test.getDataNascimento()).isEqualTo(DATA_NASCIMENTO_ALTERADO);
        assertThat(test.getDataAdmissao()).isEqualTo(DATA_ADMISSAO_ALTERADO);
        assertThat(test.getEndereco()).isEqualTo(ENDERECO_ALTERADO);
        assertThat(test.getCidade()).isEqualTo(CIDADE_ALTERADO);
        assertThat(test.getRegiao()).isEqualTo(REGIAO_ALTERADO);
        assertThat(test.getNumeroCEP()).isEqualTo(NUMERO_CEP_ALTERADO);
        assertThat(test.getSiglaPais()).isEqualTo(SIGLA_PAIS_ALTERADO);
        assertThat(test.getTelefone()).isEqualTo(TELEFONE_ALTERADO);
        assertThat(test.getRamal()).isEqualTo(RAMAL_ALTERADO);
        assertThat(test.getFoto()).isEqualTo(FOTO_ALTERADO);
        assertThat(test.getComentario()).isEqualTo(COMENTARIO_ALTERADO);
        assertThat(test.getSupervisor()).isEqualTo(SUPERVISOR_ALTERADO);
    }

    @Test
    @Transactional
    void atualizarEmpregadoNaoExistente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Empregado obj = criarEmpregado();
        obj.setId(Long.MAX_VALUE);

        EmpregadoDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, obj.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isNotFound());

        List<Empregado> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarEmpregadoComParametroIdDiferente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Empregado obj = criarEmpregado();
        obj.setId(Long.MAX_VALUE);

        EmpregadoDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, Long.MAX_VALUE - 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isBadRequest());

        List<Empregado> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarEmpregadoSemParametroId() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Empregado obj = criarEmpregado();

        EmpregadoDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isMethodNotAllowed());

        List<Empregado> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }
}
