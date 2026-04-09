# estudo-automacao-porto

Projeto de estudo de automação de testes web com foco em BDD, validando fluxos reais no site da documentação do Playwright.

---

## 📌 Descrição do Projeto

Este projeto automatiza cenários end-to-end para validar comportamento de interface e navegação web, cobrindo:

- validação de título da página inicial
- navegação para documentação
- busca por conteúdo técnico
- troca de tema (claro/escuro)
- seleção de linguagem de exemplo (Java) e validação de snippet de código

### 🧠 Linguagens usadas

- ☕ `Java 17`
- 🥒 `Gherkin` (cenários Cucumber em português)
- 🧩 `XML` (build/dependências no Maven)

### 🛠️ Frameworks, ferramentas e plugins

- 🌐 Selenium WebDriver (`selenium-java`)
- 🧪 Cucumber (`cucumber-java`, `cucumber-junit`)
- ✅ JUnit (`JUnit 4` + `JUnit 5`)
- 🚗 WebDriverManager (gestão automática do ChromeDriver)
- 📦 Apache Maven (build e execução)
- 🧾 SLF4J Simple (logs)
- 🧭 IntelliJ IDEA + Codex (IDE principal)
- 🌍 Google Chrome (navegador dos testes)

### 🤖 Agentes de IA instalados e configurados

- `capacity`
- `customize`
- `deploy-model`
- `microsoft-foundry`
- `preset`
- `skill-creator`
- `skill-installer`

---

## 🧰 Pré-requisitos

- Java JDK 17 instalado e configurado no `JAVA_HOME`
- Maven 3.8+ disponível no terminal (`mvn -v`)
- Google Chrome instalado
- Git instalado (`git --version`)
- IntelliJ IDEA (recomendado) com integração Git/GitHub ativa

---

## ⚙️ Instalação e Configuração

### 1) Clonar o projeto

```bash
git clone <URL_DO_REPOSITORIO>
cd estudo-automacao-porto
```

### 2) Validar ambiente

```bash
java -version
mvn -v
git --version
```

### 3) Baixar dependências e compilar

```bash
mvn clean compile
```

### 4) Abrir no IntelliJ IDEA

- `File` -> `Open` -> selecione a pasta do projeto
- Aguarde o import automático do Maven
- Confirme SDK do projeto em `File` -> `Project Structure` -> `Project SDK = 17`
- (Opcional) Em `Maven`, clique em `Reload All Maven Projects`

---

## ▶️ Comandos de Execução (Testes e Build)

### Rodar testes automatizados

```bash
mvn test
```

### Compilar

```bash
mvn clean compile
```

### Empacotar

```bash
mvn clean package
```

### Executar aplicação principal

```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

### Limpar build

```bash
mvn clean
```

### Relatório de execução Cucumber

Após `mvn test`, o relatório HTML é gerado em:

- `target/cucumber-reports/relatorio-automacao.html`

---

## 🐙 GitHub na IDE (IntelliJ)

### Configuração inicial

1. `File` -> `Settings` -> `Version Control` -> `Git`  
   Configure o caminho do Git e teste com `Test`.
2. `File` -> `Settings` -> `Version Control` -> `GitHub`  
   Faça login com conta GitHub (token ou OAuth).

### Fluxo diário na IDE

1. Criar branch: `Git` -> `Branches` -> `New Branch`
2. Editar arquivos e revisar diffs em `Commit` window
3. Commit: mensagem clara + selecionar arquivos
4. Push: `Git` -> `Push`
5. Pull/Update: `Git` -> `Update Project`
6. Resolver conflitos pelo merge tool da IDE quando necessário

---

## 💻 GitHub no Prompt (Terminal)

### Configuração inicial (uma vez por máquina)

```bash
git config --global user.name "Seu Nome"
git config --global user.email "seu-email@dominio.com"
```

### Clonar e preparar

```bash
git clone <URL_DO_REPOSITORIO>
cd estudo-automacao-porto
git branch
git status
```

### Criar branch de trabalho

```bash
git checkout -b feature/minha-melhoria
```

### Ciclo de desenvolvimento

```bash
git status
git add .
git commit -m "feat: descreva a alteração"
git push -u origin feature/minha-melhoria
```

### Atualizar branch local com remoto

```bash
git fetch origin
git pull origin main
```

### Integrar mudanças da `main` na sua branch

```bash
git checkout feature/minha-melhoria
git merge main
```

### Resolver conflitos (quando ocorrer)

```bash
git status
git add .
git commit -m "fix: resolve conflitos de merge"
```

### Abrir Pull Request (opções)

- Via GitHub Web: acesse o repositório e clique em `Compare & pull request`
- Via IntelliJ: menu de GitHub / Pull Requests

---

## 📚 Referências internas

- `AGENTS.md`
- `README_VALIDACAO.md`
- `ANALISE_PASSOSDETESTE.md`
- `DEPENDENCIAS.md`
- `GUIA_STEP_DEFINITIONS.md`

