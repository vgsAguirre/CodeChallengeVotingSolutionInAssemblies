package com.desafio.projeto_sicredi.repositories;

import com.desafio.projeto_sicredi.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findAll();
    List<Sessao> findByPautaId(Long id);
    boolean existsByStatusIsTrue();
    Sessao findByStatus(Boolean status);
}
