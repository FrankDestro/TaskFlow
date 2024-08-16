package com.dev.taskflow.services.mappers;

import com.dev.taskflow.models.dto.TarefaDTO;
import com.dev.taskflow.models.entities.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface TarefaMapper {

    @Mapping(source = "pessoa", target = "pessoa")
    @Mapping(source = "tarefa.pessoa.departamento.id", target = "pessoa.departamentoId")
    @Mapping(source = "tarefa.pessoa.departamento.nome", target = "pessoa.departamentoName")
    TarefaDTO toTarefaDTO(Tarefa tarefa);
    Tarefa toTarefaEntity(TarefaDTO tarefaDTO);

    @Mapping(target = "id", ignore = true)
    void updateTarefaFromDTO(TarefaDTO tarefaDTO, @MappingTarget Tarefa tarefa);
}


