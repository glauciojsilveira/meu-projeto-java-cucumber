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
        // Clica na busca e espera o input estar visível e pronto
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.DocSearch-Button"))).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("docsearch-input")));
        input.clear();
        input.sendKeys(termo);
        // Não usamos ENTER aqui para permitir que o modal carregue os resultados organicamente
    }

    @Então("[RESULT] o primeiro item deve ser {string}")
    public void validaResultado(String esperado) {
        // CORREÇÃO CENÁRIO 03: Espera primeiro o container de resultados existir para evitar o NoSuchElement
        By seletorResultado = By.cssSelector(".DocSearch-Hit-title");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".DocSearch-Dropdown")));

        // Agora espera o texto específico aparecer
        wait.until(ExpectedConditions.textToBePresentInElementLocated(seletorResultado, esperado));

        String textoEncontrado = driver.findElement(seletorResultado).getText();
        Assert.assertTrue("Esperava '" + esperado + "' mas encontrou '" + textoEncontrado + "'",
                textoEncontrado.contains(esperado));
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
        // Tenta clicar no dropdown. Se der erro de elemento antigo, tenta de novo (comum em SPAs)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navbar__item.dropdown"))).click();
            String xpath = String.format("//a[contains(@class, 'dropdown__link') and text()='%s']", lang);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navbar__item.dropdown"))).click();
            String xpath = String.format("//a[contains(@class, 'dropdown__link') and text()='%s']", lang);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        }
    }

    @Então("[DOCS] o código deve mostrar {string}")
    public void o_codigo_deve_mostrar(String codigo) {
        // CORREÇÃO PIPELINE GITHUB: Localizamos o elemento sempre dentro do wait para evitar StaleElement
        By seletorCodigo = By.cssSelector("pre code");
        wait.until(ExpectedConditions.presenceOfElementLocated(seletorCodigo));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(seletorCodigo, codigo));

        Assert.assertTrue(driver.findElement(seletorCodigo).getText().contains(codigo));
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

    @Então("[RESULT] devo ver a mensagem {string}")
    public void validaSemResultado(String mensagem) {
        // Seletor para a mensagem de erro da busca (Algolia)
        By msgErro = By.cssSelector(".DocSearch-NoResults p, .DocSearch-Title");
        wait.until(ExpectedConditions.visibilityOfElementLocated(msgErro));
        Assert.assertTrue(driver.findElement(msgErro).getText().contains(mensagem));
    }
}
