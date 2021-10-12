package br.com.andsantos.northwind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.andsantos.northwind.domain.Transportadora;

@Repository
public interface TransportadoraRepository extends JpaRepository<Transportadora, Long> {
	boolean existsByNomeTransportadora(String nomeTransportadora);

	Page<Transportadora> findAllByNomeTransportadoraContaining(String nomeTransportadora, Pageable pageable);
}
