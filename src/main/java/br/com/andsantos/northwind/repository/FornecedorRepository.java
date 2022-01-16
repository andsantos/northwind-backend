package br.com.andsantos.northwind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.andsantos.northwind.domain.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    boolean existsByNomeFornecedor(String nomeFornecedor);

    Page<Fornecedor> findAllByNomeFornecedorContaining(String nomeFornecedor, Pageable pageable);
}
