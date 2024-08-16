package com.dev.taskflow.services.mappers;

import com.dev.taskflow.models.dto.PessoaDTO;
import com.dev.taskflow.models.entities.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface PessoaMapper {

    @Mapping(source = "departamento.id", target = "departamentoId")
    @Mapping(source = "departamento.nome", target = "departamentoName")
    PessoaDTO toPessoaDTO(Pessoa pessoa);

    @Mapping(source = "departamentoId", target = "departamento.id")
    Pessoa toPessoaEntity(PessoaDTO pessoaDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "departamentoId", target = "departamento.id")
    void updatePessoaFromDTO(PessoaDTO pessoaDTO, @MappingTarget Pessoa pessoa);
}
