package com.desafio.projeto_sicredi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessao")
@Entity
public class Sessao implements Serializable {

    private static final long serialVersionUID = -3916158868185845938L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tempo_sessao", nullable = false)
    private Integer tempoSessao;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pauta", nullable = false)
    private Pauta pauta;

}
