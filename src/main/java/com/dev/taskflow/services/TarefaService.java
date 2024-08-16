package com.dev.taskflow.services;

import com.dev.taskflow.models.dto.HorasDTO;
import com.dev.taskflow.models.dto.TarefaDTO;
import com.dev.taskflow.models.entities.Pessoa;
import com.dev.taskflow.models.entities.Tarefa;
import com.dev.taskflow.repositories.PessoaRepository;
import com.dev.taskflow.repositories.TarefaRepository;
import com.dev.taskflow.services.exceptions.DepartamentoMismatchException;
import com.dev.taskflow.services.exceptions.ResourceNotFoundException;
import com.dev.taskflow.services.mappers.TarefaMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TarefaService {

    private final PessoaRepository pessoaRepository;
    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;

    @Transactional
    public TarefaDTO adicionarTarefa(TarefaDTO dto) {
        Tarefa tarefaEntity = tarefaMapper.toTarefaEntity(dto);
        tarefaEntity.setFinalizada(false);
        tarefaEntity = tarefaRepository.save(tarefaEntity);
        return tarefaMapper.toTarefaDTO(tarefaEntity);
    }

    @Transactional
    public TarefaDTO finalizarTarefa(Long id, HorasDTO horasDTO) {

        try {
            Tarefa tarefaEntity = tarefaRepository.getReferenceById(id);
            tarefaEntity.setFinalizada(true);
            tarefaEntity.setDuracao(horasDTO.getHoras());
            tarefaEntity = tarefaRepository.save(tarefaEntity);
            return tarefaMapper.toTarefaDTO(tarefaEntity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id de tarefa não encontrado " + id);
        }
    }

    @Transactional
    public TarefaDTO alocarPessoaNaTarefa(Long idTarefa, Long idPessoa) {

        Tarefa tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));

        Pessoa pessoa = pessoaRepository.findByIdWithDepartamento(idPessoa)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        Long departamentoTarefaId = tarefa.getDepartamento().getId();
        Long departamentoPessoaId = pessoa.getDepartamento().getId();

        if (!departamentoTarefaId.equals(departamentoPessoaId)) {
            throw new DepartamentoMismatchException("A pessoa e a tarefa não estão no mesmo departamento");
        }

        tarefa.setPessoa(pessoa);
        tarefa = tarefaRepository.save(tarefa);
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    @Transactional(readOnly = true)
    public List<TarefaDTO> buscarTarefasNaoAlocadas() {
        List<Tarefa> list = tarefaRepository.findTarefasNaoAlocadas();
        return list.stream().map(tarefas -> tarefaMapper.toTarefaDTO(tarefas)).toList();
    }

    @Transactional(readOnly = true)
    public TarefaDTO buscarTarefaPorId(Long id) {
        Optional<Tarefa> obj = tarefaRepository.findById(id);
        Tarefa entity = obj.orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
        return tarefaMapper.toTarefaDTO(entity);
    }
}
