package br.com.andsantos.northwind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.andsantos.northwind.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Boolean existsByNomeEmpresa(String nomeEmpresa);

	Page<Cliente> findAllByNomeEmpresaContaining(String nomeEmpresa, Pageable pageable);
}
