package com.desafio.projeto_sicredi.entities;

import com.desafio.projeto_sicredi.enums.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "voto")
@Entity
public class Voto {

    private static final long serialVersionUID = 2283367844188154501L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_voto")
    private VotoEnum votoEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_associado", nullable = false)
    private Associado associado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pauta", nullable = false)
    private Pauta pauta;
}

