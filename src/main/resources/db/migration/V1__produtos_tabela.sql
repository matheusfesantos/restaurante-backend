CREATE TABLE produtos (
                          id BIGSERIAL PRIMARY KEY,           -- Identificador único do produto
                          nome VARCHAR(100) NOT NULL,         -- Nome do produto
                          preco DECIMAL(10,2) NOT NULL,       -- Preço do produto (ex: 199.99)
                          foto TEXT                           -- URL ou base64 da imagem do produto
);