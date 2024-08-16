package com.dev.taskflow.services;

import com.dev.taskflow.factory.DepartamentoFactory;
import com.dev.taskflow.factory.PessoaFactory;
import com.dev.taskflow.factory.PessoasComHorasProjectionFactory;
import com.dev.taskflow.models.dto.PessoaDTO;
import com.dev.taskflow.models.entities.Departamento;
import com.dev.taskflow.models.entities.Pessoa;
import com.dev.taskflow.projections.PessoaMediaHorasGastas;
import com.dev.taskflow.projections.PessoasComHorasProjection;
import com.dev.taskflow.repositories.DepartamentoRepository;
import com.dev.taskflow.repositories.PessoaRepository;
import com.dev.taskflow.services.exceptions.ResourceNotFoundException;
import com.dev.taskflow.services.mappers.PessoaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    private Long existingIdPessoa;
    private Long departamentoIdExisting;
    private Long NonDepartamentoIdExisting;
    private Long NonExistingIdPessoa;
    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;
    private List<PessoasComHorasProjection> projection;
    private Departamento departamento;
    private String nomePessoa;
    private String dataInicial;
    private String dataFinal;
    private PessoaMediaHorasGastas pessoaMediaHorasGastas;

    @BeforeEach
    void SetUp() throws Exception {
        existingIdPessoa = 1L;
        NonExistingIdPessoa = 20L;
        departamentoIdExisting = 4L;
        NonDepartamentoIdExisting = 6L;
        departamento = DepartamentoFactory.createDepartamento();
        pessoa = PessoaFactory.createPessoa();
        pessoaDTO = PessoaFactory.createPessoaDTO();
        projection = Arrays.asList(PessoasComHorasProjectionFactory.createDefaultPessoasComHorasProjection());
        nomePessoa = "Ana Silva";
        dataInicial = "2024-08-30";
        dataFinal = "2024-09-12";
        pessoaMediaHorasGastas = PessoaFactory.createPessoasComHorasProjection(nomePessoa, 45.0);
    }

    @Test
    void buscarPessoaMediaHorasGastas() {
        when(pessoaRepository.buscarPessoaMediaHorasGastas(nomePessoa, dataInicial, dataFinal)).thenReturn(pessoaMediaHorasGastas);

        PessoaMediaHorasGastas result = pessoaService.buscarPessoaMediaHorasGastas(nomePessoa, dataInicial, dataFinal);

        assertNotNull(result);
    }

    @Test
    void buscarTodasPessoasShouldReturnList() {
        when(pessoaRepository.findPessoasComHoras()).thenReturn(projection);
        List<PessoasComHorasProjection> result = pessoaService.buscarTodasPessoas();
        assertNotNull(result);

    }

    @Test
    void buscarPessoaPorIdShouldReturnPessoaDTO() {
        when(pessoaRepository.findById(existingIdPessoa)).thenReturn(Optional.of(pessoa));
        when(pessoaMapper.toPessoaDTO(pessoa)).thenReturn(pessoaDTO);
        PessoaDTO result = pessoaService.buscarPessoaPorId(existingIdPessoa);
        assertNotNull(result);
    }

    @Test
    void buscarPessoaPorIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExisting() {
        when(pessoaRepository.findById(NonExistingIdPessoa)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.buscarPessoaPorId(NonExistingIdPessoa);
        });
    }

    @Test
    void inserirPessoaShouldReturnPessoaDTO() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("João Santos");
        pessoa.setDepartamento(departamento);

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("João Santos");
        pessoaDTO.setDepartamentoId(departamentoIdExisting);
        pessoa.setDepartamento(departamento);

        when(pessoaMapper.toPessoaEntity(pessoaDTO)).thenReturn(pessoa);
        when(departamentoRepository.findById(departamentoIdExisting)).thenReturn(Optional.of(departamento));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(pessoaMapper.toPessoaDTO(pessoa)).thenReturn(pessoaDTO);

        PessoaDTO result = pessoaService.inserirPessoa(pessoaDTO);

        assertNotNull(result);

    }

    @Test
    void inserirPessoaShouldThrowResourceNotFoundExceptionWhenDepartamentoNotFound() {
        pessoaDTO.setDepartamentoId(NonDepartamentoIdExisting);
        when(departamentoRepository.findById(NonDepartamentoIdExisting)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.inserirPessoa(pessoaDTO);
        });
    }

    @Test
    void alterarPessoaShouldReturnUpdatedPessoaDTO() {

        Long pessoaId = 1L;
        Long departamentoId = 2L;

        Pessoa pessoaEntity = new Pessoa();
        pessoaEntity.setId(pessoaId);
        pessoaEntity.setNome("João");

        Departamento departamento = new Departamento();
        departamento.setId(departamentoId);
        departamento.setNome("FInanceiro");

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(pessoaId);
        pessoaDTO.setNome("João Neto");
        pessoaDTO.setDepartamentoId(departamentoId);

        Pessoa updatedPessoaEntity = new Pessoa();
        updatedPessoaEntity.setId(pessoaId);
        updatedPessoaEntity.setNome("João");
        updatedPessoaEntity.setDepartamento(departamento);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaEntity));
        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(departamento));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(updatedPessoaEntity);
        when(pessoaMapper.toPessoaDTO(updatedPessoaEntity)).thenReturn(pessoaDTO);
        doNothing().when(pessoaMapper).updatePessoaFromDTO(pessoaDTO, pessoa);

        PessoaDTO result = pessoaService.alterarPessoa(existingIdPessoa, pessoaDTO);

        assertEquals(pessoaDTO.getId(), result.getId());
        assertEquals(pessoaDTO.getNome(), result.getNome());
        assertEquals(pessoaDTO.getDepartamentoId(), result.getDepartamentoId());
    }

    @Test
    void alterarPessoaShouldUpdatePessoaWithoutDepartamento() {
        Long pessoaId = 1L;
        Pessoa pessoaEntity = new Pessoa();
        pessoaEntity.setId(pessoaId);
        pessoaEntity.setNome("João");

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(pessoaId);
        pessoaDTO.setNome("João Neto");
        pessoaDTO.setDepartamentoId(null);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaEntity));
        when(pessoaRepository.save(pessoaEntity)).thenReturn(pessoaEntity);

        when(pessoaMapper.toPessoaDTO(pessoaEntity)).thenReturn(pessoaDTO);

        doNothing().when(pessoaMapper).updatePessoaFromDTO(pessoaDTO, pessoaEntity);
        PessoaDTO result = pessoaService.alterarPessoa(pessoaId, pessoaDTO);

        assertNotNull(result);
        assertEquals(pessoaDTO.getId(), result.getId());
        assertEquals(pessoaDTO.getNome(), result.getNome());
        assertNull(result.getDepartamentoId());

        verify(pessoaMapper).updatePessoaFromDTO(pessoaDTO, pessoaEntity);
    }

    @Test
    void alterarPessoaShouldThrowResourceNotFoundExceptionWhenPessoaNotFound() {
        Long pessoaId = 1L;
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(pessoaId);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.alterarPessoa(pessoaId, pessoaDTO);
        });
        assertEquals("Id não encontrado: " + pessoaId, thrown.getMessage());
    }

    @Test
    void alterarPessoaShouldThrowResourceNotFoundExceptionWhenDepartamentoNotFound() {
        Long pessoaId = 1L;
        Long departamentoId = 2L;
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(pessoaId);
        pessoaDTO.setDepartamentoId(departamentoId);

        Pessoa pessoaEntity = new Pessoa();
        pessoaEntity.setId(pessoaId);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaEntity));
        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.alterarPessoa(pessoaId, pessoaDTO);
        });
        assertEquals("Departamento não encontrado para o ID: " + departamentoId, thrown.getMessage());
    }

    @Test
    void alterarPessoaShouldThrowExceptionWhenSavingFails() {
        Long pessoaId = 1L;
        Long departamentoId = 2L;
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(pessoaId);
        pessoaDTO.setDepartamentoId(departamentoId);

        Pessoa pessoaEntity = new Pessoa();
        pessoaEntity.setId(pessoaId);
        Departamento departamento = new Departamento();
        departamento.setId(departamentoId);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaEntity));
        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(departamento));
        when(pessoaRepository.save(any(Pessoa.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            pessoaService.alterarPessoa(pessoaId, pessoaDTO);
        });
        assertEquals("Erro ao salvar", thrown.getMessage());
    }

    @Test
    void removerPessoaShouldDoNothingWhenIdExists () {
        when(pessoaRepository.findById(existingIdPessoa)).thenReturn(Optional.of(pessoa));
        assertDoesNotThrow(() -> {
            pessoaService.removerPessoa(existingIdPessoa);
        });
        verify(pessoaRepository, times(1)).deleteById(existingIdPessoa);
    }

    @Test
    void deletePhoneNumberShouldThrowResourceNotFoundExceptionWhenIdPhoneDoesNotExists () {
        when(pessoaRepository.findById(NonExistingIdPessoa)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.removerPessoa(NonExistingIdPessoa);
        });
        verify(pessoaRepository, times(1)).findById(NonExistingIdPessoa);
    }
}
