package app;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(

		features = "use_cases", glue = "app", plugin = "html:target/cucumber/test.html"

)

public class Configuration {

}
