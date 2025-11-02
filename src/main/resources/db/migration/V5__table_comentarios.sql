CREATE TABLE comentarios (
                             id BIGSERIAL PRIMARY KEY,                   -- Identificador único do comentário
                             produto_id BIGINT NOT NULL,                 -- Produto comentado
                             usuario_id UUID NOT NULL,                   -- Usuário que comentou
                             comentario TEXT NOT NULL,                   -- Texto do comentário
                             data_comentario TIMESTAMP DEFAULT NOW(),    -- Data e hora do comentário

    -- Chaves estrangeiras
                             CONSTRAINT fk_comentario_produto
                                 FOREIGN KEY (produto_id)
                                     REFERENCES produtos (id)
                                     ON DELETE CASCADE,

                             CONSTRAINT fk_comentario_usuario
                                 FOREIGN KEY (usuario_id)
                                     REFERENCES usuarios (id)
                                     ON DELETE CASCADE
);