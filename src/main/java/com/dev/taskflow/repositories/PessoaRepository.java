package com.dev.taskflow.repositories;

import com.dev.taskflow.models.dto.PessoaDTO;
import com.dev.taskflow.models.entities.Pessoa;
import com.dev.taskflow.projections.PessoaMediaHorasGastas;
import com.dev.taskflow.projections.PessoasComHorasProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p JOIN FETCH p.departamento WHERE p.id = :id")
    Optional<Pessoa> findByIdWithDepartamento(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
            SELECT  p.id as Id, p.nome AS nome, d.nome AS departamento,
                   COALESCE(SUM(t.duracao), 0) AS totalHoras
            FROM tb_pessoa p
            LEFT JOIN tb_departamento d ON p.departamento_id = d.id
            LEFT JOIN tb_tarefa t ON p.id = t.pessoa_id
            GROUP BY p.id, p.nome, d.nome;
            """)
    List<PessoasComHorasProjection> findPessoasComHoras();


    @Query(nativeQuery = true, value = """ 
            SELECT
                p.nome AS Nome,
                ROUND(AVG(t.duracao)::numeric, 2) AS mediaHorasGastas
            FROM
                tb_tarefa t
            JOIN
                tb_pessoa p ON t.pessoa_id = p.id
            WHERE
                p.nome = :nome
            AND t.prazo BETWEEN CAST(:periodoInicial AS TIMESTAMP) AND CAST(:periodoFinal AS TIMESTAMP)
                GROUP BY
                p.nome;
            """)
    PessoaMediaHorasGastas buscarPessoaMediaHorasGastas(
            @Param("nome") String nome,
            @Param("periodoInicial") String dataInicial,
            @Param("periodoFinal") String dataFinal);

}
