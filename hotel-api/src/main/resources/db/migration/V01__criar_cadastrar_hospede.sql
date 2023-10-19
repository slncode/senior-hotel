CREATE TABLE HOSPEDE (
	id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    documento BIGINT UNIQUE,
    telefone NUMERIC(10, 0)
);


-- Tabela CheckIn
CREATE TABLE CHECK_IN (
    id SERIAL PRIMARY KEY,
    hospede_documento INT REFERENCES Hospede(documento),
    data_entrada TIMESTAMP,
    data_saida TIMESTAMP,
    adicional_veiculo BOOLEAN,
    valor_estadia NUMERIC(10, 2)
);

-- Tabela ValorDiaria (Valores das Diárias)
CREATE TABLE VALOR_DIARIA (
    id SERIAL PRIMARY KEY,
    valor_dia_semana NUMERIC(10, 2),
    valor_fds NUMERIC(10, 2),
    valor_veiculo_semana NUMERIC(10, 2),
    valor_veiculo_fds NUMERIC(10, 2)
);

INSERT INTO Valor_Diaria (valor_dia_semana, valor_fds, valor_veiculo_semana, valor_veiculo_fds)
 VALUES (120.00, 150.00, 15.00, 20.00);