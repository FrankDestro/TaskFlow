package com.dev.taskflow.services;

import com.dev.taskflow.factory.DepartamentoFactory;
import com.dev.taskflow.factory.PessoaFactory;
import com.dev.taskflow.factory.PessoasComHorasProjectionFactory;
import com.dev.taskflow.factory.TarefaFactory;
import com.dev.taskflow.models.dto.HorasDTO;
import com.dev.taskflow.models.dto.TarefaDTO;
import com.dev.taskflow.models.entities.Departamento;
import com.dev.taskflow.models.entities.Pessoa;
import com.dev.taskflow.models.entities.Tarefa;
import com.dev.taskflow.repositories.PessoaRepository;
import com.dev.taskflow.repositories.TarefaRepository;
import com.dev.taskflow.services.exceptions.DepartamentoMismatchException;
import com.dev.taskflow.services.exceptions.ResourceNotFoundException;
import com.dev.taskflow.services.mappers.TarefaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TarefaServiceTest {


    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private TarefaMapper tarefaMapper;

    @Mock
    private PessoaRepository pessoaRepository;

    private Long tarefaId;
    private Long NonExistingTarefaId;
    private Tarefa tarefa;
    private TarefaDTO tarefaDTO;
    private Long idPessoa;
    private Long idPessoaNaoExistente;
    private Pessoa pessoa;
    private Departamento departamento;
    private List<Tarefa> tarefas;
    HorasDTO horasDTO = new HorasDTO();


    @BeforeEach
    void Setup() {
        tarefaId = 1L;
        NonExistingTarefaId = 10L;
        idPessoa = 3L;
        idPessoaNaoExistente = 6L;
        tarefa = TarefaFactory.createTarefa();
        tarefaDTO = TarefaFactory.createTarefaDTO();
        pessoa = PessoaFactory.createPessoa();
        departamento = DepartamentoFactory.createDepartamento();
        tarefas = Arrays.asList(TarefaFactory.createTarefa());
        horasDTO.setHoras(40);

    }

    @Test
    void adicionarTarefaShouldReturnTarefaDTO() {
        when(tarefaMapper.toTarefaEntity(tarefaDTO)).thenReturn(tarefa);
        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);
        when(tarefaMapper.toTarefaDTO(tarefa)).thenReturn(tarefaDTO);

        TarefaDTO result = tarefaService.adicionarTarefa(tarefaDTO);
        assertNotNull(result);
    }

    @Test
    void finalizarTarefaShouldReturnTarefaDTO() {
        when(tarefaRepository.getReferenceById(tarefaId)).thenReturn(tarefa);
        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);
        when(tarefaMapper.toTarefaDTO(tarefa)).thenReturn(tarefaDTO);

        TarefaDTO result = tarefaService.finalizarTarefa(tarefaId, horasDTO);

        assertNotNull(result);
    }

    @Test
    void finalizarTarefaShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        when(tarefaRepository.getReferenceById(NonExistingTarefaId)).thenReturn(null);
        doThrow(EntityNotFoundException.class).when(tarefaRepository).getReferenceById(NonExistingTarefaId);

        assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.finalizarTarefa(NonExistingTarefaId, horasDTO);
        });
    }

    @Test
    void  alocarPessoaNaTarefaShouldReturnTarefaDTO() {

        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(pessoaRepository.findByIdWithDepartamento(idPessoa)).thenReturn(Optional.of(pessoa));
        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);
        when(tarefaMapper.toTarefaDTO(tarefa)).thenReturn(tarefaDTO);

        TarefaDTO result = tarefaService.alocarPessoaNaTarefa(tarefaId, idPessoa);

        assertNotNull(result);
    }

    @Test
    void  alocarPessoaNaTarefaDeveLancarResourceNotFoundExceptionIdTarefaNaoExistir() {
        when(tarefaRepository.findById(NonExistingTarefaId)).thenReturn(null);
        doThrow(ResourceNotFoundException.class).when(tarefaRepository).findById(NonExistingTarefaId);

        assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.alocarPessoaNaTarefa(NonExistingTarefaId, idPessoa);
        });

    }

    @Test
    void alocarPessoaNaTarefaDeveLancarResourceNotFoundExceptionIdPessoaNaoExistir() {

        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(pessoaRepository.findByIdWithDepartamento(idPessoaNaoExistente)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.alocarPessoaNaTarefa(tarefaId, idPessoaNaoExistente);
        });

        verify(pessoaRepository, times(1)).findByIdWithDepartamento(idPessoaNaoExistente);
    }

    @Test
    void  alocarPessoaNaTarefaDeveLancarResourceNotFoundExceptionQuandoIdDepartamentoDiferenteIdDepartamentoPessoa() {
        departamento.setId(1L);
        Departamento departamento2 = DepartamentoFactory.createDepartamento();
        departamento2.setId(2L);
        pessoa.setDepartamento(departamento2);

        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(pessoaRepository.findByIdWithDepartamento(idPessoa)).thenReturn(Optional.of(pessoa));

        doThrow(DepartamentoMismatchException.class).when(tarefaRepository).findById(NonExistingTarefaId);

        assertThrows(DepartamentoMismatchException.class, () -> {
            tarefaService.alocarPessoaNaTarefa(tarefaId, idPessoa);
        });
    }

    @Test
    void buscarTarefasNaoAlocadasDeveRetornarTarefaDTO() {
        when(tarefaRepository.findTarefasNaoAlocadas()).thenReturn(tarefas);
        when(tarefaMapper.toTarefaDTO(tarefa)).thenReturn(tarefaDTO);

        List<TarefaDTO> result = tarefaService.buscarTarefasNaoAlocadas();

        assertNotNull(result);
    }

    @Test
    void buscarTarefaPorIdDeveRetornarTarefaDTO() {
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(tarefaMapper.toTarefaDTO(tarefa)).thenReturn(tarefaDTO);

        TarefaDTO result = tarefaService.buscarTarefaPorId(tarefaId);

        assertNotNull(result);
    }

    @Test
    void buscarTarefaPorIdDeveLancarResourceNotFoundExceptionQuandoIdTarefaNaoExistir() {
        when(tarefaRepository.findById(NonExistingTarefaId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.buscarTarefaPorId(NonExistingTarefaId);
        });
        verify(tarefaRepository, times(1)).findById(NonExistingTarefaId);
    }
}
