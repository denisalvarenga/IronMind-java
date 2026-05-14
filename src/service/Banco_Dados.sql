-- ================= LIMPEZA =================
DROP TABLE IF EXISTS frequencia CASCADE;
DROP TABLE IF EXISTS inscricao CASCADE;
DROP TABLE IF EXISTS aula CASCADE;
DROP TABLE IF EXISTS instrutor CASCADE;
DROP TABLE IF EXISTS aluno CASCADE;
DROP TABLE IF EXISTS plano CASCADE;

-- ================= PLANO =================
CREATE TABLE plano (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valor_mensal NUMERIC(10,2) NOT NULL CHECK (valor_mensal >= 0),
    beneficios TEXT,
    duracao_meses INT NOT NULL CHECK (duracao_meses > 0)
);

-- ================= ALUNO =================
CREATE TABLE aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    data_nascimento DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    data_matricula DATE NOT NULL,
    plano_id INT NOT NULL,
    FOREIGN KEY (plano_id) REFERENCES plano(id)
);

-- ================= INSTRUTOR =================
CREATE TABLE instrutor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) UNIQUE,
    telefone VARCHAR(20),
    especialidade VARCHAR(100),
    horario_trabalho VARCHAR(100)
);

-- ================= AULA =================
CREATE TABLE aula (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    duracao INT NOT NULL CHECK (duracao > 0),
    horario TIMESTAMP NOT NULL,
    capacidade INT NOT NULL CHECK (capacidade > 0),
    instrutor_id INT NOT NULL,
    FOREIGN KEY (instrutor_id) REFERENCES instrutor(id)
);

-- ================= INSCRICAO =================
CREATE TABLE inscricao (
    id SERIAL PRIMARY KEY,
    aluno_id INT NOT NULL,
    aula_id INT NOT NULL,
    data_inscricao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    FOREIGN KEY (aula_id) REFERENCES aula(id) ON DELETE CASCADE,
    UNIQUE (aluno_id, aula_id)
);

-- ================= FREQUENCIA =================
CREATE TABLE frequencia (
    id SERIAL PRIMARY KEY,
    inscricao_id INT NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (inscricao_id) REFERENCES inscricao(id) ON DELETE CASCADE
);

-- ================= ÍNDICES =================
CREATE INDEX idx_aluno_plano ON aluno(plano_id);
CREATE INDEX idx_aula_instrutor ON aula(instrutor_id);
CREATE INDEX idx_inscricao_aluno ON inscricao(aluno_id);
CREATE INDEX idx_inscricao_aula ON inscricao(aula_id);
CREATE INDEX idx_frequencia_inscricao ON frequencia(inscricao_id);

-- ================= FUNÇÃO (REGRA DE NEGÓCIO) =================
CREATE OR REPLACE FUNCTION validar_inscricao(aluno_id_param INT, aula_id_param INT)
RETURNS TEXT AS
$$
DECLARE
    data_matricula DATE;
    duracao INT;
    data_vencimento DATE;

    capacidade_aula INT;
    total_inscritos INT;

    novo_inicio TIMESTAMP;
    novo_fim TIMESTAMP;

    conflito RECORD;
BEGIN

    -- ================= VALIDAR PLANO =================
    SELECT a.data_matricula, p.duracao_meses
    INTO data_matricula, duracao
    FROM aluno a
    JOIN plano p ON a.plano_id = p.id
    WHERE a.id = aluno_id_param;

    IF data_matricula IS NULL THEN
        RETURN 'Aluno não encontrado';
    END IF;

    data_vencimento := data_matricula + (duracao || ' months')::interval;

    IF CURRENT_DATE > data_vencimento THEN
        RETURN 'Plano vencido em ' || data_vencimento;
    END IF;

    -- ================= VALIDAR CAPACIDADE =================
    SELECT capacidade INTO capacidade_aula
    FROM aula
    WHERE id = aula_id_param;

    IF capacidade_aula IS NULL THEN
        RETURN 'Aula não encontrada';
    END IF;

    SELECT COUNT(*) INTO total_inscritos
    FROM inscricao
    WHERE aula_id = aula_id_param;

    IF total_inscritos >= capacidade_aula THEN
        RETURN 'Aula lotada (' || total_inscritos || '/' || capacidade_aula || ')';
    END IF;

    -- ================= VALIDAR CONFLITO =================
    SELECT horario, (horario + (duracao || ' minutes')::interval)
    INTO novo_inicio, novo_fim
    FROM aula
    WHERE id = aula_id_param;

    FOR conflito IN
        SELECT au.nome, au.horario, au.duracao
        FROM inscricao i
        JOIN aula au ON i.aula_id = au.id
        WHERE i.aluno_id = aluno_id_param
    LOOP

        IF novo_inicio < (conflito.horario + (conflito.duracao || ' minutes')::interval)
           AND novo_fim > conflito.horario THEN
            RETURN 'Conflito com a aula: ' || conflito.nome;
        END IF;

    END LOOP;

    RETURN 'OK';

END;
$$ LANGUAGE plpgsql;

-- ================= VIEW (RELATÓRIO) =================
CREATE OR REPLACE VIEW vw_alunos_detalhes AS
SELECT 
    a.id,
    a.nome,
    p.nome AS plano,
    (a.data_matricula + (p.duracao_meses || ' months')::interval) AS data_vencimento,
    CASE 
        WHEN CURRENT_DATE > (a.data_matricula + (p.duracao_meses || ' months')::interval)
        THEN 'VENCIDO'
        ELSE 'ATIVO'
    END AS status_plano,
    COUNT(f.id) AS total_visitas,
    MAX(f.data_hora) AS ultima_visita,
    COUNT(DISTINCT i.aula_id) AS total_aulas
FROM aluno a
JOIN plano p ON a.plano_id = p.id
LEFT JOIN inscricao i ON a.id = i.aluno_id
LEFT JOIN frequencia f ON i.id = f.inscricao_id
GROUP BY a.id, p.nome, p.duracao_meses;