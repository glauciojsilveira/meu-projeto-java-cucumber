# 📚 Guia: Criar Step Definitions do Cucumber - Passo a Passo

## O que foi criado?

Arquivo: `PassosDeTeste.java`
Localização: `src/test/java/com/automacao/steps/`
Pacote: `com.automacao.steps`

---

## 🎯 O que é uma Step Definition?

Uma **Step Definition** é uma classe Java que:
- Mapeia os passos do arquivo `.feature` (linguagem natural) para código Java
- Implementa a automação real (clica, valida, digita, etc.)
- Conecta o Cucumber com as ações do Playwright/Selenium

---

## 📋 Estrutura do Arquivo Criado

```
PassosDeTeste.java
├── Package: com.automacao.steps
├── Imports: Cucumber + Playwright
├── Métodos anotados com:
│   ├── @Dado (Given) - Contexto inicial
│   ├── @Quando (When) - Ações/eventos
│   └── @Então (Then) - Validações/resultados
└── Variáveis globais: page, browser, context
```

---

## 🔧 Passo a Passo - Como Criar do ZERO

### **Passo 1: Criar a Estrutura de Diretórios**

Se não existir, crie os diretórios:
```
src/test/java/com/automacao/steps/
```

No Windows PowerShell:
```powershell
mkdir -p "src/test/java/com/automacao/steps"
```

---

### **Passo 2: Criar o Arquivo Java**

Crie um arquivo chamado `PassosDeTeste.java` na pasta acima.

---

### **Passo 3: Declarar o Pacote**

Primeira linha do arquivo:
```java
package com.automacao.steps;
```

**Por quê?** O Cucumber precisa saber em qual pacote procurar as step definitions.

---

### **Passo 4: Importar as Anotações do Cucumber**

```java
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
```

**Importante**: Use `java.pt` para português (em vez de `java.en` para inglês).

---

### **Passo 5: Importar a Biblioteca de Automação**

Se estiver usando **Playwright**:
```java
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
```

Se estiver usando **Selenium**, seria:
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
```

---

### **Passo 6: Criar a Classe**

```java
public class PassosDeTeste {
    // Variáveis globais para o navegador
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    
    // Métodos com @Dado, @Quando, @Então
}
```

---

### **Passo 7: Mapear os Passos do .feature**

Para cada linha do arquivo `.feature`, crie um método anotado:

#### Exemplo 1: Passo Dado
```gherkin
Dado [HOME] que acesso o site "https://playwright.dev"
```

Mapear assim:
```java
@Dado("[HOME] que acesso o site {string}")
public void acessarSite(String url) {
    playwright = Playwright.create();
    browser = playwright.chromium().launch();
    context = browser.newContext();
    page = context.newPage();
    page.navigate(url);
}
```

**Detalhes importantes:**
- `{string}` = captura texto entre aspas do .feature
- O parâmetro `String url` recebe esse valor automaticamente
- A descrição entre aspas deve ser idêntica ao .feature

#### Exemplo 2: Passo Quando (Ação)
```gherkin
Quando [NAV] clico em "Get started"
```

Mapear assim:
```java
@Quando("[NAV] clico em {string}")
public void clicarPorTexto(String texto) {
    page.getByRole("link", new Page.GetByRoleOptions()
        .setName(texto)).click();
}
```

#### Exemplo 3: Passo Então (Validação)
```gherkin
Então [HOME] o título deve conter "Playwright"
```

Mapear assim:
```java
@Então("[HOME] o título deve conter {string}")
public void validarTitulo(String textoEsperado) {
    String titulo = page.title();
    if (!titulo.contains(textoEsperado)) {
        throw new AssertionError("Falha: " + textoEsperado);
    }
}
```

---

### **Passo 8: Entender os Placeholders**

| Placeholder | Tipo | Exemplo |
|------------|------|---------|
| `{string}` | Texto | `"Get started"` |
| `{int}` | Número inteiro | `5` |
| `{double}` | Número decimal | `3.14` |

**Exemplo com número:**
```gherkin
Quando aguardo {int} segundos
```

```java
@Quando("aguardo {int} segundos")
public void aguardar(int segundos) {
    page.waitForTimeout(segundos * 1000);
}
```

---

### **Passo 9: Organizando Métodos por Contexto**

Use comentários para organizar por seção:

```java
// ==================== HOME ====================
@Então("[HOME] o título deve conter {string}")
public void validarTitulo(String texto) { }

