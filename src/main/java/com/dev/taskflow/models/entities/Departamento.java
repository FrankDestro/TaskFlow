package com.dev.taskflow.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "tb_departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "departamento")
    private List<Pessoa> pessoas = new ArrayList<>();

    @OneToMany(mappedBy = "departamento")
    private List<Tarefa> tarefas = new ArrayList<>();

    public int getQuantidadePessoas() {
        return pessoas.size();
    }

    public int getQuantidadeTarefas() {
        return tarefas.size();
    }
}
