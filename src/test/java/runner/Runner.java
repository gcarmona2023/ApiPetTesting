package runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/"},
        glue = "steps",
        tags = "@Pets or @Inventory or @Users"  //You can use @Pets or @Inventory or @Users
)

public class Runner {
}
