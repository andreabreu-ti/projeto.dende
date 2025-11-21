-- Atualiza a senha do usuário admin para senha criptografada com BCrypt
-- Senha original: 123456
-- Senha BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

UPDATE cad_usuario
SET senha = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    data_alteracao = GETDATE()
WHERE nome_usuario = 'admin';