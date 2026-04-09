# DEPENDENCIAS.md - Guia de Dependências do Projeto

## 📦 Dependências Instaladas

O projeto **estudo-automacao-porto** está configurado com todas as ferramentas essenciais para automação de testes e desenvolvimento em Java 17.

---

## ✅ Dependências Principais

### 1. **Selenium WebDriver 4.18.1**
**Automação de Testes Web**

```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.18.1</version>
</dependency>
```

- Automação completa de navegadores: Chrome, Firefox, Edge, IE
- Gerenciamento automático de WebDrivers
- DevTools para debugging
- Retry automático com Failsafe 3.3.2
- **Caso de uso**: Testes de interface web, automação de navegador

---

### 2. **Microsoft Playwright 1.42.0**
**Automação Moderna de Navegadores**

```xml
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.42.0</version>
</dependency>
```

- Alternativa moderna ao Selenium
- Melhor performance e confiabilidade
- Suporte para Chromium, WebKit, Firefox
- Automação de múltiplas abas e contextos
- **Caso de uso**: Testes web de alto desempenho, automação avançada

---

### 3. **Cucumber 7.15.0**
**Testes BDD (Behavior Driven Development)**

```xml
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.15.0</version>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.15.0</version>
    <scope>test</scope>
</dependency>
```

- Escrever testes em linguagem Gherkin (Given/When/Then)
- Integração com JUnit para execução
- Relatórios de testes compreensíveis para stakeholders
- **Caso de uso**: Testes funcionais legíveis, comunicação entre Dev e QA

---

### 4. **JUnit 5 (Jupiter) 5.10.1**
**Framework de Testes Unitários**

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.10.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.10.1</version>
    <scope>test</scope>
</dependency>
```

- Framework padrão para testes unitários em Java
- Anotações poderosas (@Test, @BeforeEach, @ParameterizedTest, etc.)
- Suporte para extensões customizadas
- **Caso de uso**: Testes de lógica, testes unitários de código

---

## 📋 Dependências Transitivas Importantes

Estas são dependências que vêm incluídas automaticamente:

| Dependência | Versão | Origem | Finalidade |
|------------|--------|--------|-----------|
| **Guava** | 33.0.0 | Selenium | Utilitários Google (Collections, Cache, etc.) |
| **OpenTelemetry** | 1.35.0 | Selenium | Rastreamento distribuído e telemetria |
| **Failsafe** | 3.3.2 | Selenium | Retry automático com circuit breaker |
| **SLF4J** | Implícito | Diversos | Logging abstrato (se adicionar Logback) |

---

## 🔧 Plugins Maven Configurados

### 1. **exec-maven-plugin**
Permite executar a aplicação principal diretamente pelo Maven.

```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

### 2. **maven-surefire-plugin**
Executa todos os testes unitários e BDD durante o build.

```bash
mvn test
```

### 3. **maven-compiler-plugin**
Compila o código Java 17 com suporte a recursos modernos.

```bash
mvn clean compile
```

---

## 📊 Capacidades Atuais do Projeto

✅ **O que você pode fazer AGORA:**

| Capacidade | Ferramenta | Exemplo |
|-----------|-----------|---------|
| Automação de UI web | Selenium + Playwright | Teste de login, preenchimento de formulários |
| Testes BDD | Cucumber | Testes escritos em linguagem natural |
| Testes unitários | JUnit 5 | Testes de classes e métodos isolados |
| Compilação Java 17 | Maven Compiler | Usar records, text blocks, sealed classes |
| Empacotamento JAR | Maven | Criar executável standalone |

---

## 🆕 Dependências Opcionais

Se precisar de funcionalidades adicionais, adicione estas dependências ao `pom.xml`:

### Para Testes de API REST
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
    <scope>test</scope>
</dependency>
```

### Para Logging Estruturado (SLF4J + Logback)
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.11</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.14</version>
</dependency>
```

### Para Relatórios Allure
```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-junit5</artifactId>
    <version>2.25.0</version>
    <scope>test</scope>
</dependency>
```

### Para Assertions Fluentes (AssertJ)
```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.25.3</version>
    <scope>test</scope>
</dependency>
```

### Para Mocagem (Mockito)
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>
```

### Para Banco de Dados de Teste (H2)
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.2.224</version>
    <scope>test</scope>
</dependency>
```

---

## 🚀 Próximos Passos

1. **Explorar Selenium**: Crie um teste simples automatizando um navegador
2. **Escrever BDD**: Crie um arquivo `.feature` com Cucumber
3. **Testar com JUnit**: Crie testes unitários para suas classes
4. **Adicionar Logging**: Integre SLF4J para melhor rastreamento

---

## 📞 Referências

- **Arquivo de Configuração**: `pom.xml` - fonte única de verdade
- **Documentação Maven**: https://maven.apache.org/
- **Selenium**: https://www.selenium.dev/
- **Playwright**: https://playwright.dev/java/
- **Cucumber**: https://cucumber.io/
- **JUnit 5**: https://junit.org/junit5/

