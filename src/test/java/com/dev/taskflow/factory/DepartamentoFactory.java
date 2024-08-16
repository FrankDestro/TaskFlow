package com.dev.taskflow.factory;

import com.dev.taskflow.models.dto.DepartamentoDTO;
import com.dev.taskflow.models.entities.Departamento;

public class DepartamentoFactory {

    public static Departamento createDepartamento() {
        Departamento departamento = new Departamento(1L, "Financeiro", null, null);
        return departamento;
    }

    public static DepartamentoDTO createDepartamentoDTO() {
        DepartamentoDTO  departamentoDTO = new DepartamentoDTO(1L, "Financeiro", 2, 3);
        return departamentoDTO;
    }

}
