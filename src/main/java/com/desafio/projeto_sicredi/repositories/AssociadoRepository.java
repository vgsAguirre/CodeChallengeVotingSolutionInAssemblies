package com.desafio.projeto_sicredi.repositories;

import com.desafio.projeto_sicredi.entities.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    List<Associado> findAll();
    @Query("SELECT COUNT(a) > 0 FROM Associado a")
    boolean existsAllAssociados();

    Associado findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
