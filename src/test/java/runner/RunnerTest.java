package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "io.qameta.allure.cucumber3jvm.AllureCucumber3Jvm",
        features = "src/test/java/features",
        glue = "stepdefs",
        tags = "@TEST",
        snippets = SnippetType.CAMELCASE
)
public class RunnerTest {
}
