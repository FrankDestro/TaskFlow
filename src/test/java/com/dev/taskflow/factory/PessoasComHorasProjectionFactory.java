package com.dev.taskflow.factory;

import com.dev.taskflow.projections.PessoasComHorasProjection;

public class PessoasComHorasProjectionFactory {

    public static PessoasComHorasProjection createPessoasComHorasProjection(String pessoaNome, String departamentoNome, Integer totalHoras) {
        return new PessoasComHorasProjection() {

            @Override
            public Integer getId() {
                return 0;
            }

            @Override
            public String getNome() {
                return "";
            }

            @Override
            public String getDepartamento() {
                return "";
            }

            @Override
            public Integer getTotalHoras() {
                return totalHoras;
            }
        };
    }

    public static PessoasComHorasProjection createDefaultPessoasComHorasProjection() {
        return createPessoasComHorasProjection("Default Name", "Default Department", 20);
    }
}

