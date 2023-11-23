package com.example.Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import app.constants.ApplicationConstants;
import app.constants.FilePaths;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.io.FileUtils;
// import org.apache.log4j.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
// import org.apache.log4j.PropertyConfigurator;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class GenericUtils {
	public static WebDriver driver;
	public static WebDriver driver2;
	public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	public static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();

	static Logger log = LogManager.getLogger(GenericUtils.class);
	// Logger log = Logger.getLogger("test");
	// PropertyConfigurator.configure(System.getProperty("user.dir") +
	// "/resources/log4j.properties");

	static String orderID;
	static String mobileNumber;
	static String mobNo;
	static String finaltitle;

	public GenericUtils() {
	}

	public GenericUtils(WebDriver driver2) {
		// super(driver2);
		this.driver2 = driver2;
	}

	public void initBrowser(String browserName, String url) {
		log.debug("STEP: " + browserName + " browser launched and load " + url + " in browser");
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			driverThread.set(new ChromeDriver(options));
			break;
		default:
			break;
		}
		getDriver().manage().window().maximize();
		getDriver().get(url);
		log.debug("STEP: Explicite wait set on WebDriver " + ApplicationConstants.EXP_WAIT);
		waitThread.set(new WebDriverWait(getDriver(), ApplicationConstants.EXP_WAIT));
	}

	// Following is for HeadLess Testing
	// Following is for HeadLess Testing
	@SuppressWarnings("unused")
	public void initBrowserHeadless(String browserName, String url) {
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			/// ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);

			// options.addArguments("--disable-gpu", "--window-size=1920,1080",
			// "--allow-insecure-localhost","--ignore-certificate-errors",
			// "--start-maximized", "--disable-extensions",
			// "--no-sandbox","--disable-dev-shm-usage","--remote-allow-origins=*");

			options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--allow-insecure-localhost",
					"--ignore-certificate-errors", "--start-maximized", "--disable-extensions", "--no-sandbox",
					"--disable-dev-shm-usage", "--remote-allow-origins=*");
			driverThread.set(new ChromeDriver(options));
			break;
		default:
			break;
		}

		getDriver().manage().window().maximize();
		getDriver().get(url);
		// getDriver().manage().deleteAllCookies();
		log.debug("STEP: Explicite wait set on WebDriver " + ApplicationConstants.EXP_WAIT);
		waitThread.set(new WebDriverWait(getDriver(), ApplicationConstants.EXP_WAIT));
		// ((JavascriptExecutor) getDriver()).executeScript("window.confirm =
		// function(){return true;}");
		// ((JavascriptExecutor) driver()).executeScript("window.confirm =
		// function(){return true;}");
	}

	public void terminateBrowser() {
		// getDriver().close();
		getDriver().quit();
		log.debug("STEP - Browser Quit");
	}

	public void waitForJSToLoad() {
		try {
			// Javascript Executor for ready state
			JavascriptExecutor j = (JavascriptExecutor) getDriver();
			if (j.executeScript("return document.readyState").toString().equals("complete")) {
				System.out.println("Page in ready state");
			}
		} catch (Exception exe) {
			System.out.println("Page not in ready state");
		}
	}

	public WebDriver getDriver() {
		return driverThread.get();
	}

	public WebDriver driver() {
		return driverThread.get();
	}

	public JavascriptExecutor jsDriver() {
		return (JavascriptExecutor) getDriver();
	}

	public void clickOnElementfor(String locator) {
		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		log.debug("STEP: click on element " + getLocatorValue(locator));
	}

	// public void clickOnElement(String locator) {

	// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator)));
	// Actions actions = new Actions(getDriver());
	// try {
	// WebElement element = getElement(locator);
	// //System.out.println("Get Text is : " + element.getText().toString());
	// actions.moveToElement(element).click().build().perform();

	// } catch (Exception e) {
	// Assert.fail("Issue While clicking", e.getCause());

	// }

	// }

	public void clickOnElement(String locator) {
		int count = 0;
		while (count < 3) {
			try {
				// waitForJSLoad();
				WebElement element = getElement(locator);
				element.click();
				break;

			} catch (Throwable e) {

				// System.out.println("Page not in ready state");
				wait(3000);
				count++;
			}
		}

	}

	public void clickOnElementForOTP(String locator) {

		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator)));
		Actions actions = new Actions(getDriver());
		try {
			WebElement element = getElement(locator);
			// System.out.println("Get Text is : " + element.getText().toString());
			// actions.moveToElement(element).click().build().perform();
			actions.moveToElement(element).doubleClick().build().perform();
			// actions.moveToElement(element).sendKeys(Keys.ENTER);
			Thread.sleep(5000);
		} catch (Exception e) {
			Assert.fail("Issue While clicking", e.getCause());

		}
	}

	// public void clickOnElement(String locator) {
	// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator)));
	// Actions actions = new Actions(getDriver());
	// try {

	// actions.sendKeys(Keys.ENTER).build().perform();

	// } catch (Exception e) {
	// Assert.fail("Issue While Enter tab", e.getCause());
	// }

	// }

	public void clickonElementJavaScript(String locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
		wait.until(ExpectedConditions.elementToBeClickable(getElement(locator)));
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", locator);
	}

	public void clickOnElementWithJS(String locator) {

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
		wait.until(ExpectedConditions.elementToBeClickable(getElement(locator)));
		getElement(locator).click();
		// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		log.debug("STEP: click on element " + getLocatorValue(locator));
	}

	public String getLocatorValue(String locator) {
		String value = null;
		try {
			value = locator.split(":-")[1];
			log.debug("STEP: Locatoe value" + value + " in " + locator);
		} catch (Exception e) {
			log.fatal(e.getMessage());
			log.fatal(e.getCause());
		}
		return value;
	}

	public WebElement getElement(String locator) {
		// WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
		// WebElement element =
		// getWait().until(ExpectedConditions.presenceOfElementLocated(getLocator(locator)));
		WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element;
	}

	public WebElement getElementWithoutWait(String locator) {
		WebElement element = (WebElement) getLocator(locator);

		jsDriver().executeScript("arguments[0].style.border='3px solid red'", element);
		return element;
	}

	public String getLocatorType(String locator) {
		String type = null;
		try {
			type = locator.split(":-")[0].replace("[", "").replace("]", "").toLowerCase();
			log.debug("STEP: Locatoe type " + type + " in " + locator);
		} catch (Exception e) {
			log.fatal(e.getMessage());
			log.fatal(e.getCause());
		}
		return type;
	}

	private By getLocator(String locator) {
		String locatorType = getLocatorType(locator);
		String locatorValue = getLocatorValue(locator);

		switch (locatorType) {
		case "xpath":
			return By.xpath(locatorValue);
		case "css":
			return By.cssSelector(locatorValue);
		case "id":
			return By.id(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "className":
			return By.className(locatorValue);
		case "linkText":
			return By.linkText(locatorValue);
		case "partialLinkText":
			return By.partialLinkText(locatorValue);

		}
		return null;
	}
	
	private By getListElements(String locator) {
		String locatorType = getLocatorType(locator);
		String locatorValue = getLocatorValue(locator);

		switch (locatorType) {
		case "xpath":
			return By.xpath(locatorValue);
		case "css":
			return By.cssSelector(locatorValue);
		case "id":
			return By.id(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "className":
			return By.className(locatorValue);
		case "linkText":
			return By.linkText(locatorValue);
		case "partialLinkText":
			return By.partialLinkText(locatorValue);
	
		}
		return null;
	}

	public static WebDriverWait getWait() {
		return waitThread.get();
	}

	// public boolean isElementDisplay(String locator) {
	// WebElement element = null;
	// boolean elementDisplay;
	// try {
	// wait(2000);
	// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator)));
	// element = getElement(locator);
	// elementDisplay = element.isDisplayed();
	// log.debug("STEP: Element displayed in screen viewport");
	// } catch (Exception e) {
	// // element = getElement(locator);
	// log.debug("STEP: Scroll till element to get visible in screen viewport");
	// //
	// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator)));
	// element = getElement(locator);
	// elementDisplay = element.isDisplayed();
	// log.debug("STEP: Retry to element display in screen viewport and status " +
	// element);
	// }
	// return elementDisplay;
	// }

	// public boolean isElementDisplay(String locator) {
	// try{

	// boolean ele= getElement(locator).isDisplayed();
	// if(ele==true)
	// {
	// System.out.println("Element Visible : " + locator);
	// }

	// }catch (NoSuchElementException e) {
	// System.out.println(e);
	// System.out.println("Failed to find element : " + locator);
	// }
	// return false;
	// }
	public boolean isElementDisplay(String locator) {
		WebElement element = getElement(locator);
		try {

			if (element.isDisplayed() || element.isEnabled()) {
				Assert.assertTrue(element.isEnabled());

			} else {

				Assert.fail(element.getText() + "false means Disabled or not present ");
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean isElementNotDisplay(String locator) {
		WebElement element = null;
		boolean elementDisplay;
		element = getElementWithoutWait(locator);
		elementDisplay = !element.isDisplayed();
		log.debug("STEP: Element not displayed in screen viewport");
		return elementDisplay;
	}

	public String enterTextWithClick(String locator, String strMessage) {
		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		// getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
		getElement(locator).clear();
		getElement(locator).sendKeys(strMessage);
		log.debug("STEP: " + strMessage + " is set inside element");

		/*
		 * getElement(locator).sendKeys(Keys.TAB); log.debug("STEP: " + strMessage +
		 * " is set tab from element");
		 */

		return strMessage;
	}

	public String enterText(String locator, String strMessage) {
		// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
		getElement(locator).clear();
		getElement(locator).sendKeys(strMessage);
		log.debug("STEP: " + strMessage + " is set inside element");

		/*
		 * getElement(locator).sendKeys(Keys.TAB); log.debug("STEP: " + strMessage +
		 * " is set tab from element");
		 */

		return strMessage;
	}

	public void selectDropDown(String locator, String strMessage) {
		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		wait(2000);
		getElement(locator).sendKeys(strMessage);
		wait(2000);
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.ENTER).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Enter tab", e.getCause());
		}
		log.debug("STEP: " + strMessage + " is selected");

	}

	public void selectDropDownWithScroll(String locator, String strMessage) {
		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		scrollUp();
		wait(2000);
		getElement(locator).sendKeys(strMessage);
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.ENTER).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Enter tab", e.getCause());
		}
		log.debug("STEP: " + strMessage + " is selected");

	}

	public void selectList(String locator, String strMessage) {
		WebElement dropDownListBox = getElement(locator);
		dropDownListBox.click();
		wait(3000);
		dropDownListBox.sendKeys(strMessage);
		wait(2000);
		dropDownListBox.sendKeys(Keys.ENTER);

		try {
			// dropDownListBox.sendKeys(Keys.TAB);
		} catch (Exception e) {
			Assert.fail("Issue While selecting value from dropdown", e.getCause());
		}
		log.debug("STEP: " + strMessage + " is selected");

	}

	public void SwitchWindowToChild() {
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		// String parentWindow = i1.next();
		String childWindow = i1.next();
		driver.switchTo().window(childWindow);
	}

	public void scrollTillElementDisplay(String locator) {
		// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator)));
		WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(3000);
		je.executeScript("arguments[0].scrollIntoView(true)", element);
		log.debug("STEP: Scroll till element");
	}

	public void scrollup() {

		// WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(3000);
		je.executeScript("window.scrollBy(0,-350)");
		log.debug("STEP: Scrolling up");
	}

	public void scrollUp() {
		// WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(2000);
		je.executeScript("window.scrollBy(-0,-350)");
		log.debug("STEP: Scroll up");
	}

	public void scrollUpWithKey() {
		// WebElement element = getElement(locator);
		Actions a = new Actions(getDriver());
		a.sendKeys(Keys.PAGE_UP).build().perform();
	}

	public void scrollDown() {

		// WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(3000);
		je.executeScript("window.scrollBy(0,350)");
		log.debug("STEP: Scrolling up");
	}

	public void scrollDownTillPageEnd() {

		// WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		je.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
	}

	public void pageRefresh() {
		getDriver().navigate().refresh();
		// System.out.println("Page has been refreshed");
	}

	public void onPageRefresh() {
		Actions action = new Actions(getDriver());
		action.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
		wait(5000);
	}

	public void wait(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void actionEnter() {
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.ENTER).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Enter ", e.getCause());
		}
	}

	public void EnterWithActionDownEnter(String locator, String strMessage) {
		getElement(locator).sendKeys(strMessage);
		wait(2000);
		Actions actions = new Actions(getDriver());
		try {
			wait(1000);
			actions.sendKeys(Keys.DOWN).build().perform();
			actions.sendKeys(Keys.ENTER).build().perform();
			wait(1000);
		} catch (Exception e) {
			Assert.fail("Issue While Enter tab", e.getCause());
		}
	}

	public void keyDown(String locator) {
		getElement(locator);
		wait(2000);
		Actions actions = new Actions(getDriver());
		try {

			actions.sendKeys(Keys.DOWN).build().perform();
			//actions.sendKeys(Keys.DOWN).build().perform();
			actions.sendKeys(Keys.ENTER).build().perform();

		} catch (Exception e) {
			Assert.fail("Issue While key down", e.getCause());
		}
	}

	public void EnterOnButton(String locator) {
		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator)));
		Actions actions = new Actions(getDriver());
		//
		try {

			// actions.sendKeys(Keys.DOWN).build().perform();
			actions.sendKeys(Keys.ENTER).build().perform();

		} catch (Exception e) {
			Assert.fail("Issue While Enter tab", e.getCause());
		}
	}

	public void refreshPage() {
		try {
			getDriver().navigate().refresh();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	// public static String takeScreenShot() {
	// try {
	// TakesScreenshot ts = (TakesScreenshot) getDriver();
	// return ts.getScreenshotAs(OutputType.BASE64);
	// } catch (Exception e) {
	// log.debug("FAIL: Unable to screenshot, due to " + e.getCause());
	// }
	// return null;
	// }

	public void takeScreenShotOnFailure(String fileName) throws IOException {

		File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + ".jpg"));
	}

	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Reporting/" + tname + ".png");
		FileUtils.copyFile(source, target);

	}

	public void switchToFrameindex(int iIndex) {
		try {
			getDriver().switchTo().frame(iIndex);
			log.debug("STEP: switch on frame " + iIndex);
		} catch (NoSuchFrameException e) {
			log.fatal("FAIL: Issue While Switching frame index " + iIndex);
			Assert.fail("Issue While Switching frame", e.getCause());
		}
	}

	public void switchToFrameNameorID(String strName) {
		try {
			getDriver().switchTo().frame(strName);
			log.debug("STEP: switch on frame " + strName);
		} catch (Exception e) {
			log.fatal("FAIL: Issue While Switching frame name " + strName);
			Assert.fail("Issue While Switching frame", e.getCause());
		}
	}

	public void switchToDefaultFrame() {
		try {
			getDriver().switchTo().defaultContent();
			log.debug("STEP: switch to default screen");
		} catch (Exception e) {
			log.debug("FAIL: Unable to switch to main browser, issue: " + e.getCause());
			Assert.fail(e.getMessage());
		}
	}

	public void mouseOver(String locator) {
		Actions actions = new Actions(getDriver());
		try {
			actions.moveToElement(getElement(locator)).perform();

		} catch (Exception e) {
			Assert.fail("Issue While mouse over", e.getCause());
		}
	}

	public void actionTab() {
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.TAB).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Clicking TAB", e.getCause());
		}
	}

	public void actionDoubleTab() {
		Actions actions = new Actions(getDriver());
		try {
			actions.sendKeys(Keys.TAB, Keys.TAB).build().perform();
		} catch (Exception e) {
			Assert.fail("Issue While Clicking Double TAB", e.getCause());
		}
	}

	public void actionDoubleClick(String locator) {
		Actions actions = new Actions(getDriver());
		try {
			WebElement element = getElement(locator);
			actions.moveToElement(element).doubleClick().build().perform();

			// actions.doubleClick(element).perform();
		} catch (Exception e) {
			Assert.fail("Issue While Double Clicking ", e.getCause());
		}
	}

	public static long generateRandom(int prefix) {

		Random rand = new Random();
		long x = (long) (rand.nextDouble() * 10000000);
		String number = String.valueOf(prefix) + String.format("%08d", x);
		return Long.valueOf(number);
	}

	public String MobileRandom(String locator) {
		String output = "";
		output = String.valueOf(generateRandom(79));
		// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
		getElement(locator).sendKeys(output);
		log.debug("STEP: " + output + " is set inside element");

		return output;
	}

	public boolean verifyTextold(String locator, String actualMessage) {
		String expectedMessage = null;

		try {
			expectedMessage = getElement(locator).getText();
			if (expectedMessage.equals(actualMessage)) {
				System.out.println("Message is proper displayed :" + expectedMessage);
				log.debug("STEP: Message displayed Correctly :" + expectedMessage);
			} else {
				System.out.println(" :" + expectedMessage);
				return false;
			}

		} catch (Exception e) {

			log.debug("STEP: Message is  not proper displayed" + expectedMessage);

		}
		return false;
	}

	public boolean verifyText(String locator, String expected) {
		// waitForJSLoad();
		// WebDriverWait wait = new WebDriverWait(getDriver(),
		// Duration.ofSeconds(10),Duration.ofMillis(1000));
		// getWait().until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		// System.out.println("Waited till element is visibled :" + locator);

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(50), Duration.ofMillis(1000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));

		String text = getElement(locator).getText();
		// System.out.println("Expected Message is :" + expected);
		// System.out.println(" Actual Message is :" + text);
		Assert.assertEquals(text, expected);
		return false;

	}

	public boolean verifyTextForCongrtualtionsPopup(String locator, String expected) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60), Duration.ofMillis(1000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		// System.out.println("Waited till element is visibled :" + locator);
		String text = getElement(locator).getText();
		// System.out.println("Expected Message is :" + expected);
		// System.out.println(" Actual Message is :" + text);
		Assert.assertEquals(text, expected);
		return false;

	}

	public void waitForJSLoad() {
		int count = 0;
		while (count < 3) {
			try {
				JavascriptExecutor j = (JavascriptExecutor) getDriver();
				j.executeScript("return document.readyState").toString().equals("complete");
				System.out.println("JS Loaded!");
				break;
			} catch (Throwable e) {
				wait(5000);
				count++;
			}
		}
	}

	public String navigateToBack() {
		try {
			driver.navigate().back();
			log.debug("STEP: Navigating to Back");

		} catch (Exception e) {
			log.debug("STEP: can't navigate back");
		}
		return null;

	}

	/**
	 * @param object
	 * @param data
	 * @throws AWTException
	 */

	public void PNGfileuploading(String locator) {
		String strPath = "";
		log.debug("uploading Document...");
		try {
			strPath = FilePaths.PNG_FOLDER_PATH;
			System.out.println("path:" + strPath);
			WebElement element = getElement(locator);
			element.sendKeys(strPath);
			System.out.println("PNG File is Uploaded Successfully");

			// WebElement element = getElement(locator);
			// new Actions(driver).click(element).perform();
			// new Actions(driver).sendKeys(strPath).perform();
			// new Actions(driver).sendKeys(Keys.ENTER).perform();
			// System.out.println("File is Uploaded Successfully");

			//
			// element.click();
			// wait(5000);
			// element.sendKeys(strPath);
			// wait(5000);
			// element.sendKeys(Keys.ENTER);
			// element.sendKeys(Keys.ENTER);
			// System.out.println("File is Uploaded Successfully");

			// With JS
			// JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			// wait(4000);
			// String js = "arguments[0].style.height='1'; arguments[0].style.width='1'; "
			// + "arguments[0].style.visibility='visible';";
			// jse.executeScript(js, element);
			// // element.click();
			// wait(4000);
			// // element.click();
			// //String data = (String)
			// Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);

			// element.sendKeys(strPath);
			// // element.sendKeys(Keys.ENTER);
			// // element.sendKeys(Keys.ENTER);

			// wait(4000);
			// String jsa = "arguments[0].style.visibility = 'hidden';";
			// jse.executeScript(jsa, element);
			// wait(2000);
			// System.out.println("File is Uploaded Successfully");
		} catch (Exception e) {
			System.out.println(" - Getting error while document uploading" + e.getMessage());
		}
	}
	// public void PNGfileuploadingg(String locator){

	// String strPath = "";
	// log.debug("uploading Document...");
	// try {
	// strPath = FilePaths.PNG_FOLDER_PATH;
	// System.out.println("path:" + strPath);
	// WebElement element = getElement(locator);
	// element.click();
	// wait(3000);
	// StringSelection ss = new StringSelection(FilePaths.PNG_FOLDER_PATH);
	// Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	// Robot robot = new Robot();
	// robot.delay(250);
	// robot.keyPress(KeyEvent.VK_CONTROL);
	// robot.keyPress(KeyEvent.VK_V);
	// robot.keyRelease(KeyEvent.VK_V);
	// robot.keyRelease(KeyEvent.VK_CONTROL);
	// robot.keyPress(KeyEvent.VK_ENTER);
	// robot.keyRelease(KeyEvent.VK_ENTER);
	// robot.keyRelease(KeyEvent.VK_ENTER);
	// wait(3000);
	// robot.delay(4000);

	// }
	// catch (Exception e)
	// {
	// System.out.println(" - Getting error while document uploading" +
	// e.getMessage());

	// }

	// }
	
	
	public void Csvuploading(String locator)
	{
		String strPath = "";
		log.debug("uploading Document...");
		try {
		strPath = FilePaths.CSV_FOLDER_PATH;
		System.out.println("path:" + strPath);
		WebElement element = getElement(locator);
		element.click();
		wait(3000);
		StringSelection ss = new StringSelection(FilePaths.CSV_FOLDER_PATH);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot = new Robot();
		robot.delay(250);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		wait(3000);
		robot.delay(4000);
		}
		catch (Exception e)
		{
		System.out.println(" - Getting error while document uploading" + e.getMessage());
		}
	}
	
	
	public void InvalidCsvuploading(String locator)
	{
		String strPath = "";
		log.debug("uploading Document...");
		try {
		strPath = FilePaths.CSV_Invalid_FOLDER_PATH;
		System.out.println("path:" + strPath);
		WebElement element = getElement(locator);
		element.click();
		wait(3000);
		StringSelection ss = new StringSelection(FilePaths.CSV_Invalid_FOLDER_PATH);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot = new Robot();
		robot.delay(250);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		wait(3000);
		robot.delay(4000);
		}
		catch (Exception e)
		{
		System.out.println(" - Getting error while document uploading" + e.getMessage());
		}
	}
	public void acceptAlerts() {
		try {

			wait(5000);
			getDriver().switchTo().alert().accept();
			wait(2000);
			log.debug("STEP: Accept the alert");

		} catch (Exception e) {
			Assert.fail("Issue While accepting alert", e.getCause());
		}

	}

	public boolean verifyDisableCheckbox(String locator) {
		try {
			WebElement element = getElement(locator);
			if (element.isDisplayed() && element.getAttribute("disabled").contains("disabled")) {
				// System.out.println("Checkbox is disabled :");
				log.debug("STEP: Checkbox is disabled:" + locator);
			} else {
				// System.out.println("Step : Checkbox is enabled : " + locator);
			}

		} catch (Exception e) {

			log.debug("STEP: issue whi;e checking in checkbox" + e);

		}
		return false;
	}

	public String CopymobileNumber(String locator) {

		try {
			WebElement element = getElement(locator);
			String mobNo = element.getAttribute("value");
			// System.out.println("copy is " + mobNo);
			mobileNumber = mobNo;
			// StringSelection ss = new StringSelection(mobNo);
			// Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			// Robot robot = new Robot();
			// robot.delay(250);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locator;
	}

	public void pasteMobNumber(String locator) {
		try {
			WebElement element = getElement(locator);
			// System.out.println("element is " + element);
			// System.out.println("mob is " + mobileNumber);
			element.sendKeys(mobileNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String CopyOrderID(String locator) {

		try {
			WebElement element = getElement(locator);
			orderID = element.getText();
			// StringSelection ss = new StringSelection(orderID);
			// Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			// Robot robot = new Robot();
			// robot.delay(250);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locator;
	}

	public String CopyOrderIDFORAPI() {
		try {

			WebElement element = getDriver().findElement(By.xpath("//div[contains(text(),'NS-')]"));
			String Id = element.getText();
			// System.out.println("Id is " + Id);
			return Id;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String currentDateTimeGenerate() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String finalDate = dateFormat.format(date);
			return finalDate;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String WriteOrder(String locator) {
		// String orderID = "";
		try {
			// System.out.println("write order id : " + orderID);
			WebElement element = getElement(locator);
			element.sendKeys(orderID);
			// Robot robot = new Robot();
			// robot.keyPress(KeyEvent.VK_CONTROL);
			// robot.keyPress(KeyEvent.VK_V);
			// robot.keyRelease(KeyEvent.VK_V);
			// robot.keyRelease(KeyEvent.VK_CONTROL);
			// robot.keyPress(KeyEvent.VK_ENTER);
			// robot.keyRelease(KeyEvent.VK_ENTER);
			// robot.keyRelease(KeyEvent.VK_ENTER);
			// wait(3000);
			// robot.delay(4000);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// return orderID;
		return locator;

	}
	// public void JPGfileuploadingg(String locator){
	// String strPath = "";
	// log.debug("uploading Document...");
	// try {
	// strPath = FilePaths.JPG_FOLDER_PATH;
	// System.out.println("path:" + strPath);
	// WebElement element = getElement(locator);
	// element.click();
	// wait(3000);
	// StringSelection ss = new StringSelection(FilePaths.JPG_FOLDER_PATH);
	// Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	// Robot robot = new Robot();
	// robot.delay(250);
	// robot.keyPress(KeyEvent.VK_CONTROL);
	// robot.keyPress(KeyEvent.VK_V);
	// robot.keyRelease(KeyEvent.VK_V);
	// robot.keyRelease(KeyEvent.VK_CONTROL);
	// robot.keyPress(KeyEvent.VK_ENTER);
	// robot.keyRelease(KeyEvent.VK_ENTER);
	// robot.keyRelease(KeyEvent.VK_ENTER);
	// wait(3000);
	// robot.delay(4000);
	// }

	// catch (Exception e)
	// {
	// System.out.println(" - Getting error while document uploading" +
	// e.getMessage());
	// }
	// }

//	public void JPGfileuploading(String locator) {
//		String strPath = "";
//		log.debug("uploading Document...");
//		try {
//			strPath = FilePaths.JPG_FOLDER_PATH;
//		//	System.out.println("path:" + strPath);
//			WebElement element = getElement(locator);
//			element.sendKeys(strPath);
//		//	System.out.println("JPG File is Uploaded Successfully");
//
//		}
//
//		catch (Exception e) {
//			System.out.println(" - Getting error while document uploading" + e.getMessage());
//		}
//	}

//	public void PDFfileuploading(String locator) {
//		String strPath = "";
//		log.debug("uploading Document...");
//		try {
//			strPath = FilePaths.PDF_FOLDER_PATH;
//			System.out.println("path:" + strPath);
//			WebElement element = getElement(locator);
//			element.sendKeys(strPath);
//			System.out.println("PDF File is Uploaded Successfully");
//		}
//
//		catch (Exception e) {
//			System.out.println(" - Getting error while document uploading" + e.getMessage());
//		}
//	}

	// public void PDFfileuploading(String locator){
	// String strPath = "";
	// log.debug("uploading Document...");
	// try {
	// strPath = FilePaths.PDF_FOLDER_PATH;
	// System.out.println("path:" + strPath);
	// WebElement element = getElement(locator);
	// element.click();
	// wait(3000);
	// StringSelection ss = new StringSelection(FilePaths.PDF_FOLDER_PATH);
	// Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	// Robot robot = new Robot();
	// robot.delay(250);
	// robot.keyPress(KeyEvent.VK_CONTROL);
	// robot.keyPress(KeyEvent.VK_V);
	// robot.keyRelease(KeyEvent.VK_V);
	// robot.keyRelease(KeyEvent.VK_CONTROL);
	// robot.keyPress(KeyEvent.VK_ENTER);
	// robot.keyRelease(KeyEvent.VK_ENTER);
	// robot.keyRelease(KeyEvent.VK_ENTER);
	// wait(3000);
	// robot.delay(4000);
	// }

	// catch (Exception e)
	// {
	// System.out.println(" - Getting error while document uploading" +
	// e.getMessage());
	// }
	// }
//	public void MP4fileuploading(String locator) {
//		String strPath = "";
//		log.debug("uploading Document...");
//		try {
//			strPath = FilePaths.MP4_FOLDER_PATH;
//			System.out.println("path:" + strPath);
//			WebElement element = getElement(locator);
//			element.sendKeys(strPath);
//			System.out.println("MP4 is Uploaded Successfully");
//
//		}
//
//		catch (Exception e) {
//			System.out.println(" - Getting error while document uploading" + e.getMessage());
//		}
//
//	}

	// public void MP4fileuploading(String locator){
	// String strPath = "";
	// log.debug("uploading Document...");
	// try {
	// strPath = FilePaths.MP4_FOLDER_PATH;
	// System.out.println("path:" + strPath);
	// WebElement element = getElement(locator);
	// element.click();
	// wait(3000);
	// StringSelection ss = new StringSelection(FilePaths.MP4_FOLDER_PATH);
	// Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	// Robot robot = new Robot();
	// robot.delay(250);
	// robot.keyPress(KeyEvent.VK_CONTROL);
	// robot.keyPress(KeyEvent.VK_V);
	// robot.keyRelease(KeyEvent.VK_V);
	// robot.keyRelease(KeyEvent.VK_CONTROL);
	// robot.keyPress(KeyEvent.VK_ENTER);
	// robot.keyRelease(KeyEvent.VK_ENTER);
	// robot.keyRelease(KeyEvent.VK_ENTER);
	// wait(3000);
	// robot.delay(4000);
	// }

	// catch (Exception e)
	// {
	// System.out.println(" - Getting error while document uploading" +
	// e.getMessage());
	// }

	// }

	public void scrollDown150() {
		// WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(2000);
		je.executeScript("window.scrollBy(0,150)");
		log.debug("STEP: Scroll down");
	}

	public void scrollDown250() {
		// WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(2000);
		je.executeScript("window.scrollBy(0,250)");
		log.debug("STEP: Scroll down");
	}

	public void scrollDownTillElementFind(String locator) {
		WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(2000);
		// je.executeScript("window.scrollBy(0,350)");
		Point ele = element.getLocation();
		int x = ele.getX();
		int y = ele.getY();
		je.executeScript("window.scrollBy(" + x + "," + y + ");");
		log.debug("STEP: Scroll down till element display");

	}

	public String enterTextWithActionEnter(String locator, String strMessage) {
		// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
		getElement(locator).sendKeys(strMessage);
		log.debug("STEP: " + strMessage + " is set inside element");

		/*
		 * getElement(locator).sendKeys(Keys.TAB); log.debug("STEP: " + strMessage +
		 * " is set tab from element");
		 */

		return strMessage;
	}

	public void scrollUp150() {
		// WebElement element = getElement(locator);
		JavascriptExecutor je = (JavascriptExecutor) getDriver();
		wait(2000);
		je.executeScript("window.scrollBy(-0,-150)");
		log.debug("STEP: Scroll up");
	}

	public void selectListWithTab(String locator, String strMessage) {
		WebElement dropDownListBox = getElement(locator);
		dropDownListBox.sendKeys(Keys.TAB);
		wait(3000);
		dropDownListBox.sendKeys(strMessage);
		wait(2000);
		Actions action = new Actions(getDriver());
		action.sendKeys(Keys.TAB).build().perform();
		action.sendKeys(Keys.ENTER).build().perform();
		try {
			// dropDownListBox.sendKeys(Keys.TAB);
		} catch (Exception e) {
			Assert.fail("Issue While selecting value from dropdown", e.getCause());
		}
		log.debug("STEP: " + strMessage + " is selected");
	}

	public static long generateRandomForRegisterationNumber() {
		Random rand = new Random();
		long x = (long) (rand.nextDouble() * 10000000);
		String number = String.format("%08d", x);
		return Long.valueOf(number);
	}

	public String PassingregisterationNumber(String locator) {
		String output = "";
		output = String.valueOf(generateRandomForRegisterationNumber());
		// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
		getElement(locator).sendKeys(output);
		log.debug("STEP: " + output + " is set inside element");
		return output;
	}

	public static long generatePANNumber() {
		Random rand = new Random();
		long x = (long) (rand.nextLong() * 1000000000);
		String number = String.format("%11d", x);
		return Long.valueOf(number);
	}

	public static long generateRollNumber() {
		Random rand = new Random();
		long x = (long) (rand.nextDouble() * 10000000);
		String number = String.format("%08d", x);
		return Long.valueOf(number);
	}

	public String PassingRoleNumber(String locator) {
		String output = "";
		output = String.valueOf(generateRollNumber());
		// getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
		getElement(locator).sendKeys(output);
		log.debug("STEP: " + output + " is set inside element");
		return output;
	}

	public String enterTextwithClick(String locator, String strMessage) {
		getWait().until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		// getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
		// getElement(locator).clear();
		wait(2000);
		getElement(locator).sendKeys(strMessage);
		log.debug("STEP: " + strMessage + " is set inside element");

		/*
		 * getElement(locator).sendKeys(Keys.TAB); log.debug("STEP: " + strMessage +
		 * " is set tab from element");
		 */

		return strMessage;
	}

	public static String generateRandomPanFirstChars() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int length = 5;

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		// System.out.println(randomString);
		return randomString;

	}

	public static String generateRandomPan() {

		String numbers = "0123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int length = 4;

		for (int i = 0; i < length; i++) {

			int index = random.nextInt(numbers.length());
			char randomChar = numbers.charAt(index);
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		// System.out.println(randomString);
		return randomString;

	}

	public static String generateRandomPanLastChars() {

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		int length = 1;

		for (int i = 0; i < length; i++) {

			int index = random.nextInt(alphabet.length());

			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		// System.out.println(randomString);
		return randomString;

	}

	public String PanNumberRandomGenerator(String locator) {
		String output = "";
		output = generateRandomPanFirstChars() + generateRandomPan() + generateRandomPanLastChars();
		// System.out.println(output);
		getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
		getElement(locator).sendKeys(output);
		return output;
	}

	public void verifyInvisibility(String locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
		// wait.until(ExpectedConditions.invisibilityOfAllElements(getElement(locator)));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(getLocator(locator)));
		// System.out.println("Waited till element is disappeared :"+ locator );

	}

	public void verifyVisibility(String locator) {
		// WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		// wait.until(ExpectedConditions.invisibilityOfAllElements(getElement(locator)));
		// wait.until(ExpectedConditions.visibilityOfAllElements(getElement(locator)));
		// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		// WebElement element =
		// wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		getWait().until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		// System.out.println("Waited till element is visibled :" + locator);

	}

	public void verifyURL(String locator) {
		// WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		// wait.until(ExpectedConditions.invisibilityOfAllElements(getElement(locator)));
		// wait.until(ExpectedConditions.visibilityOfAllElements(getElement(locator)));

		String URL = getDriver().getCurrentUrl();
		Assert.assertEquals(URL, locator);

	}

	public void waitOfPageLoading() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		String result = js.executeScript("return document.readyState").toString();
		if (!result.equals("complete")) {
			Thread.sleep(5000);
		}
	}

	public void verifyPageLoader(String locators) {

		if (!locators.isEmpty()) {

			verifyInvisibility(locators);
			System.out.println("Waited till loader is Invisible");

		}
	}

	public void verifyStateAutoValueSet(String locator) {
		getWait().until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));

	}

	public void getText(String locators) {
		String text = getElement(locators).getText();
		System.out.println(text);

	}

	public void navigateURL(String URL) {
		getDriver().get(URL);
	}

	public String generateRandomText(String locator) {
		try {
			String Title = "QAAutomation" + RandomStringUtils.random(5, true, true);
			getWait().until(ExpectedConditions.visibilityOf(getElement(locator))).sendKeys(Keys.TAB);
			getElement(locator).sendKeys(Title);
			log.debug("STEP: " + Title + " is set inside element");
			finaltitle = Title;
			return finaltitle;

		} catch (Exception exe) {

			log.debug("STEP - Generte Random Text");
		}
		return null;

	}

	public void assertinOfTitle() {

		wait(2000);

		List<WebElement> elements = getDriver()
				.findElements(By.xpath("//*[@id='no-more-tables']/table//tbody//tr//td[6]"));

		for (int i = elements.size() - 1; i > 0; i--) {

			// System.out.println(elements.get(i).getText()); //

			System.out.println(finaltitle);

			if (elements.get(i).getText().equals(finaltitle)) {

				System.out.println("Title verified in the table");

				log.info("STEP - Title verified in the table");

				elements.get(i).click();

			}

			else {

				log.info("STEP - Title not verified in the table"); //

			}

		}

	}

	
	public String currentDate() {

		try {

			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

			Date date = new Date();

			String finaldate = dateFormat.format(date);

			System.out.println(dateFormat.format(date));

			return finaldate;

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}
	
	
	public void EscKey() {
        // WebElement element = getElement(locator);
        Actions a = new Actions(getDriver());
        a.sendKeys(Keys.ESCAPE).build().perform();
    }
	public void SpaceKey() {
        // WebElement element = getElement(locator);
        Actions a = new Actions(getDriver());
      
        a.sendKeys(Keys.SPACE).build().perform();
    }
	
	
	


}
