package com.dev.taskflow.services;

import com.dev.taskflow.factory.DepartamentoFactory;
import com.dev.taskflow.models.dto.DepartamentoDTO;
import com.dev.taskflow.models.entities.Departamento;
import com.dev.taskflow.repositories.DepartamentoRepository;
import com.dev.taskflow.services.mappers.DepartamentoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DepartamentoServiceTest {

    @InjectMocks
    private DepartamentoService departamentoService;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private DepartamentoMapper departamentoMapper;

    private Departamento departamento;
    private DepartamentoDTO departamentoDTO;
    private List<Departamento> departamentos;

    @BeforeEach
    void Setup() {
        departamento = DepartamentoFactory.createDepartamento();
        departamentoDTO = DepartamentoFactory.createDepartamentoDTO();
        departamentos = Arrays.asList(departamento);
    }


    @Test
    void buscarTodosDepartamentosShouldReturnListDepartamentoDTO() {
        when(departamentoRepository.findAll()).thenReturn(departamentos);
        when(departamentoMapper.toDepartamentoDTO(departamento)).thenReturn(departamentoDTO);

        List<DepartamentoDTO> result = departamentoService.buscarTodosDepartamentos();

        assertNotNull(result);
    }
}