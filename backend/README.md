# Desafio Softplan 

## Configuração do Backend:

### Tecnologias Utilizadas:
 - Java 8
 - Spring boot
 - PostgreSQL
 - Json Web Token (JWT)
 - JPA
 - Hibernate
 - Flyway

### Módulos da API

 - Usuários
 - Processos
 
### Configuração Sem Docker
 
* Para rodar a aplicação sem utilizar o docker, será necessário ter o `Java` na sua máquina e o Banco de dados `PostgreSQL`.
* Altere os dados de conexão do banco de dados no arquivo `application.yml`, que está localizado no caminho `src/main/resources/application.yml`, para os da sua máquina. Mude apenas os seguintes valores:
```
 username: localhost
 password: postgres
 url: jdbc:postgresql://localhost:5432/db_desafio_softplan
``` 

* Lembre de criar o seu banco de dados com o mesmo nome que você colocou no "url" no arquivo `application.yml`

### Configuração Com Docker
 
* Para rodar a aplicação com utilizar o docker, será necessário ter o `Docker` na sua máquina.
* Altere os dados de conexão do banco de dados no arquivo `docker-compose.yml`, que está localizado na raiz do projeto backend, para os da sua máquina. Mude apenas os seguintes valores:
```
 POSTGRES_PASSWORD=docker
 POSTGRES_USER=postgres
 POSTGRES_DB=db_desafio_softplan

 SPRING_DATASOURCE_URL=jdbc:postgresql://processapidatabase:5432/db_desafio_softplan
 SPRING_DATASOURCE_USERNAME=postgres
 SPRING_DATASOURCE_PASSWORD=docker
``` 

* Lembre de criar o seu banco de dados com o mesmo nome que você colocou no "url" no arquivo `docker-compose.yml`

* Por fim, entre na pasta backend e execute esses 2 comandos:
Primeiro:
```
 ./mvnw package -DskipTests
```
Segundo:
```
 docker-compose up
```

* Feito isso, a aplicação criará as tabelas e adicionará já alguns usuários.

* A aplicação estará disponível na url: [http://localhost:8080](http://localhost:8080)

* Usuários criados com as migrations:

```
(Administrador):
    nome: Administrador
    email: admin@email.com
    senha: admin@123

(Triador):
    nome: Ana
    email: ana@email.com
    senha: ana@123

(Finalizador):
    nome: José
    email: jose@email.com
    senha: jose@123
```


## Configuração do Frontend:

### Tecnologias Utilizadas:
 - React
 - TypeScript
 - Ant Design

### Páginas da Aplicação

 - Login
 - Início (/inicio)
 - Módulo de usuários (/usuarios/**)
 - Módulo de Processo (/processo/**)
 
   ```
   Acesso: 
        email: admin@email.com
        senha: admin@123
   
        email: ana@email.com
        senha: ana@123
  
        email: jose@email.com
        senha: jose@123
   ```
 
### Configuração

* O projeto necessita tando do `node.js` e `yarn`para ser executado,
caso não tenha em sua máquina, acesse os links abaixo:
    * node.js: [Baixar node.js](https://nodejs.org/pt-br/download/)
    * yarn: [Baixar yarn](https://legacy.yarnpkg.com/en/docs/install/#mac-stable)
    
* Logo após a etapa de instalação, basta entrar na pasta do projeto e rodar o comando
`yarn install`, para o projeto baixar todas as bibliotecas necessárias.
* Renomear o arquivo `.env.exemple` para `.env` pois nele há o endereço da url da api que é necessário para o funcionamento da aplicação:
```
 REACT_APP_AXIOS_URL=http://localhost:8080/api/v1
``` 

* Por fim, dando tudo certo até o seguinte momento, temos 1 comando para rodar:
    * `yarn start`: este comando irá levantar a aplicação no seguinte endereço: [http://localhost:3000](http://localhost:3000)