package br.com.andsantos.northwind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.andsantos.northwind.domain.Empregado;

@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {
	Boolean existsByNomeEmpregado(String nomeEmpregado);

	Page<Empregado> findAllByNomeEmpregadoContaining(String nomeEmpregado, Pageable pageable);
}
