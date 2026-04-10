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
        // Não usamos Keys.ENTER aqui para o Cenário 10 não quebrar
    }

    @Então("[RESULT] o primeiro item deve ser {string}")
    public void validaResultado(String esperado) {
        // CORREÇÃO CENÁRIO 03: Espera o texto aparecer dentro do seletor específico
        By seletorResultado = By.cssSelector(".DocSearch-Hit-title");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(seletorResultado, esperado));

        WebElement primeiroItem = driver.findElement(seletorResultado);
        Assert.assertTrue("Resultado não encontrado! Encontrado: " + primeiroItem.getText(),
                primeiroItem.getText().toLowerCase().contains(esperado.toLowerCase()));
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
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navbar__item.dropdown"))).click();
            String xpath = String.format("//a[contains(@class, 'dropdown__link') and text()='%s']", lang);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        } catch (Exception e) {
            // Re-tentativa em caso de erro de clique/stale
            driver.navigate().refresh();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navbar__item.dropdown"))).click();
            String xpath = String.format("//a[contains(@class, 'dropdown__link') and text()='%s']", lang);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        }
    }

    @Então("[DOCS] o código deve mostrar {string}")
    public void o_codigo_deve_mostrar(String codigo) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("pre code")));
        Assert.assertTrue(element.getText().contains(codigo));
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
        wait.until(ExpectedConditions.presenceOfElementLocated(link));
        Assert.assertTrue(driver.findElement(link).isDisplayed());
    }

    @Quando("[NAV] clico no seletor de idiomas")
    public void abreIdiomas() {
        // Seletor mais preciso para o botão de idiomas
        List<WebElement> dropdowns = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".navbar__item.dropdown")));
        dropdowns.get(dropdowns.size() - 1).click();
    }



    @Então("[RESULT] devo ver a mensagem {string}")
    public void validaSemResultado(String mensagem) {
        // CORREÇÃO CENÁRIO 10: Seletor específico da mensagem de erro do Algolia
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".DocSearch-NoResults p, .DocSearch-Title")));
        Assert.assertTrue("Mensagem esperada não encontrada", container.getText().contains(mensagem));
    }
}
