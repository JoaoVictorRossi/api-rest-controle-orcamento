CREATE TABLE tb_receitas
(
	ID BIGINT AUTO_INCREMENT NOT NULL,
    DESCRICAO VARCHAR(500),
    VALOR FLOAT NOT NULL,
    TEMPO DATE NOT NULL,
    
    PRIMARY KEY (ID)
);

CREATE TABLE tb_despesas
(
	ID BIGINT AUTO_INCREMENT NOT NULL,
    DESCRICAO VARCHAR(500),
    VALOR FLOAT NOT NULL,
    TEMPO DATE NOT NULL,
    
    PRIMARY KEY (ID)
);
