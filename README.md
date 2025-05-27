# API de Produtos

Este projeto é uma API RESTful desenvolvida em Java com Jakarta EE, utilizando JAX-RS. Ele permite o gerenciamento de usuários e produtos, com persistência de dados em um banco de dados MySQL.

## Funcionalidades

- **Gerenciamento de Usuários:**
  - Criação de novos usuários
  - Listagem de usuários existentes
  - Atualização de informações de usuários
  - Remoção de usuários

- **Gerenciamento de Produtos:**
  - Adição de novos produtos
  - Listagem de produtos disponíveis
  - Atualização de detalhes dos produtos
  - Exclusão de produtos

## Estrutura do Banco de Dados

O projeto utiliza um banco de dados MySQL com as seguintes tabelas:

- **usuarios**: Armazena informações dos usuários (como ID, nome, e-mail, etc.)
- **produtos**: Contém detalhes dos produtos (como ID, nome, descrição, preço, etc.)

## Tecnologias Utilizadas

- Java com Jakarta EE (JAX-RS)
- Apache TomEE ou Payara Server
- MySQL
- Maven
- NetBeans

## Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/Nicolas22prog/api-produtos.git
   ```

2. Importe o projeto no NetBeans ou outro IDE compatível com Jakarta EE.

3. Configure o banco de dados MySQL (crie as tabelas `usuarios` e `produtos` conforme necessário).

4. Configure a conexão com o banco no arquivo `persistence.xml`.

5. Execute o servidor (TomEE ou Payara) e acesse os endpoints da API, por exemplo:
   ```
   http://localhost:8080/api-produtos/api/produtos
   ```

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
