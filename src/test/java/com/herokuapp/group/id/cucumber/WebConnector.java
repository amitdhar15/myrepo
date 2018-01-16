package com.herokuapp.group.id.cucumber;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Strings;

public class WebConnector {
	private static List<WebDriver> drivers = new ArrayList<>();
	public static WebDriver driver = null;
	// Initializing Properties file
	private Properties OR = null;
	private Properties CONFIG = null;
	Dimension d = new Dimension(1920, 1080);
	// Resize the current window to the given dimension

	private static String CURRENT_OS = System.getProperty("os.name")
			.toLowerCase();
	// public static final String ANSI_GREEN = "\u001B[32m";

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static String driverPath = "C:/chromedriver_win32/";

	public static String lastUrl;
	public static String lastPageSource;
	public static Set<Cookie> lastCookies;

	// default constructor
	public WebConnector() {
		System.out.println("                                                                                                    ");
		System.out.println("Step 1: Initializing Properties file");
		System.out.println("                                                                                                    ");
		try {

			final String propertiesDirectoryPath = getPropertiesDirectoryPath();
			System.out.println("automation_home = " + propertiesDirectoryPath);
			System.out.println(System.getProperty("os.name"));

			FileInputStream fs = new FileInputStream(propertiesDirectoryPath
					+ File.separator + "OR.properties");
			OR = new Properties();
			OR.load(fs);

			fs = new FileInputStream(propertiesDirectoryPath + File.separator
					+ "CONFIG.properties");
			CONFIG = new Properties();
			CONFIG.load(fs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private String getPropertiesDirectoryPath() {
		final String automation_home = getAutomationHomePath();
		return automation_home + File.separator + "properties";
	}

	private String getAutomationHomePath() {
		String automationHome = System.getenv("AUTOMATION_HOME");
		return automationHome == null ? "AUTOMATION_HOME" : automationHome;
	}

	public void openBrowser() {

		final String browser = CONFIG.getProperty("browser");

		if (Strings.isNullOrEmpty(browser)
				|| "mozilla".equals(browser.toLowerCase())) {
			System.out.println("launching Firefox browser");
			driver = new FirefoxDriver();

		} else if ("phantomjs".equals(browser.toLowerCase())) {
			File file = new File(getPhantomJSPath());
			System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
			driver = new PhantomJSDriver();

		} else if ("chrome".equals(browser.toLowerCase())) {
			System.out.println("launching chrome browser");
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//driver//chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			driver = new ChromeDriver(options);

		} else {
			throw new RuntimeException("browser not supported !!! " + browser);
		}

		drivers.add(driver);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	/**
	 * Code to Run Application on Jenkins on Multiple Envirnments
	 * 
	 * @param urlProperty
	 */

	public void runOnMultipleEnvironment(String urlProperty) {
		// public void navigateToIdpWeb(String urlProperty) {
		// String webBaseUrl = System.getProperty("web-base-url",
		// "http://the-internet.herokuapp.com/login");
		String relativeUrl = CONFIG.getProperty(urlProperty);
		// if (relativeUrl == null) fail("Missing property: " + urlProperty);
		// String idp_url = webBaseUrl + relativeUrl;
		navigate(relativeUrl);

	}

	public void navigate(String url) {
		System.out.println("Navigating to: " + url);
		for (Cookie cookie : getCookiesIfPossible()) {
			System.out.println("Cookie before: " + cookie);
		}
		driver.get(url);
		waitUntilSpinnerHasStopped();
		for (Cookie cookie : getCookiesIfPossible()) {
			System.out.println("Cookie after: " + cookie);
		}
	}

	public String getPro(String URL) {
		driver.get(CONFIG.getProperty(URL));
		return URL;
	}

	public String getPropertyByKey(String key) {
		String value = CONFIG.getProperty(key);
		if (value == null) {
			fail("Cannot find configuration with key: " + key);
		}
		return value;
	}

	// click on any object
	public void click(String propertyKey) {
		waitUntilSpinnerHasStopped();
		WebElement element = findByXpath(propertyKey);
		WebDriverWait wait = new WebDriverWait(driver, 1);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	// writing in a text field / select value from a list
	public void input(String propertyKey, String data) {
		waitUntilSpinnerHasStopped();
		String xpath = getXpathByPropertyKey(propertyKey);
		WebDriverWait wait = new WebDriverWait(driver, 1);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath(xpath)));
		} catch (TimeoutException e) {
			WebElement element = findByXpath(propertyKey);
			System.err.println("Error waiting for visbility of element"
					+ element);
			if (element != null) {
				System.err.println("Element is visible: "
						+ element.isDisplayed());
				System.err
						.println("Element is enabled: " + element.isEnabled());
			}
			throw e;
		}
		findByXpath(propertyKey).sendKeys(data);
	}

	public void inputList(String object, String data) {
		 driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		//driver.findElement(By.name("username")).sendKeys("username");
	}

	public void verifyTitle() {

	}

	// checking the presence of a particular element
	public boolean isElementPresent(String object) {

		return driver.getPageSource().contains(object);

		// int count =
		// driver.findElements(By.xpath(OR.getProperty(object))).size();
		// System.out.println("Count"+ object);
		// System.out.println("Count"+ count);
		// if (count == 0)
		// return false;
		// else
		// return true;
	}

	public String getText(String object) {
		return driver.findElement(By.xpath(OR.getProperty(object))).getText();

	}

	public void verifyText(String object, String data) {
		driver.findElement(By.xpath(OR.getProperty(object))).getText()
				.equals(data);

	}

	public WebElement findByXpath(String propertyKey) {
		String xpath = getXpathByPropertyKey(propertyKey);
		WebElement element = driver.findElement(By.xpath(xpath));
		if (element == null) {
			fail("Cannot find element with xpath: " + xpath);
		} else {
			printlnWhite("Found element " + element + " by xpath: " + xpath);
		}
		return element;
	}

	public String getXpathByPropertyKey(String propertyKey) {
		String xpath = OR.getProperty(propertyKey);
		if (StringUtils.isBlank(xpath)) {
			fail("Missing xpath property with key: " + propertyKey);
		}
		return xpath;
	}

	public void waitUntilSpinnerHasStopped() {
		waitUntilInvisible("loader");
	}

	public void waitUntilInvisible(String id) {

		long start = System.currentTimeMillis();
		long timeout = 10000; // 10 seconds

		while (isElementVisible(By.id(id))) {
			printlnWhite("Element is visible: " + id);

			// check that we haven't waited too long
			if (System.currentTimeMillis() > (start + timeout)) {
				Assert.fail("Waited too long (" + timeout
						+ " ms) for the element with id '" + id
						+ "' to become invisible");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		printlnBlue("Element is no longer visible: " + id);

	}

	private void printlnWhite(String text) {
		System.out.print(ANSI_WHITE);
		System.out.print(text);
		System.out.println(ANSI_BLACK);
	}

	private void printlnBlue(String text) {
		System.out.print(ANSI_BLUE);
		System.out.print(text);
		System.out.println(ANSI_BLACK);
	}

	private void printlnYellow(String text) {
		System.out.print(ANSI_YELLOW);
		System.out.print(text);
		System.out.println(ANSI_BLACK);
	}

	private boolean isElementVisible(By locator) {
		WebDriverWait waitOneSecond = new WebDriverWait(driver, 1);
		try {
			waitOneSecond.until(ExpectedConditions
					.visibilityOfElementLocated(locator));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getBrowserUrl() {
		return driver.getCurrentUrl();

	}

	public void closeBrowser() {
		lastUrl = getCurrentUrlIfPossible();
		lastPageSource = getPageSourceIfPossible();
		lastCookies = getCookiesIfPossible();
		driver.close();
	}

	public String getPhantomJSPath() {
		String basePath = getAutomationHomePath() + File.separator + "binaries";

		if (isWindows()) {
			return basePath + File.separator + "phantomjs-2.1.1-windows"
					+ File.separator + "bin" + File.separator + "phantomjs.exe";
		} else if (isMac()) {
			return basePath + File.separator + "phantomjs-2.1.1-macosx"
					+ File.separator + "bin" + File.separator + "phantomjs";
		} else if (isLinux()) {
			return basePath + File.separator + "phantomjs-2.1.1-linux-x86_64"
					+ File.separator + "bin" + File.separator + "phantomjs";
		}

		throw new RuntimeException("Your system is not supported!");

	}

	private static boolean isWindows() {
		return (CURRENT_OS.contains("win"));
	}

	private static boolean isMac() {
		return (CURRENT_OS.contains("mac"));
	}

	private static boolean isLinux() {
		return (CURRENT_OS.contains("nux"));
	}

	public void clearBrowserHistory() {
		// TODO Auto-generated method stub
		driver.manage().deleteAllCookies();

	}

	public String getCurrentUrl() throws InterruptedException {
		Thread.sleep(10000);
		String url = driver.getCurrentUrl();
		return url;
	}

	public Object putWait() throws InterruptedException {
		// ipm_wait.wait(30);
		return CONFIG;
	}

	public void printUrlAndPageSource() {
		printlnYellow("------------------ URL and source after failure -------------------");
		printlnYellow("URL: "
				+ StringUtils.defaultString(getCurrentUrlIfPossible(), lastUrl));
		Set<Cookie> cookies = getCookiesIfPossible();
		if (cookies == null)
			cookies = lastCookies;
		for (Cookie cookie : cookies) {
			printlnYellow("Cookie: " + cookie);
		}
		printlnYellow("Page source:");
		printlnYellow(StringUtils.defaultString(getPageSourceIfPossible(),
				lastPageSource));
		printlnYellow("-------------------------------------------------------------------");
	}

	private String getCurrentUrlIfPossible() {
		try {
			return driver.getCurrentUrl();
		} catch (Exception e) {
			return null;
		}
	}

	private String getPageSourceIfPossible() {
		try {
			return driver.getPageSource();
		} catch (Exception e) {
			return null;
		}
	}

	private Set<Cookie> getCookiesIfPossible() {
		try {
			return driver.manage().getCookies();
		} catch (Exception e) {
			return new HashSet<>();
		}
	}

	public void selectFromDropdown(String object, String object1)
			throws InterruptedException {

		Select dropdown = new Select(driver.findElement(By.id(object)));
		dropdown.selectByVisibleText(object1);

		Thread.sleep(3000);
	}

	public void getscreenshot() throws Exception {
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		// The below method will save the screen shot in d drive with name
		// "screenshot.png"
		FileUtils.copyFile(scrFile, new File(
				"/home/vfroot/idp_3/screenshot.png"));
	}

	public static void quitAll() throws Exception {
		for (WebDriver driver : drivers) {
			driver.quit();
		}
	}

	public void waitForFewSeconds(String object) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//*[@id='main']/div/div[2]/p")));
	}

	public void movemouse(String object) throws InterruptedException {

		String locate = OR.getProperty(object);
		
		Actions ToolTip1 = new Actions(driver);
		WebElement googleLogo = driver.findElement(By.xpath(locate));
		Thread.sleep(5000);
		ToolTip1.clickAndHold(googleLogo).perform();
		Thread.sleep(5000);

	}

	public static void sortElementByLastName(List<WebElement> tr_collection) {
		Collections.sort(tr_collection, new Comparator<WebElement>() {
			public int compare(WebElement p1, WebElement p2) {

				return p1.getTagName().compareToIgnoreCase(p2.getTagName());
			}
		});
	}

	public void tablesort(String object) throws InterruptedException {

		WebElement table_element = driver.findElement(By.id(object));
		List<WebElement> tr_collection = table_element.findElements(By
				.xpath("//*[@id='table1']/tbody/tr"));

		List<TableList> tableList = new ArrayList<TableList>();

		System.out.println("NUMBER OF ROWS IN THIS TABLE = "
				+ tr_collection.size());
		int row_num, col_num;
		row_num = 1;
		for (WebElement trElement : tr_collection) {
			List<WebElement> td_collection = trElement.findElements(By
					.xpath("td"));
			System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
			col_num = 1;
			TableList tblLst = new TableList();
			for (WebElement tdElement : td_collection) {

				System.out.println("row # " + row_num + ", col # " + col_num
						+ "text=" + tdElement.getText());
				if (col_num == 1) {
					tblLst.setLastName(tdElement.getText());
				} else if (col_num == 2) {
					tblLst.setFirstName(tdElement.getText());
				} else if (col_num == 3) {
					tblLst.setEmail(tdElement.getText());
				} else if (col_num == 4) {
					tblLst.setSalary(tdElement.getText());
				} else if (col_num == 5) {
					tblLst.setUrl(tdElement.getText());
				} else if (col_num == 6) {
					tblLst.setLink(tdElement.getText());
				}

				col_num++;
			}
			row_num++;

			tableList.add(tblLst);

		}

		Collections.sort(tableList, TableList.FirstNameComparator);

		System.out.println(tableList);

	}

}