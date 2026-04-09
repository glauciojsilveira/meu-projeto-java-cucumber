package com.automacao.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.logging.Level; // Para silenciar logs
import java.util.logging.Logger; // Para silenciar logs

public class Hooks {
    public static WebDriver driver;

    @Before
    public void setup() {
        // 1. Silencia o WebDriverManager (tira as linhas de INFO do driver)
        System.setProperty("wdm.loglevel", "OFF");

        // 2. Silencia os logs do Selenium (CDP e outros)
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        // 3. Silencia as mensagens de inicialização do ChromeDriver
        System.setProperty("webdriver.chrome.silentOutput", "true");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-logging"});

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }


    @After
    public void tearDown(Scenario scenario) {
        // Se o teste falhar, o Selenium tira um print e coloca no relatório
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Evidencia de Erro");
            } catch (Exception e) {
                System.out.println("Erro ao tirar print: " + e.getMessage());
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
