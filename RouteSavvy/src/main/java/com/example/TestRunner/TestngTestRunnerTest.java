package com.example.TestRunner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//@RunWith(Cucumber.class)
@CucumberOptions

(

		features = { "classpath:feature/01login.feature", "classpath:feature/02UploadValidFile.feature",
				"classpath:feature/03UploadInvalidFile.feature" },
		// features = "classpath:feature/042AddRemoveBaseQuestion.feature",

		glue = "com.example.StepDefinitions",
		// monochrome=false,
		// tags = "@test1",
		////// glue="stepDefinitions",stepNotifications=true,tags="@SmokeTest or
		////// @RegressionTest",
		// glue="stepDefinitions",stepNotifications=true,tags="@SmokeTest and
		////// @RegressionTest",
		// glue="stepDefinitions",stepNotifications=true,tags="not @SanityTest"
		// plugin= {"html:target/cucumber.html",
		////// "json:target/cucumber.json","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","rerun:target/failed_scenarios.txt"}
		// ),

		// plugin =
		// {"pretty","html:target/cucumber.html","json:target/cucumber.json","junit:target/cucumber.xml","html:target/cucumber.html",
		// "json:target/cucumber.json","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","rerun:target/failed_scenarios.txt"})
		plugin = { "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })

// AbstractTestNGCucumberTests - This is class which provided all wrappers to
// run our cucumber test through TestNG
public class TestngTestRunnerTest extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
