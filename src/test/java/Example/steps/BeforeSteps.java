package Example.steps;
;
import Example.steps.SaveData.SaveData;
import Example.steps.SaveData.ScenarioContext;
import com.google.common.collect.ImmutableMap;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.When;
import java.util.Objects;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class BeforeSteps extends BaseRestApi {
//    @Before
//    @When("Авторизуюсь")
//    public static void authentication() {
//        if (Objects.isNull(getCookie())) {
//            allureEnvironmentWriter(    // Добавляет в отчет Allure Environment
//                    ImmutableMap.<String, String>builder()
//                            .put("URL", "Адрес свагера например, для вставки в отчет Allure")
//                            .build());
//        }
//    }

    @After
    public static void afterScenario(Scenario scenario) {
        logger.info("Scenario: " + scenario.getName() + "          Status: " + scenario.getStatus());
        ScenarioContext.clearContext();
    }
}
