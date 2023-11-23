package com.example.StepDefinitions;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.example.PageObjects.FileUploadPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FileUploadStep {

	// We should intialize the object of page class to call their methos
	FileUploadPage upload = new FileUploadPage();

	// public static final String Title = "Testing" + RandomStringUtils.random(5,
	// true, true);

	public FileUploadStep() {

	}

	@Given("^User is on Login page$")
	public void User_is_on_Login_page() throws Throwable {

		upload.userIsOnLoginPage();

	}

	@When("^User enter token as (.+)$")
	public void User_enter_token_as_token(String token) throws Throwable {

		upload.enterTokenValue(token);

	}

	@Then("^User click on SignIn Button and verify error message$")
	public void User_click_on_SignIn_Button_and_verify_error_message() throws Throwable {

		upload.clickOnSignInButton();
		upload.verifyErrorMessage();

	}

	@Then("^User click on SignIn Button$")
	public void User_click_on_SignIn_Button() throws Throwable {

		upload.clickOnSignInButton();
		upload.veifyWelcomeScreen();

	}

	@Then("^User upload location csv file$")
	public void User_upload_location_csv_file() throws Throwable {

		upload.uploadCsvFile();

	}

	@Then("^User upload  invalid location csv file$")
	public void User_upload_invalid_location_csv_file() throws Throwable {

		upload.uploadInvalidCsvFile();

	}

	@Then("^User click on Create Route button$")
	public void User_click_on_Create_Route_button() throws Throwable {

		upload.clickOnCreateRouteButton();

	}

	@Then("^verify invalid file error message$")
	public void verify_invalid_file_error_message() throws Throwable {

		upload.verifyInvalidFileErrorMessage();

	}

	@Then("^User click on Next button$")
	public void User_click_on_Next_button() throws Throwable {

		upload.clickOnNextButton();

	}

	@Then("^verify that added location display on route listing screen$")
	public void verify_that_added_location_display_on_route_listing_screen() throws Throwable {

		upload.verifyAddedlocation();

	}

	@Then("^User click on Map section$")
	public void User_click_on_Map_section() throws Throwable {

		upload.clickOnMapSection();

	}

	@When("^User add new location as (.+)$")
	public void User_add_new_location_as_AddLocation(String AddLocation) throws Throwable {

		upload.enterLocation(AddLocation);

	}

	@Then("^User click on Add location icon$")
	public void User_click_on_Add_location_icon() throws Throwable {

		upload.clickOnAddLocationIcon();

	}

	@Then("^User verify upload file location data display on Map section$")
	public void User_verify_upload_file_location_data_display_on_Map_section() throws Throwable {

		upload.verifyUploadFileLocation();

	}
}