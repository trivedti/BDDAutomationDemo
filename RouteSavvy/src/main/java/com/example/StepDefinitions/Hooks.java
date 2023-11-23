package com.example.StepDefinitions;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.example.Utilities.GenericUtils;
import app.constants.ApplicationConstants;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    public static WebDriver driver;
    public static GenericUtils genericUtils;

    @Before
    public static void initializationOfBrowser() {
        genericUtils = new GenericUtils();
        genericUtils.initBrowser(ApplicationConstants.browser, ApplicationConstants.URL);
        System.out.println("============Chrome Browser Opened Succesfully!!!!=========");

        //Following Code is for Headless Automation
        // genericUtils = new GenericUtils();
        // genericUtils.initBrowserHeadless(ApplicationConstants.browser, ApplicationConstants.URL);
        // System.out.println("============Chrome Browser Opened In Headless Mode Succesfully!!!!=========");
    }

    @AfterStep
    public void addScreenshot(Scenario scenario) throws IOException {
        File screenshot = ((TakesScreenshot) genericUtils.getDriver()).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
        scenario.attach(fileContent, "image/png", "screenshot");
    }

	
	  @After
	  public static void closeBrowser() throws IOException {
	  
	  GenericUtils genericUtils = new GenericUtils();
	  genericUtils.terminateBrowser(); System.out.
	  println("============Chrome Browser Closed Succesfully!!!!==========");
	  
	  }
	 
}

// @After
// public static void takeScreenShotWithBrowserClosedSentReport(Scenario
// scenario) throws IOException, MessagingException{
// // if (scenario.isFailed()) {
// GenericUtils genericUtils =new GenericUtils();
// genericUtils.captureScreen(genericUtils.getDriver(), "Test");
// File f = new File(System.getProperty("user.dir") + "/Reporting/" + "Test" +
// ".png");
// genericUtils.terminateBrowser();
// System.out.println("============close the Browser==========");
// // SendMailUser sendmailUser=new SendMailUser();
// // sendmailUser.execute();
// // System.out.println("Report has been sent");

// }
