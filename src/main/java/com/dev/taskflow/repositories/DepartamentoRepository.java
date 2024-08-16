package com.dev.taskflow.repositories;

import com.dev.taskflow.models.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository <Departamento, Long> {
    
}
