package com.dev.taskflow.factory;

import com.dev.taskflow.models.dto.TarefaDTO;
import com.dev.taskflow.models.entities.Tarefa;

import java.time.Instant;

public class TarefaFactory {

    public static Tarefa createTarefa() {
        Tarefa tarefa = new Tarefa(1L,"Recrutar novo funcionário", "Recrutamento para a vaga de desenvolvedor", Instant.now(), 100, true,
                DepartamentoFactory.createDepartamento()  , PessoaFactory.createPessoa());
        return tarefa;
    }

    public static TarefaDTO createTarefaDTO() {
        TarefaDTO  tarefaDTO = new TarefaDTO(1L,"Recrutar novo funcionário", "Recrutamento para a vaga de desenvolvedor", Instant.now(), 100, true,
                PessoaFactory.createPessoaDTO(), DepartamentoFactory.createDepartamentoDTO());
        return tarefaDTO;
    }
}
