# Análise Atualizada - Passos de Teste (08/04/2026)

## Status geral

O projeto de testes está funcional.

Validação executada localmente em **08/04/2026** com:

```bash
mvn test
```

Resultado:
- 5 cenários executados
- 5 cenários aprovados
- 0 falhas / 0 erros

---

## O que existe hoje no projeto de testes

### Arquivos e responsabilidades

- `src/test/java/com/automacao/steps/PassosDeTeste.java`
  - Contém os steps Cucumber (`@Dado`, `@Quando`, `@Então`) em português.
  - Usa Selenium com `WebDriverWait` (espera explícita).

- `src/test/java/com/automacao/steps/Hooks.java`
  - Faz setup/teardown por cenário com `@Before` e `@After`.
  - Inicializa `ChromeDriver` via `WebDriverManager`.
  - Configura `implicitlyWait(5s)` e maximiza janela.

- `src/test/java/com/automacao/steps/Runner.java`
  - Runner JUnit 4 do Cucumber.
  - Gera relatório HTML em `target/cucumber-reports/relatorio-automacao.html`.
  - Caminho completo: `C:\QAx\estudo-automacao-porto\target\cucumber-reports\relatorio-automacao.html`.
  - Nome do arquivo de relatório: `relatorio-automacao.html`.

- `src/test/resources/features/Documentacao.feature`
  - 5 cenários cobrindo:
  - validação de título
  - navegação para docs
  - busca por termo
  - troca de tema
  - troca de linguagem para Java

---

## Passos implementados em `PassosDeTeste.java`

- `[HOME] que acesso o site {string}`
- `[HOME] o título deve conter {string}`
- `[NAV] clico em {string}`
- `[DOCS] a URL deve ter {string}`
- `[BUSCA] pesquiso por {string}`
- `[RESULT] o primeiro item deve ser {string}`
- `[TEMA] clico para alternar o tema`
- `[TEMA] verifico se o fundo mudou`
- `[MENU] escolho a linguagem {string}`
- `[DOCS] o código deve mostrar {string}`

Cobertura atual está coerente com o arquivo `.feature`.

---

## Ajustes necessários/recomendados

1. Evitar inicialização de `wait` e `driver` como campos fixos em `PassosDeTeste`.
   - Hoje está assim:
   - `WebDriver driver = Hooks.driver;`
   - `WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));`
   - Isso funciona no cenário atual, mas é frágil para evolução (paralelismo e ciclo de vida do driver).
   - Melhor prática: criar o `wait` dentro dos métodos usando o `Hooks.driver` atual.

2. Escolher uma estratégia principal de espera.
   - O projeto usa **espera implícita** (`Hooks`) e **explícita** (`PassosDeTeste`) ao mesmo tempo.
   - Recomendação: manter só explícita para previsibilidade.

3. Corrigir warnings de build Maven.
   - No `pom.xml`, os plugins `maven-compiler-plugin`, `maven-surefire-plugin` e `exec-maven-plugin` estão sem `<version>`.
   - O build passa, mas o Maven alerta que isso pode quebrar em versões futuras.

4. Tratar aviso de CDP do Selenium/Chrome.
   - Durante execução apareceu aviso de incompatibilidade CDP para Chrome 146.
   - Não quebrou os testes, mas é um risco futuro de estabilidade para recursos DevTools.

---

## Conclusão

O conjunto atual de testes está saudável e passando.

O arquivo `PassosDeTeste.java` não está “quebrado”, mas a análise anterior estava incompleta/desatualizada. A principal melhoria técnica agora é reforçar a robustez do gerenciamento de `driver/wait` e eliminar warnings de configuração no `pom.xml`.
