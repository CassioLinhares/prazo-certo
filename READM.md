# 📱 Prazo Certo

> **Aplicativo Android para gerenciamento de atividades acadêmicas com autenticação segura via Firebase.**

Desenvolvido com **Jetpack Compose** e **Firebase**, o *Prazo Certo* permite que estudantes organizem seus trabalhos e prazos de forma simples, segura e em tempo real — sem necessidade de backend próprio.

---

## 🖼️ Telas da Aplicação

### Tela de Login
> Autenticação do usuário com e-mail/senha ou conta Google.

```
┌─────────────────────────┐
│      Prazo Certo        │
│         🐱              │
│      Autenticação       │
│  ┌───────────────────┐  │
│  │  E-mail           │  │
│  └───────────────────┘  │
│  ┌───────────────────┐  │
│  │  Password         │  │
│  └───────────────────┘  │
│  ┌───────────────────┐  │
│  │      Entrar       │  │
│  └───────────────────┘  │
│  ┌───────────────────┐  │
│  │ Entrar com Google │  │
│  └───────────────────┘  │
│  Não tem conta? Criar   │
└─────────────────────────┘
```

---

### Tela de Cadastro (SignUp)
> Criação de nova conta utilizando Firebase Authentication.

```
┌─────────────────────────┐
│      Prazo Certo        │
│         🐸              │
│       Criar conta       │
│  ┌───────────────────┐  │
│  │  E-mail           │  │
│  └───────────────────┘  │
│  ┌───────────────────┐  │
│  │  Password         │  │
│  └───────────────────┘  │
│  ┌───────────────────┐  │
│  │   Criar conta     │  │
│  └───────────────────┘  │
│  Já tem conta? Entrar   │
└─────────────────────────┘
```

---

### Tela Home — Lista de Atividades
> Visualização, criação, edição e exclusão de tarefas em tempo real.

```
┌─────────────────────────┐
│ Prazo Certo - Cássio  ✕ │ ← logout
├─────────────────────────┤
│ Atividades              │
├─────────────────────────┤
│ Modelagem Conceitual    │
│ Banco de Dados          │
│ 2025-12-12          ✏️🗑│
├─────────────────────────┤
│ Calculadora de IMC      │
│ Lógica de Programação   │
│ 2025-12-12          ✏️🗑│
├─────────────────────────┤
│ Levantamento de Req.    │
│ Prazo 2                 │
│ 2025-10-12          ✏️🗑│
└─────────────────┌───┐───┘
                  │ + │
                  └───┘
```

---

### AlertDialog — Adicionar / Editar Atividade
> Formulário para cadastro e atualização das tarefas.

```
┌────────────────────────┐
│       Adicionar        │
│ ┌────────────────────┐ │
│ │ Título da atividade│ │
│ └────────────────────┘ │
│ ┌────────────────────┐ │
│ │ Disciplina         │ │
│ └────────────────────┘ │
│ ┌────────────────────┐ │
│ │ Data de entrega    │ │
│ └────────────────────┘ │
│ ☐ Entregue             │
│        [Cancelar][OK]  │
└────────────────────────┘
```

---

## 🚀 Funcionalidades

| Módulo | Funcionalidade |
|--------|----------------|
| 🔐 Autenticação | Login com e-mail e senha |
| 🔐 Autenticação | Login com conta Google (OAuth 2.0) |
| 🔐 Autenticação | Persistência de sessão (permanece logado) |
| 👤 Cadastro | Criação de conta com Firebase Authentication |
| 📋 Tarefas | Adicionar nova atividade |
| 📋 Tarefas | Editar atividade existente |
| 📋 Tarefas | Excluir atividade com confirmação |
| 📋 Tarefas | Visualização em lista com LazyColumn |
| ☁️ Cloud | Sincronização em tempo real via Realtime Database |

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
|------------|------------|
| **Kotlin** | Linguagem principal |
| **Jetpack Compose** | Interface declarativa moderna |
| **Firebase Authentication** | Autenticação de usuários |
| **Firebase Realtime Database** | Banco de dados em tempo real |
| **Navigation Compose** | Navegação entre telas |
| **Material Design 3** | Componentes visuais |
| **Google Sign-In SDK** | Autenticação com conta Google |

