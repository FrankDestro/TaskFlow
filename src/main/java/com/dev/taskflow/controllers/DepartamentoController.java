package com.dev.taskflow.controllers;

import com.dev.taskflow.models.dto.DepartamentoDTO;
import com.dev.taskflow.services.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> listarDepartamentosComQuantidadePessoasETarefas() {
        List<DepartamentoDTO> departamentos = departamentoService.buscarTodosDepartamentos();
        return ResponseEntity.ok(departamentos);
    }


}
