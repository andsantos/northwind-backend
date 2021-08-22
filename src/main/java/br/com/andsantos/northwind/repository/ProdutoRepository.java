package br.com.andsantos.northwind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.andsantos.northwind.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	Boolean existsByNomeProduto(String nomeProduto);

	Page<Produto> findAllByNomeProdutoContaining(String nomeProduto, Pageable pageable);
}
