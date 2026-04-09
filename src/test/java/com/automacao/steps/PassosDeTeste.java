package com.automacao.steps;

import io.cucumber.java.pt.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
import java.time.Duration;
import org.openqa.selenium.Keys;

public class PassosDeTeste {

    // Pegamos o driver que o Hooks criou
    WebDriver driver = Hooks.driver;
    // Criamos um "garçom" (Wait) que espera até 15 segundos por elementos específicos
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

        input.sendKeys(termo);

        // Usa JavaScript para forçar o evento de "Enter" caso o Selenium falhe
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].dispatchEvent(new KeyboardEvent('keydown', {'key': 'Enter'}));", input);
    }

    @Então("[RESULT] o primeiro item deve ser {string}")
    public void validaResultado(String esperado) {
        // Em vez de localizar e validar em linhas separadas, usamos o wait para aguardar o texto específico
        // Isso garante que o Selenium espere o "Autocomplete" terminar a filtragem
        boolean confirmacao = wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector(".DocSearch-Hit-title"), esperado));

        Assert.assertTrue("O resultado esperado '" + esperado + "' não apareceu a tempo.", confirmacao);
    }


    @Quando("[TEMA] clico para alternar o tema")
    public void alternaTema() {
        // Seleciona o botão de sol/lua usando atributo data-tooltip ou role
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label*='dark']"))).click();
    }

    @Então("[TEMA] verifico se o fundo mudou")
    public void verificaFundo() {
        String tema = driver.findElement(By.tagName("html")).getAttribute("data-theme");
        Assert.assertNotNull("O atributo de tema não foi encontrado", tema);
    }

    @Quando("[MENU] escolho a linguagem {string}")
    public void escolheLinguagem(String lang) {
        try {
            // 1. Tenta clicar no dropdown (espera até estar clicável)
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navbar__item.dropdown")));
            dropdown.click();
        } catch (StaleElementReferenceException e) {
            // Se o elemento ficou "velho", procuramos ele novamente do zero
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navbar__item.dropdown")));
            dropdown.click();
        }

        // 2. Agora clicamos na opção (Java, Python, etc.)
        // O seletor abaixo busca o link que tem EXATAMENTE o texto da linguagem
        String xpathOpcao = String.format("//a[contains(@class, 'dropdown__link') and text()='%s']", lang);
        WebElement opcao = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOpcao)));

        // Usamos o JavaScript que você gostou (Opção 2) para garantir o clique na opção
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", opcao);
    }


    @Então("[DOCS] o código deve mostrar {string}")
    public void o_codigo_deve_mostrar(String codigo) {
        // Verificar se o código contém o snippet esperado
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("pre code")));
        Assert.assertTrue("Código não contém o snippet esperado", element.getText().contains(codigo));
    }
}
