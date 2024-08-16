package com.dev.taskflow.controllers;


import com.dev.taskflow.models.dto.PessoaDTO;
import com.dev.taskflow.projections.PessoaMediaHorasGastas;
import com.dev.taskflow.projections.PessoasComHorasProjection;
import com.dev.taskflow.services.PessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping("/gastos")
    public ResponseEntity<PessoaMediaHorasGastas> buscarPessoasPorNomeEPeriodo(
            @RequestParam String nome,
            @RequestParam String periodoInicial,
            @RequestParam String periodoFinal) {

        PessoaMediaHorasGastas pessoa =
                pessoaService.
                        buscarPessoaMediaHorasGastas
                                (nome, periodoInicial, periodoFinal);
        return ResponseEntity.ok().body(pessoa);

    }

    @GetMapping
    public ResponseEntity<List<PessoasComHorasProjection>> buscarTodasPessoas(Pageable pageable) {
        List<PessoasComHorasProjection> listaPessoas = pessoaService.buscarTodasPessoas();
        return ResponseEntity.ok().body(listaPessoas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> buscarPessoaPorId(@PathVariable Long id) {
        PessoaDTO dto = pessoaService.buscarPessoaPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> inserirPessoa(@RequestBody PessoaDTO dto) {
        dto = pessoaService.inserirPessoa(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> UpdateTask(@PathVariable Long id, @RequestBody PessoaDTO dto) {
        dto = pessoaService.alterarPessoa(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removerPessoa(@PathVariable Long id) {
        pessoaService.removerPessoa(id);
        return ResponseEntity.noContent().build();
    }
}
