package com.dev.taskflow.repositories;

import com.dev.taskflow.models.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository <Tarefa, Long> {

    @Query(nativeQuery = true, value = """
            SELECT *
              FROM tb_tarefa
              WHERE pessoa_id IS NULL
              ORDER BY prazo ASC
              LIMIT 3;
            """)
    List<Tarefa> findTarefasNaoAlocadas();
}
