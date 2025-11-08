ALTER TABLE produtos
    ADD COLUMN usuario_id UUID;

ALTER TABLE produtos
    ADD CONSTRAINT fk_produtos_usuario
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios(id)
            ON DELETE CASCADE;