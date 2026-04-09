package com.automacao.steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.automacao.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/relatorio-automacao.html" // Onde o relatório será salvo
        },
        monochrome = true
)
public class Runner {
}
