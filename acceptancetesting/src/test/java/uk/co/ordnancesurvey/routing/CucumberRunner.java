package uk.co.ordnancesurvey.routing;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import uk.co.ordnancesurvey.webtests.IntegrationTestProperties;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Category(IntegrationTestProperties.class)
@Cucumber.Options(format = { "html:target/cucumber-reports/html", "json:target/cucumber-reports/cucumber.json" }, glue="uk.co.ordnancesurvey.routing",features = {"src/test/cucumber/Routing-Exeter.feature","src/test/cucumber/DPNRouting.feature"}, tags = { "~@RegressionLiveDataTests","@Routing"})
public class CucumberRunner {
	
    
   

}









































































































































