// ==================== NAVEGAÇÃO ====================
@Quando("[NAV] clico em {string}")
public void clicarPorTexto(String texto) { }
```

---

### **Passo 10: Configurar o Runner do Cucumber**

Crie uma classe chamada `ExecutorDeTestes.java`:

```java
package com.automacao;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.automacao.steps",
    language = "pt",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/relatorio-automacao.html"
    }
)
public class ExecutorDeTestes {
}
```

**Parâmetros importantes:**
- `features`: onde estão os arquivos `.feature`
- `glue`: onde estão as step definitions (Cucumber procura aqui)
- `language`: idioma dos passos (`pt` = português)
- `plugin`: define saída do console e relatório HTML em `target/cucumber-reports/relatorio-automacao.html`
- Caminho completo: `C:\QAx\estudo-automacao-porto\target\cucumber-reports\relatorio-automacao.html`
- Nome do arquivo de relatório: `relatorio-automacao.html`

---

## 🔍 Comparação: Arquivo .feature vs Java

### Arquivo .feature
```gherkin
Cenário: 01 - Validar título inicial
  Quando acesso o site "https://playwright.dev"
  Então o título deve conter "Playwright"
```

### Arquivo Java (PassosDeTeste.java)
```java
@Quando("acesso o site {string}")
public void acessarSite(String url) {
    page.navigate(url);
}

@Então("o título deve conter {string}")
public void validarTitulo(String texto) {
    assert page.title().contains(texto);
}
```

---

## 🎯 Padrão de Nomenclatura Usado

```
[CONTEXTO] descrição do passo
```

**Exemplos:**
- `[HOME]` - passos na página inicial
- `[NAV]` - passos de navegação
- `[BUSCA]` - passos de busca
- `[RESULT]` - passos de validação de resultados
- `[TEMA]` - passos de tema/customização

**Benefício**: Agrupa passos por seção, facilitando a leitura.

---

## 📊 Fluxo de Execução

```
Arquivo .feature
        ↓
Cucumber lê os passos
        ↓
Procura match em com.automacao.steps
        ↓
Encontra o método @Dado/@Quando/@Então
        ↓
Executa o código Java
        ↓
Resultado: ✅ Passou ou ❌ Falhou
```

---

## ✅ Checklist para Criar Step Definitions

- [ ] Pacote criado: `com.automacao.steps`
- [ ] Classe criada: `PassosDeTeste.java`
- [ ] Package declarado: `package com.automacao.steps;`
- [ ] Importações adicionadas: Cucumber + Playwright/Selenium
- [ ] Classe instancia o driver/browser
- [ ] Cada passo do .feature tem um método anotado
- [ ] Placeholders (`{string}`, `{int}`) correspondem aos parâmetros
- [ ] Métodos têm nomes descritivos em português
- [ ] Classe de runner configurada com `glue = "com.automacao.steps"`

---

## 🚀 Próximos Passos

1. **Executar os testes**: `mvn test`
2. **Adicionar mais cenários**: Edite `Documentacao.feature`
3. **Mapear os novos passos**: Adicione métodos em `PassosDeTeste.java`
4. **Melhorar os seletores**: Use Playwright Codegen para gerar localizadores

---

## 📞 Dúvidas Comuns

**P: Por que usar `{string}` em vez de só escrever "url"?**
R: `{string}` é um placeholder que o Cucumber reconhece. Permite reutilizar o mesmo passo com valores diferentes.

**P: Posso ter mais de uma classe de steps?**
R: Sim! Crie `LoginSteps.java`, `BuscaSteps.java`, etc. no mesmo pacote `com.automacao.steps`.

**P: Como validar se algo não existe?**
R: Use `assertFalse(page.getByText("texto").isVisible())` ou lance exceção se não estiver visível.

**P: Posso chamar um passo de outro?**
R: Não diretamente, mas pode chamar métodos auxiliares normais (sem anotações).

