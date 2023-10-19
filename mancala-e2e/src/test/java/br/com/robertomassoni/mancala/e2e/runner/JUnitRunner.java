package br.com.robertomassoni.mancala.e2e.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/reports/report.html",
                "json:target/reports/cucumber.json",
                "timeline:target/reports/timeline"},
        tags = "@Mancala",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        stepNotifications = true,
        features = "classpath:features",
        glue = {"br.com.robertomassoni.mancala.e2e.steps",
                "br.com.robertomassoni.mancala.e2e.configuration"})
public class JUnitRunner {

}
