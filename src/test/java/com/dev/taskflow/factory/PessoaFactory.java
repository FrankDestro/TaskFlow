package com.dev.taskflow.factory;

import com.dev.taskflow.models.dto.PessoaDTO;
import com.dev.taskflow.models.entities.Pessoa;
import com.dev.taskflow.models.entities.Tarefa;
import com.dev.taskflow.projections.PessoaMediaHorasGastas;
import com.dev.taskflow.projections.PessoasComHorasProjection;
import com.dev.taskflow.services.mappers.PessoaMapper;

import java.util.ArrayList;
import java.util.List;

public class PessoaFactory {

    private PessoaMapper pessoaMapper;

    public static Pessoa createPessoa() {
        List<Tarefa> list = new ArrayList<>();
        Pessoa pessoa = new Pessoa(1L, "Ana Silva", DepartamentoFactory.createDepartamento(), list);
        return pessoa;
    }

    public static PessoaDTO createPessoaDTO() {
        PessoaDTO pessoaDTO = new PessoaDTO(1L, "Ana Silva", 1L,"Financeiro");
        return pessoaDTO;
    }

    public static PessoaMediaHorasGastas createPessoasComHorasProjection (String Nome, double MediaHorasGastas) {
        return new PessoaMediaHorasGastas () {

            @Override
            public String getNome() {
                return "";
            }

            @Override
            public Double getMediaHorasGastas() {
                return 0.0;
            }
        };
    }
}
