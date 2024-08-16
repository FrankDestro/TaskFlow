package com.dev.taskflow.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PessoaDTO {

    private Long id;
    private String nome;

    private Long departamentoId;
    private String departamentoName;
}
