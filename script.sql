DROP DATABASE IF EXISTS agencia_viagem;

-- Criando BD
CREATE DATABASE IF NOT EXISTS agencia_viagem;
USE agencia_viagem;

-- Tabela de clientes
CREATE TABLE cliente (
	id_cliente INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	telefone VARCHAR(20) UNIQUE,
	email VARCHAR(100) UNIQUE,
	tipo ENUM('nacional', 'estrangeiro') NOT NULL,
	cpf VARCHAR(14) UNIQUE,     	-- Apenas se nacional
	passaporte VARCHAR(20) UNIQUE  -- Apenas se estrangeiro
);

-- Tabela de pacotes de viagem
CREATE TABLE pacote_viagem (
	id_pacote INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	destino VARCHAR(100) NOT NULL,
	duracao_dias INT,
	preco DECIMAL(10,2) NOT NULL,
	tipo varchar(100),
	descricao TEXT
);

-- Tabela de serviços adicionais
CREATE TABLE servico_adicional (
	id_servico INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	descricao TEXT,
	preco DECIMAL(10,2) NOT NULL
);

-- Tabela de pedidos (cliente contratando pacote)
CREATE TABLE pedido (
	id_pedido INT AUTO_INCREMENT PRIMARY KEY,
	id_cliente INT,
	id_pacote INT,
	data_contratacao DATE NOT NULL,
	FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_pacote) REFERENCES pacote_viagem(id_pacote) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabela de relação entre pedido e serviços adicionais (N:M)
CREATE TABLE pedido_servico (
	id_pedido INT,
	id_servico INT,
	PRIMARY KEY (id_pedido, id_servico),
	FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_servico) REFERENCES servico_adicional(id_servico) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Inserções de clientes
INSERT INTO cliente (nome, telefone, email, tipo, cpf) 
VALUES 
('Borges', '61981781838', 'borges@hotmail.com', 'nacional', '055.900.600-99'),
('Ana Souza', '61991234567', 'ana.souza@gmail.com', 'nacional', '123.456.789-00'),
('Lucas Ferreira', '11987654321', 'lucas.f@gmail.com', 'nacional', '987.654.321-00'),
('Maria Silva', '21998765432', 'maria.silva@outlook.com', 'nacional', '456.789.123-45'),
('João Santos', '85912345678', 'joao.santos@yahoo.com', 'nacional', '321.654.987-10'),
('Carolina Mendes', '47987123456', 'carolina.m@gmail.com', 'nacional', '789.123.456-78'),
('Rafael Costa', '51976543210', 'rafael.costa@uol.com.br', 'nacional', '159.753.486-20');

INSERT INTO cliente (nome, telefone, email, tipo, passaporte) 
VALUES 
('Emily Johnson', '+447912345678', 'emily.j@hotmail.co.uk', 'estrangeiro', 'X1234567'),
('Taro Yamada', '+81312345678', 'taro.y@japan.jp', 'estrangeiro', 'JP998877'),
('Pierre Dubois', '+33145678901', 'pierre.d@france.fr', 'estrangeiro', 'FR567890'),
('Sofia Martinez', '+34612345678', 'sofia.m@spain.es', 'estrangeiro', 'ES123456'),
('Giuseppe Rossi', '+39331234567', 'giuseppe.r@italy.it', 'estrangeiro', 'IT789012'),
('Hans Mueller', '+49301234567', 'hans.m@germany.de', 'estrangeiro', 'DE345678');

-- Inserções de pacotes
INSERT INTO pacote_viagem (nome, destino, duracao_dias, preco, tipo, descricao) 
VALUES 
('Aventura na Amazônia', 'Manaus', 7, 2999.90, 'aventura', 'Exploração de trilhas e passeio de barco'),
('Luxo em Paris', 'Paris', 5, 9999.00, 'luxo', 'Hospedagem 5 estrelas com city tour completo'),
('Rota Cultural Mineira', 'Ouro Preto', 3, 1200.50, 'cultural', 'Visitas a museus e igrejas históricas'),
('Surpresa no Deserto', 'Dubai', 6, 7500.00, 'outro', 'Safari no deserto com jantar árabe'),
('Praias do Nordeste', 'Natal', 4, 1800.00, 'relaxamento', 'Resort all inclusive com atividades aquáticas'),
('Aventura no Chile', 'Santiago', 8, 4500.00, 'aventura', 'Trekking na Cordilheira dos Andes'),
('Romance em Veneza', 'Veneza', 4, 6800.00, 'romance', 'Passeio de gôndola e jantar romântico'),
('Safári no Quênia', 'Nairóbi', 10, 12000.00, 'aventura', 'Safari fotográfico nos parques nacionais'),
('Cultura no Japão', 'Tóquio', 12, 8900.00, 'cultural', 'Imersão na cultura japonesa tradicional e moderna'),
('Relax nas Maldivas', 'Malé', 6, 15000.00, 'luxo', 'Resort overwater com spa e mergulho');

-- Inserções de serviços adicionais
INSERT INTO servico_adicional (nome, descricao, preco)
VALUES 
('Seguro Viagem', 'Cobertura internacional contra imprevistos', 199.99),
('Transfer Aeroporto', 'Transporte privativo de ida e volta', 150.00),
('Passeio Extra', 'Passeio turístico adicional opcional', 350.00),
('Refeição Especial', 'Pacote de refeições em restaurantes parceiros', 400.00),
('Guia Particular', 'Acompanhamento de guia especializado', 600.00),
('Upgrade de Hospedagem', 'Melhoria para categoria superior', 800.00),
('Aluguel de Equipamentos', 'Equipamentos para atividades específicas', 250.00),
('Spa e Wellness', 'Pacote de tratamentos relaxantes', 450.00),
('Fotografia Profissional', 'Sessão de fotos durante a viagem', 500.00),
('Wi-Fi Internacional', 'Conexão de internet durante toda a viagem', 120.00);

-- Inserções de pedidos
INSERT INTO pedido (id_cliente, id_pacote, data_contratacao)
VALUES 
(1, 2, '2025-04-22'),
(2, 1, '2025-04-23'),
(3, 3, '2025-04-24'),
(4, 5, '2025-04-25'),
(5, 4, '2025-04-26'),
(6, 7, '2025-04-27'),
(7, 6, '2025-04-28'),
(8, 9, '2025-04-29'),
(9, 8, '2025-04-30'),
(10, 10, '2025-05-01'),
(11, 1, '2025-05-02'),
(12, 3, '2025-05-03'),
(13, 5, '2025-05-04');

-- Inserções de pedidos com serviços
INSERT INTO pedido_servico (id_pedido, id_servico)
VALUES 
(1, 1), (1, 2), (1, 6),
(2, 3), (2, 7),
(3, 1), (3, 4),
(4, 2), (4, 8),
(5, 1), (5, 5), (5, 9),
(6, 2), (6, 4), (6, 8),
(7, 1), (7, 7), (7, 10),
(8, 5), (8, 9),
(9, 1), (9, 3), (9, 5),
(10, 2), (10, 6), (10, 8),
(11, 1), (11, 7),
(12, 4), (12, 5),
(13, 2), (13, 8), (13, 10);

