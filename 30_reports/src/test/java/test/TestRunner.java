package test;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.ConfigurationParameters;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@SelectClasspathResource("features")
@ConfigurationParameters({
        @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps"),
        @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@happy and not @createBoard"),
        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "json:build/cucumber-report/json/cucumber-report.json")
})
public class TestRunner {
}