---

## 🔥 Configuração Firebase

O projeto utiliza dois serviços principais do Firebase:

### Firebase Authentication
- Criação e login de contas (e-mail/senha)
- Autenticação com conta Google via OAuth 2.0 + OpenID Connect
- Senhas armazenadas com **hash + salt** (nunca em texto puro)
- Gerenciamento automático de sessão

### Firebase Realtime Database
- Dados armazenados em formato **JSON**
- Sincronização em **tempo real** para todos os clientes conectados
- Suporte **offline**: dados salvos localmente e sincronizados ao reconectar

---

## 📂 Estrutura do Projeto

```
app/
├── manifests/
├── kotlin+java/
│   └── br.com.fiap.prazocerto/
│       ├── model/
│       │   └── Atividade.kt          # Classe de dados
│       ├── screens/
│       │   ├── LoginScreen.kt        # Tela de login
│       │   ├── SignupScreen.kt       # Tela de cadastro
│       │   ├── HomeScreen.kt         # Tela principal
│       │   └── AtividadeDialog.kt    # Dialog de criação/edição
│       ├── ui.theme/
│       └── MainActivity.kt           # Entry point + Navigation
└── res/
    └── values/
        └── strings.xml               # Contém default_web_client_id
```

---

## ⚙️ Como Executar o Projeto

### 1️⃣ Clonar o repositório

```bash
git clone https://github.com/FIAP/ON_TDS_ANDROID_APP_PRAZO_CERTO.git
```

### 2️⃣ Abrir no Android Studio

Abra o projeto no **Android Studio** (versão recomendada: Hedgehog ou superior).

### 3️⃣ Configurar Firebase

1. Acesse o [Firebase Console](https://console.firebase.google.com/)
2. Crie um novo projeto chamado **Prazo Certo**
3. Conecte o projeto Android via **Tools → Firebase** no Android Studio
4. Ative os seguintes serviços:
    - ✅ **Firebase Authentication** → E-mail/Senha + Google
    - ✅ **Firebase Realtime Database** → modo de teste
5. Para o login com Google:
    - Obtenha o **SHA-1** via Gradle → Tasks → android → **signingReport**
    - Adicione o SHA-1 nas configurações do projeto Firebase
    - Copie o **ID do Cliente Web** e adicione ao `strings.xml`:
      ```xml
      <string name="default_web_client_id">SEU_ID_AQUI</string>
      ```

> ⚠️ **Importante**: Adicione `app/google-services.json` ao seu `.gitignore` para não expor credenciais no GitHub.

### 4️⃣ Executar

Execute em um **emulador Android** ou **dispositivo físico** (API 28+).

---

## 🔐 Segurança

- Senhas dos usuários **nunca são armazenadas em texto puro**
- Firebase utiliza algoritmos de **hash e salt** para proteger credenciais
- O arquivo `google-services.json` deve ser adicionado ao `.gitignore`
- Em caso de comprometimento do sistema, invasores não conseguem acessar as senhas reais — apenas os hashes

---

## 📌 Objetivo do Projeto

Este projeto foi desenvolvido para praticar:

- ✅ Integração de aplicações Android com **Firebase**
- ✅ Autenticação segura com **OAuth 2.0** e **OpenID Connect**
- ✅ **CRUD completo** com banco de dados em tempo real
- ✅ Desenvolvimento de interfaces modernas com **Jetpack Compose**
- ✅ Navegação entre telas com **Navigation Compose**

---

## 👨‍💻 Autor

**Cássio Mateus da Silva Linhares**  
Estudante de Desenvolvimento Android — FIAP  
RM: 565388

---

## 📄 Referências

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose/documentation?hl=pt-br)
- [Firebase Authentication — Android](https://firebase.google.com/docs/auth/android/google-signin?hl=pt-br)
- [Firebase Realtime Database](https://firebase.google.com/docs/database?hl=pt-br)
- [Navigation Compose](https://developer.android.com/guide/navigation/backstack?hl=pt-br)