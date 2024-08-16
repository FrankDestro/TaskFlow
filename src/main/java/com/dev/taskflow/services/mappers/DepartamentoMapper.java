package com.dev.taskflow.services.mappers;

import com.dev.taskflow.models.dto.DepartamentoDTO;
import com.dev.taskflow.models.entities.Departamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel="spring")
public interface DepartamentoMapper {

    DepartamentoDTO toDepartamentoDTO(Departamento departamento);
    Departamento toDepartamentoEntity(DepartamentoDTO departamentoDTO);

    @Mapping(target = "id", ignore = true)
    void updateDepartamentoFromDTO(DepartamentoDTO departamentoDTO, @MappingTarget Departamento departamento);

}
