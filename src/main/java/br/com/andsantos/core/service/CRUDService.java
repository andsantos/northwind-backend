package br.com.andsantos.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CRUDService<A> {
    A salvar(A dto);

    A atualizar(A dto);

    void excluir(Long id);

    A obter(Long id);

    Page<A> listar(Pageable pageable);
}
