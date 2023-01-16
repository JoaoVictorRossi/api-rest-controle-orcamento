<h2>
    Controle de Orçamento Familiar
</h2>

---
### 💡  Sobre
Projeto focado em Back-End proporcionado pela Alura, com intuito de aprimorar e praticar os conhecimentos adquiridos em cursos e pesquisas.

Aplicação feita com objetivo de fornecer serviço de gestão financeira para qualquer tipo de usuário.

### 💬  Informações
O projeto consiste em uma API Rest feita na linguagem Java, seguindo Clean Architecture, padrões de projeto, e princípios para melhor otimização de código.

### 🧠 Tecnologias
- Java 17.
- Spring Boot 3:
    - Spring MVC.
    - Spring Data JPA.
    - Spring Security.
- Mockito.
- Flyway.
- MySQL.
- Hibernate
- Bibliotecas:
    - Auth0.
    - Junit.
    - Lombok.
    - JPA.
- Ferramentas:
    - Maven.

## 📃  Documentação
### Autenticação

Request de Autorização para registro no Banco de Dados

    POST /auth/registro - Registrar nova conta


*Request Body*
~~~ JSON
{
    "login": "testapi.orcamento@gmail.com", // (Obrigatório)
    "pass":"apitester" // (Obrigatório)
}
~~~

Request de Autenticação para adquirir o Token JWT

    POST /auth/login - Acesso a API


*Request Body*
~~~ JSON
{
    "login": "testapi.orcamento@gmail.com", // (Obrigatório)
    "pass":"apitester" // (Obrigatório)
}
~~~
<br>

### Receita

    POST /receitas - Cadastro de Receita

Parametro*Request Body*
~~~ JSON
{
    "descricao": "Test", // Descrição da receita (Obrigatório)
    "valor": "1550.50", // Valor da receita (Obrigatório)
    "tempo": "2023-01-01" // Data da receita (Obrigatório)
}
~~~

    GET /receitas - Listagem de Receita

| Rota  | Parâmetro  | Tipo | Obrigatório | Descrição|
| ----- | ---------- | ---- | ----------- | -------- |
| /receitas | Nenhum | Nenhum | Nenhum | Listar todas as Receitas|
| /receitas?descricao  | Descrição | String | False |Listar todas as receitas com a mesma descrição |
| /receitas/id | Identificador | Long | True | Listar receita pelo ID |
| /receitas/ano/mes | Ano e Mês | Integer e Integer| True | Listar todas as receitas de uma determinada data |


    PUT /receitas - Modificar Receita já cadastrada.

| Rota  | Parâmetro  | Tipo | Obrigatório | Descrição|
| ----- | ---------- | ---- | ----------- | -------- |
| /receitas/id | Identificador | Long | True | Atualizar Receita |

~~~ JSON
{
    "descricao": "Test2", // Descrição da Receita (Não Obrigatório)
    "valor": "2550.50", // Valor da Receita (Não Obrigatório)
    "tempo": "2023-02-01" // Data da Receita (Não Obrigatório)
}
~~~

    DELETE /receitas - Deletar Receita

| Rota  | Parâmetro  | Tipo | Obrigatório | Descrição|
| ----- | ---------- | ---- | ----------- | -------- |
| /receitas/id | Identificador | Long | True | Deletar Receita |

<br>

### Despesa


    POST /despesas - Cadastro de Receita

*Request Body*
~~~ JSON
{
    "descricao":"Test", // Descrição da Despesa (Não Obrigatório)
    "valor":"210.00", // Valor da Despesa (Não Obrigatório)
    "tempo":"2022-12-29",// Data da Despesa (Não Obrigatório)
    "categoria": "OUTRAS"// Categoria da Despesa (Obrigatório)
}

~~~

    GET /despesas - Listagem de Receita

| Rota  | Parâmetro  | Tipo | Obrigatório | Descrição|
| ----- | ---------- | ---- | ----------- | -------- |
| /despesas | Nenhum | Nenhum | Nenhum | Listar todas as Despesas|
| /despesas?descricao  | Descrição | String | False |Listar todas as despesas com a mesma descrição |
| /despesas/id | Identificador | Long | True | Listar despesa pelo ID |
| /despesas/ano/mes | Ano e Mês | Integer e Integer| True | Listar todas as despesas de uma determinada data |


    PUT /despesas - Modificar Receita já cadastrada.

| Rota  | Parâmetro  | Tipo | Obrigatório | Descrição|
| ----- | ---------- | ---- | ----------- | -------- |
| /despesas/id | Identificador | Long | True | Atualizar Despesa |

~~~ JSON
{
    "descricao": "Test2", // Descrição da Despesa (Não Obrigatório)
    "valor": "2550.50", // Valor da Despesa (Não Obrigatório)
    "tempo": "2023-02-01", // Data da Despesa (Não Obrigatório)
    "categoria": "LAZER"// Categoria da Despesa (Não Obrigatório)
}
~~~

    DELETE /despesas - Deletar Receita

| Rota  | Parâmetro  | Tipo | Obrigatório | Descrição|
| ----- | ---------- | ---- | ----------- | -------- |
| /despesas/id | Identificador | Long | True | Deletar Despesa |

<br>

### Resumo

    GET /resumo - Adquirir Resumo do mês

| Rota  | Parâmetro  | Tipo | Obrigatório | Descrição|
| ----- | ---------- | ---- | ----------- | -------- |
| /resumo/ano/mes | Ano e Mês | Integer e Integer | True | Adquirir Resumo detalhado do mês |
