CREATE TABLE avaliacoes (
                            id BIGSERIAL PRIMARY KEY,                            -- Identificador único da avaliação
                            produto_id BIGINT NOT NULL,                          -- Produto avaliado
                            usuario_id UUID NOT NULL,                            -- Usuário que fez a avaliação
                            nota SMALLINT CHECK (nota BETWEEN 1 AND 5) NOT NULL, -- Avaliação de 1 a 5 estrelas
                            data_avaliacao TIMESTAMP DEFAULT NOW(),              -- Data da avaliação

    -- Chaves estrangeiras
                            CONSTRAINT fk_avaliacao_produto
                                FOREIGN KEY (produto_id)
                                    REFERENCES produtos (id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_avaliacao_usuario
                                FOREIGN KEY (usuario_id)
                                    REFERENCES usuarios (id)
                                    ON DELETE CASCADE
);