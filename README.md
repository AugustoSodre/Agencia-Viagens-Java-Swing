# Agência de Viagens - Java Swing

Alunos
- Augusto Sodré   -> 22400555

Este projeto é um sistema de gerenciamento para uma agência de viagens, desenvolvido em Java utilizando a biblioteca Swing para a interface gráfica. O sistema permite o cadastro e gerenciamento de clientes, pacotes de viagem, pedidos e serviços.

## Funcionalidades
- Cadastro, edição e remoção de clientes (nacionais e estrangeiros)
- Cadastro, edição e remoção de pacotes de viagem (aventura, cultural, luxo, outros)
- Cadastro, edição e remoção de pedidos
- Cadastro, edição e remoção de serviços
- Interface gráfica amigável desenvolvida com Java Swing
- Persistência de dados utilizando banco de dados relacional

## Estrutura do Projeto
```
pom.xml
script.sql
src/
  main/
    java/
      control/        # Controladores do sistema
      dao/            # Acesso a dados (DAO)
      main/           # Classe principal
      model/          # Modelos de domínio
      view/           # Telas e componentes Swing
    resources/        # Recursos do projeto
  test/
    java/             # Testes automatizados
    resources/
target/               # Arquivos compilados
```

## Como Executar
1. Certifique-se de ter o Java JDK 8+ e o Maven instalados.
2. Clone este repositório:
   ```bash
   git clone <url-do-repositorio>
   ```
3. Acesse a pasta do projeto:
   ```bash
   cd Agencia-Viagens-Java-Swing
   ```
4. Compile o projeto com Maven:
   ```bash
   mvn clean package
   ```
5. Execute a aplicação:
   ```bash
   java -cp target/classes main.Main
   ```

## Banco de Dados
- O script `script.sql` contém as instruções para criação das tabelas necessárias.
- Configure a conexão com o banco de dados no arquivo `Conexao.java`.
