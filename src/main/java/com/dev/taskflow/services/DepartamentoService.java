package com.dev.taskflow.services;

import com.dev.taskflow.models.dto.DepartamentoDTO;
import com.dev.taskflow.models.entities.Departamento;
import com.dev.taskflow.repositories.DepartamentoRepository;
import com.dev.taskflow.services.mappers.DepartamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final DepartamentoMapper departamentoMapper;

    @Transactional(readOnly = true)
    public List<DepartamentoDTO> buscarTodosDepartamentos() {
        List<Departamento> list = departamentoRepository.findAll();
        return list.stream().map(departamento -> departamentoMapper.toDepartamentoDTO(departamento)).toList();
    }
}
