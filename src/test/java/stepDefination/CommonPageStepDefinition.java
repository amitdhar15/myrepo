package stepDefination;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.List;

import com.herokuapp.group.id.cucumber.WebConnector;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonPageStepDefinition {
	WebConnector application = new WebConnector();
	// HttpURLConnectionExample Idpbackend = new HttpURLConnectionExample();
	String Actualtext;

	@Before
	public void openBrowser() throws Exception {
		System.out.println("Step 2: Opening The Browser");
		System.out
				.println("                                                                                                    ");
		application.openBrowser();
		application.clearBrowserHistory();
	}

	@After
	public void closeApplication() {
		application.closeBrowser();
	}

	@After
	public void afterFailedScenario(Scenario scenario) throws Exception {
		if (scenario.isFailed()) {
			application.printUrlAndPageSource();
		}
	}

	@Given("move mouse on \"([^\"]*)\"$")
	public void moveAccount(String object) throws Exception {
		application.movemouse(object);
	}

	@Given("table sort \"([^\"]*)\"$")
	public void tableSort(String object) throws Exception {

		application.tablesort(object);
	}

	@Then("^User is redirected to IDGW return url as \"([^\"]*)\"$")
	public void explicitLoginReturnUrl(String urlPropertyKey)
			throws InterruptedException {
		application.waitUntilSpinnerHasStopped();
		String ActualUrl = application.getCurrentUrl();
		// String ExpectedUrl = "http://localhost/";
		String ExpectedUrl = application.getPropertyByKey(urlPropertyKey);
		System.out.println("Expected url is " + ExpectedUrl);
		System.out.println("actual url is " + ActualUrl);
		assertThat(ActualUrl, containsString(ExpectedUrl));
		System.out.println("User is redirected back to IDGW return url");
	}

	@When("^Clear browser history$")
	public void clearBrowserHistory() throws Exception {
		application.clearBrowserHistory();
	}

	@And("Click on \"([^\"]*)\"$")
	public void click(String xPathPropertyKey) throws InterruptedException {
		System.out.println("Clicking " + xPathPropertyKey);
		application.click(xPathPropertyKey);
		Thread.sleep(6000);
	}

	@And("enter \"([^\"]*)\" as \"([^\"]*)\"$")
	public void inputData(String xpathPropertyKey, String data)
			throws InterruptedException {

		System.out.println("Entering in " + xpathPropertyKey + " value " + data);
		// application.input(xpathPropertyKey, data);
		application.inputList(xpathPropertyKey, data);

		// Thread.sleep(6000);
	}

	
	@And("enter value \"([^\"]*)\" and \"([^\"]*)\"$")
	public void inputvalue(String xpathPropertyKey, String data)	throws InterruptedException {

		System.out.println("Entering in " + xpathPropertyKey + " value " + data);
		// application.input(xpathPropertyKey, data);
		application.inputList(xpathPropertyKey, data);

		// Thread.sleep(6000);
	}
	
	
	
	
	/*@When("^User enters Credentials to LogIn$")
	public void user_enters_testuser__and_Test(DataTable usercredentials) throws Throwable {
		List<List<String>> data = usercredentials.raw();
		driver.findElement(By.id("log")).sendKeys(data.get(0).get(0)); 
	    driver.findElement(By.id("pwd")).sendKeys(data.get(0).get(1));
	    driver.findElement(By.id("login")).click();
	    
	    driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
	}*/
	
	
	
	@Then("^text should exist as \"([^\"]*)\"$")
	public void validatePin(String expectedReslt) { 
		System.out.println("Expected Result  - " + expectedReslt);
		application.isElementPresent(expectedReslt);
	}

	@Then("\"([^\"]*)\" display a label saying \"([^\"]*)\"$")
	public void lbltextVerify(String object, String data) {
		application.waitUntilSpinnerHasStopped();
		application.verifyText(object, data);
	}

	@When("^User navigated to \"([^\"]*)\"$")
	public void NavigatingbyURL(String object) throws InterruptedException {
		application.runOnMultipleEnvironment(object);
		// application.waitForFewSeconds(object);
		// Thread.sleep(3000);
	}

	@When("^User is on \"([^\"]*)\"$")
	public void NavigatingToHomePage(String object) throws InterruptedException {
		application.runOnMultipleEnvironment(object);
		// application.waitForFewSeconds(object);
		// Thread.sleep(3000);
	}

	@When("^User navigated to for \"([^\"]*)\"$")
	public void NavigatingbyURLfor(String object) throws InterruptedException {
		application.runOnMultipleEnvironment(object);
		application.waitForFewSeconds(object);
		// Thread.sleep(3000);
	}

	@When("^Select from \"([^\"]*)\" as \"([^\"]*)\"$")
	public void select(String object, String object1)
			throws InterruptedException {
		application.selectFromDropdown(object, object1);
		// Thread.sleep(3000);
	}

	@Then("^Quit all open windows$")
	public void quitAllOpenWindows(String object, String object1)
			throws InterruptedException {
		application.selectFromDropdown(object, object1);
		// Thread.sleep(3000);
	}

	@Then("^Driver to wait for few seconds$")
	public void WebDriverTowait(String object, String object1)
			throws InterruptedException {
		application.selectFromDropdown(object, object1);
		// Thread.sleep(3000);
	}

}