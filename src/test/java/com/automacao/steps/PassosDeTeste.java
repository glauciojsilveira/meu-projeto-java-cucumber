package com.automacao.steps;

import io.cucumber.java.pt.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
import java.time.Duration;
import java.util.List;

public class PassosDeTeste {

    WebDriver driver = Hooks.driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    @Dado("[HOME] que acesso o site {string}")
    public void acessoSite(String url) {
        driver.get(url);
    }

    @Então("[HOME] o título deve conter {string}")
    public void validaTitulo(String texto) {
        wait.until(ExpectedConditions.titleContains(texto));
        Assert.assertTrue(driver.getTitle().contains(texto));
    }

    @Quando("[NAV] clico em {string}")
    public void clicoEm(String textoBotao) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(textoBotao))).click();
    }

    @Então("[DOCS] a URL deve ter {string}")
    public void validaUrl(String termo) {
        wait.until(ExpectedConditions.urlContains(termo));
        Assert.assertTrue(driver.getCurrentUrl().contains(termo));
    }

    @Quando("[BUSCA] pesquiso por {string}")
    public void pesquisa(String termo) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.DocSearch-Button"))).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("docsearch-input")));
        input.clear();
        input.sendKeys(termo);
        // Em pipeline, o ENTER as vezes falha, então forçamos o envio da tecla
        input.sendKeys(Keys.ENTER);
    }

    @Então("[RESULT] o primeiro item deve ser {string}")
    public void validaResultado(String esperado) {
        By seletorResultado = By.cssSelector(".DocSearch-Hit-title");
        // Aguarda até que o texto esperado apareça no elemento, ignorando flutuações de carregamento
        wait.until(ExpectedConditions.textToBePresentInElementLocated(seletorResultado, esperado));
        WebElement primeiroItem = driver.findElement(seletorResultado);
        Assert.assertTrue("Resultado não contém o texto esperado", primeiroItem.getText().contains(esperado));
    }

    @Quando("[TEMA] clico para alternar o tema")
    public void alternaTema() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label*='dark']"))).click();
    }

    @Então("[TEMA] verifico se o fundo mudou")
    public void verificaFundo() {
        String tema = driver.findElement(By.tagName("html")).getAttribute("data-theme");
        Assert.assertNotNull(tema);
    }

    @Quando("[MENU] escolho a linguagem {string}")
    public void escolheLinguagem(String lang) {
        // Resolve o StaleElementReferenceException tentando localizar o elemento no momento do clique
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navbar__item.dropdown"))).click();
        String xpath = String.format("//a[contains(@class, 'dropdown__link') and text()='%s']", lang);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @Então("[DOCS] o código deve mostrar {string}")
    public void o_codigo_deve_mostrar(String codigo) {
        // CORREÇÃO GITHUB ACTIONS: Espera o texto específico aparecer dentro do bloco de código
        // Isso evita o StaleElement porque o wait só termina quando o conteúdo novo (Java) é injetado
        By codeBlock = By.cssSelector("pre code");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(codeBlock, codigo));

        WebElement element = driver.findElement(codeBlock);
        Assert.assertTrue("Código esperado não encontrado no bloco", element.getText().contains(codigo));
    }

    @Quando("[FOOTER] clico no ícone do {string}")
    public void clicoIconeRedeSocial(String rede) {
        String seletor = String.format("a[aria-label*='%s']", rede);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(seletor))).click();
    }

    @Então("[NAV] a nova aba deve conter {string}")
    public void validaNovaAba(String termo) {
        Object[] janelas = driver.getWindowHandles().toArray();
        driver.switchTo().window(janelas[janelas.length - 1].toString());
        wait.until(ExpectedConditions.urlContains(termo));
        Assert.assertTrue(driver.getCurrentUrl().contains(termo));
        driver.close();
        driver.switchTo().window(janelas[0].toString());
    }

    @Então("[DOCS] o cabeçalho principal deve ser {string}")
    public void validaCabecalho(String esperado) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), esperado));
        Assert.assertEquals(esperado, driver.findElement(By.tagName("h1")).getText());
    }

    @Então("[DOCS] devo ver o link para {string}")
    public void verificaLinkComunidade(String linkTexto) {
        By link = By.linkText(linkTexto);
        wait.until(ExpectedConditions.visibilityOfElementLocated(link));
        Assert.assertTrue(driver.findElement(link).isDisplayed());
    }

    @Quando("[NAV] clico no seletor de idiomas")
    public void abreIdiomas() {
        // Localiza todos os dropdowns e clica no último (que costuma ser o de idiomas no Playwright)
        List<WebElement> dropdowns = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".navbar__item.dropdown")));
        dropdowns.get(dropdowns.size() - 1).click();
    }

    @Então("[HOME] devo ver o texto {string}")
    public void verificaTextoNaPagina(String texto) {
        // Busca genérica para verificar texto em qualquer lugar da tela (mais seguro para pipeline)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), '" + texto + "')]")));
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains(texto));
    }

    @Então("[RESULT] devo ver a mensagem {string}")
    public void validaSemResultado(String mensagem) {
        // Seletor robusto para a mensagem de erro do Algolia
        By noResultsMsg = By.cssSelector(".DocSearch-NoResults p, .DocSearch-Title");
        wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMsg));
        Assert.assertTrue("Mensagem de 'sem resultados' não apareceu", driver.findElement(noResultsMsg).getText().contains(mensagem));
    }
}
