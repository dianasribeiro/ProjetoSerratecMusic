# ProjetoSerratecMusic
Primeiro Projeto de API da residencia serratec 2025.2. 

# Serratec Music API 🎵

API RESTful desenvolvida como parte de um projeto de avaliação, simulando o back-end de uma plataforma de streaming de música, a **Serratec Music**. A API gerencia usuários, artistas, músicas e playlists, implementando conceitos essenciais de desenvolvimento back-end com o ecossistema Spring.

---

## 🚀 Funcionalidades Principais

- **CRUDs Completos**: Gerenciamento total de Artistas, Músicas, Usuários e Playlists.
- **Relacionamentos Complexos**: Implementação correta de relacionamentos JPA:
  - `OneToOne` → `Usuario <-> Perfil`
  - `OneToMany` → `Usuario <-> Playlist`
  - `ManyToMany` → `Musica <-> Artista` e `Playlist <-> Musica`
  - 
- **Validação de Dados**: Uso de Bean Validation para garantir a integridade dos dados recebidos.
- **Tratamento de Exceções**: `ControllerAdvice` para um tratamento de erros centralizado e padronizado.
- **Documentação Automática**: Geração de documentação interativa da API com Springdoc OpenAPI (Swagger).

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Springdoc OpenAPI (Swagger)
- Postman
---

## 🏛️ Arquitetura do Projeto

O projeto segue uma arquitetura em camadas para organizar as responsabilidades:
com.serratec.musicapi 

📦 com.serratec.musicapi  
├── 📦 controller   → Camada de API, responsável por expor os endpoints REST.  
├── 📦 domain       → Entidades de domínio (modelos) que representam as tabelas do banco de dados.  
├── 📦 exception    → Classes para tratamento de exceções globais.  
└── 📦 repository   → Camada de acesso a dados, responsável pela comunicação com o banco via Spring Data JPA.

---

## 📊 Diagrama do Banco de Dados

Diagrama ilustrativo da estrutura relacional implementada em PostgreSQL.

<img width="889" height="627" alt="Captura de tela 2025-10-18 154559" src="https://github.com/user-attachments/assets/f9ec191b-41d1-4581-9cb1-fd684d45c010" />


---

## 📚 Documentação dos Endpoints

A API é documentada automaticamente com Swagger. Após iniciar a aplicação, acesse:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

<img width="1812" height="1019" alt="Captura de tela 2025-10-18 163439" src="https://github.com/user-attachments/assets/0a834864-c5da-4d5e-a92e-64aef9199874" />


## 📮 Outra Ferramentas de Teste

A API foi testada com a seguinte ferramenta:

- 📮 [Postman](https://www.postman.com)


-------------------------
### Exemplos de Requisições

#### 1. Criar um Usuário e seu Perfil

```http
POST /usuarios
{
  "nome": "Manuel",
  "email": "Manu@email.com",
  "perfil": {
    "telefone": "11999957462",
    "dataNascimento": "1995-12-15"
  }
}
```

Exemplo de como ficaria depois de criada

<img width="1373" height="877" alt="Captura de tela 2025-10-18 165139" src="https://github.com/user-attachments/assets/217c1e74-7a01-4d57-ab3e-2f824ca76166" />

#### 2. Criar uma Música com Artistas existentes
```http
POST /musicas

{
  "titulo": "Bohemian Rhapsody",
  "minutos": 6,
  "genero": "ROCK",
  "artistas": [
    { "id": 1 },
    { "id": 2 }
  ]
}
```
#### 3.  Criar uma Playlist para um Usuário
```http
POST /playlists

{
  "nome": "Glamurosa",
  "descricao": "Lançada nos anos 90,'Glamurosa' é um marco do Funk Melody, um estilo mais romântico, melódico e com batidas mais suaves",
  "usuario": {
    "id": 6
  }
}
```
<img width="1367" height="693" alt="Captura de tela 2025-10-18 164618" src="https://github.com/user-attachments/assets/12a8356a-7ea3-4e9a-aceb-f77163ea9ce8" />

#### 4. Atualizar as Músicas de uma Playlist
```http
PUT /playlists/{id}
{
   {
    "id": 3,
    "nome": "Viva La Vida",
    "descricao": "Conhecida pelo seu arranjo orquestral e refrão que se tornou um hino.",
    "musicas": [
      {"id": 3}
      {"id": 1}
    ]
  }
}
```

<img width="1376" height="797" alt="Captura de tela 2025-10-18 164715" src="https://github.com/user-attachments/assets/d94122f0-45cc-4470-93d7-f80465065b9f" />

## ⚙️ Como Executar o Projeto

# Pré-requisitos
- Java JDK 17 ou superior
- Apache Maven 3.8 ou superior
- PostgreSQL instalado e em execução
- Cliente de API como Postman ou Insomnia (opcional)


# 1- Clone o repositório:
```
Bash
git clone https://github.com/seu-usuario/serratec-music-api.git
cd serratec-music-api
```

# 2- Configure o Banco de Dados:
  *Crie um banco de dados no PostgreSQL chamado serratec_music.
  *Edite o arquivo src/main/resources/application.properties com suas credenciais:
```
Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/serratec_music
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres
```

# 3-Execute a aplicação:
```
Bash
mvn spring-boot:run

```
# 4- Acesse a API:
- http://localhost:8080
- http://localhost:8080/swagger-ui.html


---

## 👨‍💻 Autor

✨ **Diana Souza Ribeiro**  
🎓 Residente do Serratec 2025.2  
💻 Desenvolvedora Full Stack

---



