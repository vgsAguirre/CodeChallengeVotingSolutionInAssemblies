package com.desafio.projeto_sicredi.repositories;

import com.desafio.projeto_sicredi.entities.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
