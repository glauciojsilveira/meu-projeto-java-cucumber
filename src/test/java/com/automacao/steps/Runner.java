package com.automacao.steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Onde estão seus arquivos .feature
        glue = "com.automacao.steps",                        // Onde estão suas classes Java de Step Definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Gera um relatório HTML simples no 'target'
        monochrome = true,                      // Deixa a saída do terminal mais legível
        tags = "not @wip"                      // Executa todos os testes que NÃO possuem a tag @wip (Work In Progress)
)
public class Runner {
        // Esta classe não precisa de corpo, as anotações acima fazem todo o trabalho.
}

