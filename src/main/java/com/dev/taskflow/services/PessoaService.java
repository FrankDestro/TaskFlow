package com.dev.taskflow.services;


import com.dev.taskflow.models.dto.PessoaDTO;
import com.dev.taskflow.models.entities.Departamento;
import com.dev.taskflow.models.entities.Pessoa;
import com.dev.taskflow.projections.PessoaMediaHorasGastas;
import com.dev.taskflow.projections.PessoasComHorasProjection;
import com.dev.taskflow.repositories.DepartamentoRepository;
import com.dev.taskflow.repositories.PessoaRepository;
import com.dev.taskflow.services.exceptions.ResourceNotFoundException;
import com.dev.taskflow.services.mappers.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;
    private final DepartamentoRepository departamentoRepository;

    @Transactional(readOnly = true)
    public PessoaMediaHorasGastas buscarPessoaMediaHorasGastas(String nome, String periodoInicial, String periodoFinal) {

        PessoaMediaHorasGastas pessoa = pessoaRepository.buscarPessoaMediaHorasGastas(nome, periodoInicial, periodoFinal);
        return pessoa;
    }

    @Transactional(readOnly = true)
    public List<PessoasComHorasProjection> buscarTodasPessoas() {
        List<PessoasComHorasProjection> projections = pessoaRepository.findPessoasComHoras();
        return projections;
    }

    @Transactional(readOnly = true)
    public PessoaDTO buscarPessoaPorId(Long id) {
        Optional<Pessoa> obj = pessoaRepository.findById(id);
        Pessoa entity = obj.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        return pessoaMapper.toPessoaDTO(entity);
    }

    @Transactional
    public PessoaDTO inserirPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaMapper.toPessoaEntity(pessoaDTO);

        Departamento departamento = departamentoRepository.findById(pessoaDTO.getDepartamentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento not found"));
        pessoa.setDepartamento(departamento);

        pessoa = pessoaRepository.save(pessoa);
        return pessoaMapper.toPessoaDTO(pessoa);
    }

    @Transactional
    public PessoaDTO alterarPessoa(Long id, PessoaDTO dto) {
        Pessoa pessoaEntity = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));
        if (dto.getDepartamentoId() != null) {
            Departamento departamento = departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado para o ID: " + dto.getDepartamentoId()));
            Pessoa novaPessoaEntity = new Pessoa();
            novaPessoaEntity.setId(pessoaEntity.getId());
            novaPessoaEntity.setNome(dto.getNome());
            novaPessoaEntity.setDepartamento(departamento);
            novaPessoaEntity.setTarefas(pessoaEntity.getTarefas());

            pessoaEntity = pessoaRepository.save(novaPessoaEntity);
        } else {
            pessoaMapper.updatePessoaFromDTO(dto, pessoaEntity);
            pessoaEntity = pessoaRepository.save(pessoaEntity);
        }
        return pessoaMapper.toPessoaDTO(pessoaEntity);
    }

    @Transactional
    public void removerPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id não encontrado: " + id));
        pessoaRepository.deleteById(id);
    }
}
