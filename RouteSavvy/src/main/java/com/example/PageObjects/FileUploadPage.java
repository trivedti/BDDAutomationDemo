package com.example.PageObjects;

import com.example.Utilities.GenericUtils;
import com.example.Utilities.PropertyFileOperations;
import cucumber.app.enums.FileNames;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FileUploadPage extends GenericUtils {

	public FileUploadPage() {
		// TODO Auto-generated constructor stub
	}

	PropertyFileOperations locators;
	static String Email;
	// public WebDriver driver;

	static Logger log = LogManager.getLogger(FileUploadPage.class);

	{
		locators = new PropertyFileOperations(FileNames.RouteSavvy1);
		log.info("STEP: Assets Page Locators loaded into memory");
	}

	public void userIsOnLoginPage() {

		verifyText(locators.getKey("VerifyLoginText"), "Sign In");
		log.info("STEP: Login Page Text display" + "VerifyLoginText");
	}

	public void enterTokenValue(String token) {
		enterText(locators.getKey("Token"), token);
		log.info("STEP:Enter Token value successfully" + "Token");
	}

	public void verifyErrorMessage() {
		verifyText(locators.getKey("InvalidTokenMessage"), "Your login token is invalid");
		log.info("STEP:verify invalid token message" + "InvalidTokenMessage");
	}

	public void clickOnSignInButton() {
		clickOnElement(locators.getKey("SignIn"));
		log.info("STEP:Successfully click On SignIn Button" + "SignIn");
	}

	public void veifyWelcomeScreen() {
		verifyText(locators.getKey("DashboardScreen"), "Welcome to RouteSavvy");
		log.info("STEP:verify welcome screen message" + "DashboardScreen");
	}

	public void uploadCsvFile() {
		Csvuploading(locators.getKey("UploadFile"));
		log.info("STEP:Upload Csv file successfully " + "UploadFile");
	}

	public void uploadInvalidCsvFile()

	{
		InvalidCsvuploading(locators.getKey("UploadFile"));
		log.info("STEP:Upload InvalidCsv file successfully " + "UploadFile");
	}

	public void clickOnCreateRouteButton() {
		clickOnElement(locators.getKey("CreateRouteButton"));
		log.info("STEP:Successfully click On CreateRouteButton" + "CreateRouteButton");
	}

	public void verifyInvalidFileErrorMessage() {
		verifyText(locators.getKey("InvalidFileErrorMessage"),
				"If the Match Type is not Address then we couldn't locate exactly and this location is less accurate and should not be used for routing. To fix addresses go to the Previous step(s) to upload fixed data or change column headers.");
		log.info("STEP: verify location error message locationA" + "InvalidFileErrorMessage");
	}

	public void clickOnNextButton() {
		clickOnElement(locators.getKey("NextButton"));
		clickOnElement(locators.getKey("NextButton"));

		wait(3000);
		clickOnElement(locators.getKey("clickOnCloseIcon"));
		log.info("STEP:Successfully click on NextButton" + "NextButton");
	}

	public void verifyAddedlocation() {
//		verifyText(locators.getKey("CustomerALocation"), "Customer A");
//		log.info("STEP: verify Added locationA" + "CustomerALocation");
//		
//		verifyText(locators.getKey("CustomerGLocation"), "Customer F");
//		log.info("STEP: verify added locationB" + "CustomerBLocation");

		WebElement element = getDriver().findElement(By.xpath("//div[@id='route-stops']"));

		System.out.println("Optimized route display : " + element.getText());

//			log.info("STEP:Optimized route display : " + element.getText());
	}

	public void clickOnMapSection() {
		clickOnElement(locators.getKey("MapSection"));
		log.info("STEP:Successfully click On Map section" + "MapSection");
	}

	public void enterLocation(String AddLocation) {
		enterText(locators.getKey("EnterAddress"), AddLocation);
		keyDown(locators.getKey("EnterAddress"));

		wait(6000);
		log.info("STEP:Enter loaction value successfully" + "Token");
	}

	public void clickOnAddLocationIcon() {
		clickOnElement(locators.getKey("AddressSerchIcon"));

		wait(8000);
		log.info("STEP:Successfully click On Address Search Icon" + "AddressSerchIcon");
	}

	public void verifyUploadFileLocation() {
		wait(5000);
		WebElement element = getDriver().findElement(By.xpath("//div[@id='locations-locations']"));

		System.out.println("Upload File location display : " + element.getText());

//		log.info("STEP:Upload File location display :" +  element.getText());
	}

}
