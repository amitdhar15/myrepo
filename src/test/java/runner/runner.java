/**
 * 
 */

package runner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.herokuapp.group.id.cucumber.WebConnector;

import cucumber.api.junit.Cucumber;

//runner class 
@RunWith(LoggingCucumber.class) 
@Cucumber.Options(
		
//		features="features",
		features={"features",},
		tags = { "@regression","~@off" },
		glue={"stepDefination"},
		format = {/*"html:target/cucumber-report/selenium1",*/"json-pretty:target/cucumber-report/selenium1/cucumber.json"}) 


public class runner { 
	
	@AfterClass
	public static void afterAll() {
		try {
			WebConnector.quitAll();
		} catch (Exception e) {
			System.out.println("Unable to quit all browser windows because of error: " + e.getMessage());
		}
	}
	
}





































// runner class
//@ExtendedCucumberOptions(jsonReport = "target/cucumber-report/selenium1/cucumber.json",
//retryCount = 3,
//detailedReport = true,
//detailedAggregatedReport = true,
//overviewReport = true,
//coverageReport = true,
//jsonUsageReport = "target/cucumber-usage.json",
//usageReport = true,
//toPDF = true,
//excludeCoverageTags = {"@flaky" },
//includeCoverageTags = {"@passed" },
//outputFolder = "target")
/*@RunWith(Cucumber.class)
@Cucumber.Options(features = "features", tags = { "~@ignore" }, glue = { "stepDefination" }, format = {
		 "html:target/cucumber-report/selenium1", "json-pretty:target/cucumber-report/selenium1/cucumber.json" })

public class runnerIDP {

}*/


/*@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        retryCount = 3,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        //coverageReport = true,
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        toPDF = true,
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        outputFolder = "target")
@Cucumber.Options(format = { "html:target/cucumber-html-report",
        "pretty:target/cucumber-report/selenium1/cucumber.json",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "features" },
       // glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = { "~@ignore" })
public class runnerIDP {
}*/
