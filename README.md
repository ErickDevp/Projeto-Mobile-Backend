<div align="center">

<h1>💪 FitTracker Pro — Backend</h1>

<p>API REST para gerenciamento de treinos, rotinas e exercícios, desenvolvida com <strong>Java + Spring Boot</strong>.</p>

<p>
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Flutter-App_Mobile-02569B?style=for-the-badge&logo=flutter&logoColor=white" />
</p>

<p>
  <a href="https://github.com/ErickDevp/Projeto-Mobile-FrontEnd">📱 Repositório Front-end (Flutter)</a>
</p>

</div>

---

## 📋 Sobre o Projeto

O **FitTracker Pro** é uma aplicação mobile fullstack voltada para o acompanhamento de atividades físicas. O sistema permite que usuários criem e gerenciem seus treinos personalizados, montem rotinas semanais, acompanhem exercícios por grupo muscular e utilizem templates de rotinas pré-definidas.

Este repositório contém a **API REST (back-end)** que alimenta o app mobile Flutter.

---

## 🚀 Funcionalidades

- 🔐 **Autenticação** com JWT (login, registro e reset de senha)
- 👤 **Perfil de usuário** com foto, nível de treino e objetivo físico
- 🏋️ **Gerenciamento de treinos** com clonagem de treinos existentes
- 📅 **Rotinas semanais** com distribuição por dia da semana
- 📋 **Templates de rotinas** prontos para reutilização
- 💪 **Cadastro de exercícios** por treino
- 🗄️ **Inicialização de banco** com dados padrão via `DatabaseInitializer`

---

## 🛠️ Tecnologias Utilizadas

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.x |
| Segurança | Spring Security + JWT |
| Persistência | Spring Data JPA / Hibernate |
| Banco de Dados | MySQL |
| Build | Maven |
| Armazenamento | Upload local de imagens (`/uploads/perfil`) |

---

## 📁 Estrutura do Projeto

```
src/
└── main/
    ├── java/com/fittracker/fittrackerpro/
    │   ├── config/              # Configurações (Security, CORS, DatabaseInitializer)
    │   ├── controller/          # Endpoints REST
    │   │   ├── AuthController
    │   │   ├── UsuarioController
    │   │   ├── TreinoController
    │   │   ├── RotinaController
    │   │   ├── RotinaTemplateController
    │   │   ├── ExercicioController
    │   │   └── DiaController
    │   ├── dto/                 # Objetos de transferência de dados
    │   │   ├── auth/
    │   │   ├── treino/
    │   │   ├── rotina/
    │   │   ├── rotinaTemplate/
    │   │   ├── exercicio/
    │   │   ├── diaRotina/
    │   │   └── usuario/
    │   ├── entity/              # Entidades JPA
    │   │   ├── enums/           # DiaSemana | NivelTreino | ObjetivoUsuario | Role
    │   │   ├── Usuario
    │   │   ├── Treino
    │   │   ├── Rotina
    │   │   ├── RotinaTemplate
    │   │   ├── DiaRotina
    │   │   ├── Exercicio
    │   │   └── PasswordResetToken
    │   ├── mapper/              # Conversão Entity ↔ DTO
    │   ├── repository/          # Interfaces JPA
    │   ├── security/            # Filtro JWT (JwtAuthFilter)
    │   └── service/             # Regras de negócio
    │       └── TreinoCloneService  # Clonagem de treinos
    └── resources/
        └── application.properties
uploads/
└── perfil/                     # Fotos de perfil dos usuários
```

---

## ⚙️ Pré-requisitos

- [Java 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8+](https://maven.apache.org/)
- [MySQL 8.0+](https://www.mysql.com/)

---

## 🔧 Configuração e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/Projeto-Mobile-Backend.git
cd Projeto-Mobile-Backend
```

### 2. Configure as variáveis de ambiente

Crie um arquivo `.env` baseado no `.env_example` (ou configure diretamente em `application.properties`):

```env
DB_URL=jdbc:mysql://localhost:3306/fittracker_pro
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=seu_segredo_jwt
JWT_EXPIRATION=86400000
```

### 3. Crie o banco de dados

```sql
CREATE DATABASE fittracker_pro CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

> O Spring Boot criará as tabelas automaticamente via JPA/Hibernate. O `DatabaseInitializer` populará dados padrão na primeira execução.

### 4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 🔌 Endpoints Principais

### 🔐 Autenticação
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Login do usuário |
| `POST` | `/auth/register` | Cadastro de novo usuário |
| `POST` | `/auth/reset-password` | Solicitar reset de senha |
| `POST` | `/auth/change-password` | Alterar senha |

### 👤 Usuários
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/usuarios/{id}` | Buscar perfil |
| `PUT` | `/usuarios/{id}` | Atualizar perfil |
| `POST` | `/usuarios/{id}/foto` | Upload de foto de perfil |

### 🏋️ Treinos
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/treinos` | Listar treinos do usuário |
| `POST` | `/treinos` | Criar treino |
| `PUT` | `/treinos/{id}` | Atualizar treino |
| `DELETE` | `/treinos/{id}` | Remover treino |
| `POST` | `/treinos/{id}/clonar` | Clonar treino existente |

### 📅 Rotinas
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/rotinas` | Listar rotinas |
| `POST` | `/rotinas` | Criar rotina |
| `GET` | `/rotinas/{id}/dias` | Listar dias da rotina |

### 📋 Templates de Rotina
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/rotina-templates` | Listar templates disponíveis |
| `POST` | `/rotina-templates` | Criar template |

### 💪 Exercícios & Dias
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/exercicios` | Adicionar exercício ao treino |
| `PUT` | `/exercicios/{id}` | Atualizar exercício |
| `DELETE` | `/exercicios/{id}` | Remover exercício |
| `POST` | `/dias` | Associar treino a um dia da rotina |

---

## 🗂️ Enumerações do Domínio

| Enum | Valores |
|---|---|
| `DiaSemana` | `SEGUNDA`, `TERCA`, `QUARTA`, `QUINTA`, `SEXTA`, `SABADO`, `DOMINGO` |
| `NivelTreino` | `INICIANTE`, `INTERMEDIARIO`, `AVANCADO` |
| `ObjetivoUsuario` | `HIPERTROFIA`, `EMAGRECIMENTO`, `CONDICIONAMENTO`, `FORCA` |
| `Role` | `USER`, `ADMIN` |

---

## 🔗 Repositório Relacionado

| Projeto | Tecnologia | Link |
|---|---|---|
| 📱 Front-end Mobile | Flutter + Dart | [Projeto-Mobile-FrontEnd](https://github.com/ErickDevp/Projeto-Mobile-FrontEnd) |

---

## 🧪 Testes

```bash
./mvnw test
```

---

## 🎓 Sobre

Projeto desenvolvido para a disciplina de **Projeto Mobile** com foco em desenvolvimento de APIs REST para aplicativos móveis, utilizando boas práticas de arquitetura em camadas, DTOs, Mappers e autenticação stateless com JWT.

---

<div align="center">
  Feito com ☕ Java, 💚 Spring Boot e 💙 Flutter
</div>
