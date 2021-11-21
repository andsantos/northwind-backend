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

import java.math.BigDecimal;
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
import br.com.andsantos.northwind.domain.Fornecedor;
import br.com.andsantos.northwind.domain.Produto;
import br.com.andsantos.northwind.repository.CategoriaRepository;
import br.com.andsantos.northwind.repository.FornecedorRepository;
import br.com.andsantos.northwind.repository.ProdutoRepository;
import br.com.andsantos.northwind.service.dto.ProdutoDTO;
import br.com.andsantos.northwind.service.mapper.ProdutoMapper;
import br.com.andsantos.northwind.util.TestUtil;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class ProdutoControllerTest {
    private static final String ENTITY_API_URL = "/api/produtos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final String NOME_PRODUTO = "AAAAAAAAAAAAA";
    private static final String QUANTIDADE_POR_UNIDADE = "AAAA";
    private static final BigDecimal PRECO_UNITARIO = new BigDecimal(1000).setScale(2);
    private static final String PRECO_UNITARIO_STRING = "1.000,00";
    private static final Long UNIDADES_EM_ESTOQUE = Long.valueOf(1);
    private static final Boolean DESCONTINUADO = Boolean.FALSE;

    private static final String NOME_PRODUTO_ALTERADO = "BBBBBBBBBBBB";
    private static final String QUANTIDADE_POR_UNIDADE_ALTERADO = "BBB";
    private static final BigDecimal PRECO_UNITARIO_ALTERADO = new BigDecimal(1500).setScale(2);
    private static final Long UNIDADES_EM_ESTOQUE_ALTERADO = Long.valueOf(2);
    private static final Boolean DESCONTINUADO_ALTERADO = Boolean.TRUE;

    private static final String PADRAO_NOME_CATEGORIA = "AAAAAAAAAAAAA";
    private static final String PADRAO_DESCRICAO = "AAAAAAAAAAAAAAAAAA";
    private static final String PADRAO_IMAGEM = "AAAAAAAAAAAA";

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ProdutoMapper mapper;

    public Produto criarProduto() {
        var dto = new Produto();
        dto.setNomeProduto(NOME_PRODUTO);
        dto.setQuantidadePorUnidade(QUANTIDADE_POR_UNIDADE);
        dto.setPrecoUnitario(PRECO_UNITARIO);
        dto.setUnidadesEmEstoque(UNIDADES_EM_ESTOQUE);
        dto.setDescontinuado(DESCONTINUADO);

        Categoria categoria = categoriaRepository.save(criarCategoria());
        dto.setCategoria(categoria);

        Fornecedor fornecedor = fornecedorRepository.save(criarFornecedor());
        dto.setFornecedor(fornecedor);

        return dto;
    }

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

    public Categoria criarCategoria() {
        var dto = new Categoria();
        dto.setNomeCategoria(PADRAO_NOME_CATEGORIA);
        dto.setDescricao(PADRAO_DESCRICAO);
        dto.setImagem(PADRAO_IMAGEM);

        return dto;
    }

    @Test
    @Transactional
    void salvarProduto() throws Exception {
        ProdutoDTO dto = mapper.toDto(criarProduto());

        mockMvc
            .perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isCreated());

        List<Produto> lista = repository.findAll();
        Produto test = lista.get(lista.size() - 1);
        assertThat(test.getNomeProduto()).isEqualTo(NOME_PRODUTO);
        assertThat(test.getQuantidadePorUnidade()).isEqualTo(QUANTIDADE_POR_UNIDADE);
        assertThat(test.getPrecoUnitario()).isEqualTo(PRECO_UNITARIO);
        assertThat(test.getUnidadesEmEstoque()).isEqualTo(UNIDADES_EM_ESTOQUE.intValue());
        assertThat(test.getDescontinuado()).isEqualTo(DESCONTINUADO);
    }

    @Test
    @Transactional
    void listarProdutos() throws Exception {
        Produto obj = repository.save(criarProduto());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.[*].fornecedorId").value(hasItem(obj.getFornecedor().getId().intValue())))
            .andExpect(jsonPath("$.[*].categoriaId").value(hasItem(obj.getCategoria().getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeProduto").value(hasItem(NOME_PRODUTO)))
            .andExpect(jsonPath("$.[*].quantidadePorUnidade").value(hasItem(QUANTIDADE_POR_UNIDADE)))
            .andExpect(jsonPath("$.[*].precoUnitario").value(hasItem(PRECO_UNITARIO_STRING)))
            .andExpect(jsonPath("$.[*].unidadesEmEstoque").value(hasItem(UNIDADES_EM_ESTOQUE.intValue())))
            .andExpect(jsonPath("$.[*].descontinuado").value(hasItem(DESCONTINUADO)));
    }

    @Test
    @Transactional
    void listarProdutosComFiltro() throws Exception {
        Produto obj = repository.save(criarProduto());

        mockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&nome=A"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obj.getId().intValue())))
            .andExpect(jsonPath("$.[*].fornecedorId").value(hasItem(obj.getFornecedor().getId().intValue())))
            .andExpect(jsonPath("$.[*].categoriaId").value(hasItem(obj.getCategoria().getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeProduto").value(hasItem(NOME_PRODUTO)))
            .andExpect(jsonPath("$.[*].quantidadePorUnidade").value(hasItem(QUANTIDADE_POR_UNIDADE)))
            .andExpect(jsonPath("$.[*].precoUnitario").value(hasItem(PRECO_UNITARIO_STRING)))
            .andExpect(jsonPath("$.[*].unidadesEmEstoque").value(hasItem(UNIDADES_EM_ESTOQUE.intValue())))
            .andExpect(jsonPath("$.[*].descontinuado").value(hasItem(DESCONTINUADO)));
    }

    @Test
    @Transactional
    void obterProduto() throws Exception {
        Produto obj = repository.save(criarProduto());

        mockMvc
            .perform(get(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obj.getId().intValue()))
            .andExpect(jsonPath("$.fornecedorId").value(obj.getFornecedor().getId().intValue()))
            .andExpect(jsonPath("$.categoriaId").value(obj.getCategoria().getId().intValue()))
            .andExpect(jsonPath("$.nomeProduto").value(NOME_PRODUTO))
            .andExpect(jsonPath("$.quantidadePorUnidade").value(QUANTIDADE_POR_UNIDADE))
            .andExpect(jsonPath("$.precoUnitario").value(PRECO_UNITARIO_STRING))
            .andExpect(jsonPath("$.unidadesEmEstoque").value(UNIDADES_EM_ESTOQUE))
            .andExpect(jsonPath("$.descontinuado").value(DESCONTINUADO));
    }

    @Test
    @Transactional
    void testarProdutoNaoExistente() throws Exception {
        mockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void excluirProduto() throws Exception {
        Produto obj = repository.save(criarProduto());

        int qtdeAntesExclusao = repository.findAll().size();

        mockMvc
            .perform(delete(ENTITY_API_URL_ID, obj.getId()))
            .andExpect(status().isNoContent());

        List<Produto> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao - 1);
    }

    @Test
    @Transactional
    void atualizarProduto() throws Exception {
        Produto obj = repository.save(criarProduto());

        int qtdeAntesExclusao = repository.findAll().size();

        ProdutoDTO dto = mapper.toDto(obj);

        dto.setNomeProduto(NOME_PRODUTO_ALTERADO);
        dto.setQuantidadePorUnidade(QUANTIDADE_POR_UNIDADE_ALTERADO);
        dto.setPrecoUnitario(PRECO_UNITARIO_ALTERADO);
        dto.setUnidadesEmEstoque(UNIDADES_EM_ESTOQUE_ALTERADO);
        dto.setDescontinuado(DESCONTINUADO_ALTERADO);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, dto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isCreated());

        List<Produto> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);

        Produto test = repository.findById(obj.getId()).get();

        assertThat(test.getNomeProduto()).isEqualTo(NOME_PRODUTO_ALTERADO);
        assertThat(test.getQuantidadePorUnidade()).isEqualTo(QUANTIDADE_POR_UNIDADE_ALTERADO);
        assertThat(test.getPrecoUnitario()).isEqualTo(PRECO_UNITARIO_ALTERADO);
        assertThat(test.getUnidadesEmEstoque()).isEqualTo(UNIDADES_EM_ESTOQUE_ALTERADO);
        assertThat(test.getDescontinuado()).isEqualTo(DESCONTINUADO_ALTERADO);
    }

    @Test
    @Transactional
    void atualizarProdutoNaoExistente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Produto obj = criarProduto();
        obj.setId(Long.MAX_VALUE);

        ProdutoDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, obj.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isNotFound());

        List<Produto> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarProdutoComParametroIdDiferente() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Produto obj = criarProduto();
        obj.setId(Long.MAX_VALUE);

        ProdutoDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(
                put(ENTITY_API_URL_ID, Long.MAX_VALUE - 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dto))
            )
            .andExpect(status().isBadRequest());

        List<Produto> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }

    @Test
    @Transactional
    void atualizarProdutoSemParametroId() throws Exception {
        int qtdeAntesExclusao = repository.findAll().size();
        Produto obj = criarProduto();

        ProdutoDTO dto = mapper.toDto(obj);

        mockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isMethodNotAllowed());

        List<Produto> lista = repository.findAll();
        assertThat(lista).hasSize(qtdeAntesExclusao);
    }
}
