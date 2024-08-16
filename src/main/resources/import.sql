INSERT INTO tb_departamento (nome) VALUES ('Recursos Humanos');
INSERT INTO tb_departamento (nome) VALUES ('Tecnologia');
INSERT INTO tb_departamento (nome) VALUES ('Financeiro');
INSERT INTO tb_departamento (nome) VALUES ('Marketing');
INSERT INTO tb_departamento (nome) VALUES ('Jurídico');

INSERT INTO tb_pessoa (nome, departamento_id) VALUES ('Ana Silva', 1);
INSERT INTO tb_pessoa (nome, departamento_id) VALUES ('João Santos', 2);
INSERT INTO tb_pessoa (nome, departamento_id) VALUES ('Maria Oliveira', 3);
INSERT INTO tb_pessoa (nome, departamento_id) VALUES ('Carlos Pereira', 4);
INSERT INTO tb_pessoa (nome, departamento_id) VALUES ('Fernanda Lima', 5);

INSERT INTO tb_tarefa (titulo, descricao, prazo, duracao, finalizada, departamento_id, pessoa_id) VALUES ('Recrutar novo funcionário', 'Recrutamento para a vaga de desenvolvedor', '2024-08-30 10:00:00', 100, true, 1, 1);
INSERT INTO tb_tarefa (titulo, descricao, prazo, duracao, finalizada, departamento_id, pessoa_id) VALUES ('Desenvolver nova funcionalidade', 'Implementar novo módulo no sistema', '2024-09-15 17:00:00', 120, true, 2, 2);
INSERT INTO tb_tarefa (titulo, descricao, prazo, duracao, finalizada, departamento_id, pessoa_id) VALUES ('Revisar relatórios financeiros', 'Analisar relatórios do último trimestre', '2024-08-25 15:00:00', 180, true, 3, 3);
INSERT INTO tb_tarefa (titulo, descricao, prazo, duracao, finalizada, departamento_id, pessoa_id) VALUES ('Campanha de marketing digital', 'Criar campanha para novo produto', '2024-09-10 12:00:00', 50, false, 3, 1);
INSERT INTO tb_tarefa (titulo, descricao, prazo, duracao, finalizada, departamento_id, pessoa_id) VALUES ('Atualização de contratos', 'Revisar e atualizar contratos legais', '2024-09-05 09:00:00', 40, false, 5, 1);

INSERT INTO tb_tarefa (titulo, descricao, prazo, duracao, finalizada, departamento_id, pessoa_id) VALUES ('Planejamento de projeto', 'Definir os requisitos e plano para o novo projeto', '2024-08-20 10:00:00', 0, false, 2, null);
INSERT INTO tb_tarefa (titulo, descricao, prazo, duracao, finalizada, departamento_id, pessoa_id) VALUES ('Revisão de procedimentos internos', 'Atualizar procedimentos internos da empresa', '2024-08-28 11:00:00', 0, false, 5, null);
INSERT INTO tb_tarefa (titulo, descricao, prazo, duracao, finalizada, departamento_id, pessoa_id) VALUES ('Treinamento de equipe', 'Realizar treinamento para a equipe de vendas', '2024-09-02 14:00:00', 0, false, 1, null);


--http://localhost:8080/swagger-ui/index.html