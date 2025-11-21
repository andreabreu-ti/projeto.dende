-- Flyway Migration V1__create_tables_inicial.sql
-- Objetivo: Criar as tabelas iniciais (cad_usuario, cad_grupoacesso, cad_grupousuario)
-- e inserir dados básicos.
-- Banco de Dados: SQL Server

--------------------------------------------------------------------------------
-- 1. Criação da Tabela cad_usuario (Classe Usuario)
--------------------------------------------------------------------------------
CREATE TABLE cad_usuario (
    -- Chave Primária, IDENTITY para auto-incremento
    id_usuario INT PRIMARY KEY IDENTITY(1,1),
    -- Colunas da entidade Usuario
    nome_usuario VARCHAR(50) NOT NULL UNIQUE,
    nome_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    situacao CHAR(1) NOT NULL DEFAULT 'A', -- Regra de Domínio, default 'A'
    codigo_erp INT NOT NULL,
    data_criacao DATETIME2(7) NOT NULL, -- Uso de DATETIME2 para precisão
    data_alteracao DATETIME2(7) NULL
);

-- Adiciona um índice para otimizar buscas por nome_completo, se necessário
CREATE INDEX IX_cad_usuario_nome_completo ON cad_usuario (nome_completo);

---

--------------------------------------------------------------------------------
-- 2. Criação da Tabela cad_grupoacesso (Classe GrupoAcesso)
--------------------------------------------------------------------------------
CREATE TABLE cad_grupoacesso (
    -- Chave Primária, IDENTITY para auto-incremento
    id_grupo INT PRIMARY KEY IDENTITY(1,1),
    -- Colunas da entidade GrupoAcesso
    desc_grupo VARCHAR(100) NOT NULL,
    situacao CHAR(1) NOT NULL DEFAULT 'A', -- Regra de Domínio, default 'A'
    data_criacao DATETIME2(7) NOT NULL,
    data_alteracao DATETIME2(7) NULL
);

---

--------------------------------------------------------------------------------
-- 3. Criação da Tabela cad_grupousuario (Classe GrupoUsuario)
-- Esta é a tabela de associação (N:N)
--------------------------------------------------------------------------------
CREATE TABLE cad_grupousuario (
    -- Chaves Estrangeiras que compõem a Chave Primária Composta
    id_grupo INT NOT NULL,
    id_usuario INT NOT NULL,
    -- Colunas da entidade GrupoUsuario
    data_associacao DATETIME2(7) NOT NULL,
    status_associacao CHAR(1) NOT NULL DEFAULT 'A', -- Note: Corrigido o erro de duplicidade de coluna na classe GrupoUsuario.java
    -- Definição da Chave Primária Composta (EmbeddedId)
    CONSTRAINT PK_GrupoUsuario PRIMARY KEY (id_grupo, id_usuario),
    -- Definição das Chaves Estrangeiras
    CONSTRAINT FK_GrupoUsuario_GrupoAcesso FOREIGN KEY (id_grupo)
        REFERENCES cad_grupoacesso (id_grupo)
        ON DELETE CASCADE, -- Sugerido: Se um Grupo de Acesso for deletado, a associação também é
    CONSTRAINT FK_GrupoUsuario_Usuario FOREIGN KEY (id_usuario)
        REFERENCES cad_usuario (id_usuario)
        ON DELETE CASCADE -- Sugerido: Se um Usuário for deletado, a associação também é
);

---

--------------------------------------------------------------------------------
-- 4. Inserção de Dados Básicos
--------------------------------------------------------------------------------

-- Inserção na tabela cad_usuario
INSERT INTO cad_usuario (nome_usuario, nome_completo, email, senha, situacao, codigo_erp, data_criacao, data_alteracao)
VALUES (
    'admin',
    'Administrador',
    'admin@java.com',
    '123456', -- Em um ambiente real, esta senha deve ser HASHED!
    'A',
    1,
    GETDATE(), -- Data e hora atual do SQL Server
    NULL
);

-- Inserção na tabela cad_grupoacesso
INSERT INTO cad_grupoacesso (desc_grupo, situacao, data_criacao, data_alteracao)
VALUES (
    'Administrador',
    'A',
    GETDATE(),
    NULL
);

-- Inserção na tabela cad_grupousuario
-- Como as chaves primárias são IDENTITY, usamos SCOPE_IDENTITY() ou valores literais (1, 1) se tivermos certeza.
-- Neste caso, como são as primeiras inserções, podemos usar os valores conhecidos (1, 1).
INSERT INTO cad_grupousuario (id_grupo, id_usuario, data_associacao, status_associacao)
VALUES (
    1, -- id_grupo do Administrador
    1, -- id_usuario do admin
    GETDATE(),
    'A'
);