package ro.sapientia.mesteri2015.test;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SCRUMBugUpdateStepDefinition {
	
	protected WebDriver driver;

	@Before
	public void setup() {
		driver = new FirefoxDriver();
	}

	@Given("^I open a single bug page and click update$")
	public void I_open_a_single_bug_page_and_click_update() throws Throwable {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/bug/1");
		
		WebElement updateButton = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/a[1]"));
		updateButton.click();
	}

	@When("I enter \"([^\"]*)\" in the title textbox and \"([^\"]*)\" in the description textbox and I push the update button")
	public void I_enter_in_the_title_textbox_and_description_textbox_and_I_push_the_update_button(String title, String description) throws Throwable {
		WebElement titleTextBox = driver.findElement(By.id("bug-title"));
		titleTextBox.clear();
		titleTextBox.sendKeys(title);
		
		WebElement descriptionTextBox = driver.findElement(By.id("bug-description"));
		descriptionTextBox.clear();
		descriptionTextBox.sendKeys(description);

		WebElement updateButton = driver.findElement(By.id("update-bug-button"));
		updateButton.click();
	}

	@Then("^I should get result \"([^\"]*)\" with \"([^\"]*)\" in single bug page$")
	public void I_should_get_result_in_single_bug_page(String expectedTitle, String expectedDescription) throws Throwable {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/bug/1");
		
		WebElement titleTextBox = driver.findElement(By.id("bug-title"));
		String resultTitle = titleTextBox.getText();
		
		WebElement descriptionTextBox = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[1]/p"));
		String resultDescription = descriptionTextBox.getText();

		Assert.assertEquals(resultTitle, expectedTitle);
		Assert.assertEquals(resultDescription, expectedDescription);

		//driver.close();
	}
	
	// Second scenario
	
	@When("I enter nothing in the title textbox and I push the update button")
	public void I_enter_nothing_in_the_title_textbox_and_I_push_the_update_button() throws Throwable {
		WebElement titleTextBox = driver.findElement(By.id("bug-title"));
		titleTextBox.clear();
		titleTextBox.sendKeys("");

		WebElement addButton = driver.findElement(By.id("update-bug-button"));
		addButton.click();
	}
	
	@Then("^I should get result error message for update next to title textbox saying \"([^\"]*)\"$")
	public void I_should_get_result_error_message_for_update_next_to_title_textbox(String expectedMessage) throws Throwable {
		WebElement errorMessageLabel = driver.findElement(By.id("error-title"));
		String resultMessage = errorMessageLabel.getText();
		
		Assert.assertEquals(resultMessage, expectedMessage);

		//driver.close();
	}

	@After
	public void closeBrowser() {
		driver.quit();
	}

}
