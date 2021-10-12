package br.com.andsantos.northwind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.andsantos.northwind.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	boolean existsByNomeCategoria(String nomeCategoria);

	Page<Categoria> findAllByNomeCategoriaContaining(String nomeCategoria, Pageable pageable);
}
