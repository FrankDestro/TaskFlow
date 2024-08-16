package com.dev.taskflow.controllers;

import com.dev.taskflow.models.dto.HorasDTO;
import com.dev.taskflow.models.dto.TarefaDTO;
import com.dev.taskflow.services.TarefaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDTO> inserirPessoa(@RequestBody TarefaDTO dto) {
        dto = tarefaService.adicionarTarefa(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/finalizar/{id}")
    public ResponseEntity<TarefaDTO> finalizarTarefa(@PathVariable Long id,  @RequestBody HorasDTO horasDTO) {
        TarefaDTO dto = tarefaService.finalizarTarefa(id, horasDTO);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/alocar/{idTarefa}")
    public ResponseEntity<TarefaDTO> alocarPessoaTarefa(@PathVariable Long idTarefa, @RequestBody Long idPessoa) {
        TarefaDTO dto = tarefaService.alocarPessoaNaTarefa(idTarefa, idPessoa);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> buscarTarefasNaoAlocadas() {
        List<TarefaDTO> listaPessoas = tarefaService.buscarTarefasNaoAlocadas();
        return ResponseEntity.ok().body(listaPessoas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TarefaDTO> buscarTarefaPorId(@PathVariable Long id) {
        TarefaDTO dto = tarefaService.buscarTarefaPorId(id);
        return ResponseEntity.ok(dto);
    }
}
