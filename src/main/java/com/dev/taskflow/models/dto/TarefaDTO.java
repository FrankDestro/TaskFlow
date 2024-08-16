package com.dev.taskflow.models.dto;

import com.dev.taskflow.models.entities.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TarefaDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Instant prazo;
    private Integer duracao;
    private boolean finalizada;

    private PessoaDTO pessoa;
    private DepartamentoDTO departamento;
}
