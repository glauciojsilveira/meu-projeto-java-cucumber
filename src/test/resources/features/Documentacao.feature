#language: pt
Funcionalidade: Explorar o site Playwright

  Contexto:
    Dado [HOME] que acesso o site "https://playwright.dev"

  Cenário: 01 - Validar título inicial
    Então [HOME] o título deve conter "Playwright"

  Cenário: 02 - Abrir documentação
    Quando [NAV] clico em "Get started"
    Então [DOCS] a URL deve ter "intro"

  Cenário: 03 - Buscar por Localizadores
    Quando [BUSCA] pesquiso por "Locators"
    Então [RESULT] o primeiro item deve ser "Locators"

  Cenário: 04 - Mudar para modo escuro
    Quando [TEMA] clico para alternar o tema
    Então [TEMA] verifico se o fundo mudou

  Cenário: 05 - Selecionar Java como linguagem
    Quando [NAV] clico em "Get started"
    E [MENU] escolho a linguagem "Java"
    Então [DOCS] o código deve mostrar "Playwright.create()"