package steps;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;
import pages.ForgotPasswordPage;
import utils.BaseTest;
import org.apache.log4j.Logger;



public class LoginSteps extends BaseTest  {

	public LoginPage loginPage = new LoginPage(driver);
	public HomePage homePage = new HomePage(driver);
	public ForgotPasswordPage forgotPwdPage = new ForgotPasswordPage(driver);
	public static Duration timeout = Duration.ofSeconds(20);
	public WebDriverWait wait = new WebDriverWait(driver, timeout); 
	public static Logger logger = Logger.getLogger(LoginSteps.class.getName());
	
	String validUsername = "inductiveEpitraxAdmin";
	String validPassword = "Pass!2345678";
	String invalidUsername = "inductive";
	String invalidPassword = "Pass1234";

	@Given("I am on the the login page")
	public void openLoginPage() {
		logger.info("Executing Step: i am on the the login page");
        //Cucumber hook is doing the job. So no code here.
	}
	@When("I enter valid username and valid password")
	public void enterValidCredentials() throws Throwable {
		logger.info("Executing step: I enter valid username and valid password ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.usernameTxt));
		driver.findElement(loginPage.usernameTxt).sendKeys(validUsername);
		Thread.sleep(3000);
		driver.findElement(loginPage.passwordTxt).sendKeys(validPassword);
		captureScreenshot("login_page_screenshot");
	}
	@And("I click on sign in button")
	public void clickOnSigninBtn() throws Throwable {
		logger.info("Executing Step: I click on sign in button");
		driver.findElement(loginPage.loginBtn).click();
		Thread.sleep(10000);
	}

	@Then("I expect to be signed on")
	public void verifySuccessfullLogin() throws Throwable {
		Thread.sleep(5000);
		Assert.assertTrue(driver.findElement(homePage.favoritesBtn).isDisplayed(), "Favorites button not found. Failing the login test case");
		logger.info("Executing Step: I expect to be signed on");
	}
	@When("I enter invalid username and invalid password")
	public void enterInvalidCredentials() throws Throwable {
		logger.info("Executing step: I enter invalid username and invalid password ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.usernameTxt));
		driver.findElement(loginPage.usernameTxt).sendKeys(invalidUsername);
		driver.findElement(loginPage.passwordTxt).sendKeys(invalidPassword);
		Thread.sleep(1000);
		captureScreenshot("login_page_screenshot");
	}

	@Then("I expect to be see an invalid login text")
	public void verifyInvalidLoginText() throws Throwable {
		logger.info("executing step: I expect to be see an invalid login text");
		wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.invalidLoginMsg));
		String expectedMsg = "You have entered an invalid password or username";
		String actualMsg = driver.findElement(loginPage.invalidLoginMsg).getText();
		Assert.assertTrue(actualMsg.contains(expectedMsg), "Expected invalid login message not found. Failing the invalid login test case");
		
	}

	@When("I navigate to the forgot password link")
	public void clickOnForgotPasswordLink() throws Throwable {
		logger.info("Executing step: I navigate to the forgot password link");
		loginPage = new LoginPage(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.forgotPasswordLink));
		driver.findElement(loginPage.forgotPasswordLink).click();
		Thread.sleep(2000);
	}

	@Then("I expect their to be a Forgot Password Header")
	public void verifyForgotPasswordHeader() {
		logger.info("Executing step: I expect their to be a Forgot Password Header");
		wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPwdPage.forgotPasswordHeaderMsg));
		String CorrectForgotPasswordHeaderMsg = "Please enter the username associated with your account";
		String ActualForgotPasswordHeaderMsg = driver.findElement(forgotPwdPage.forgotPasswordHeaderMsg).getText();
		Assert.assertTrue(ActualForgotPasswordHeaderMsg.contains(CorrectForgotPasswordHeaderMsg),"Correct forgot password header not found. Failing the login test case");

		
	}

	@And("I enter in my username")
	public void enterUsername() throws Throwable {
		logger.info("Executing step: I enter in my username ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPwdPage.usernameTxt));
		driver.findElement(forgotPwdPage.usernameTxt).sendKeys(validUsername);
	}

	@When("I click on Submit button")
	public void clickOnSubmitButton() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPwdPage.submitBtn));
		driver.findElement(forgotPwdPage.submitBtn).click();
	}

	@Then("I expect to be see a confirmation that password was sent to email")
	public void verifyPasswordSentToEmail() throws Throwable {
		logger.info("executing step: I expect to see a confirmation that password was sent to email");
		wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.forgotPwdCnfMsg));
		String expectedConfirmationMsg = "If an account was found for the username provided, you will receive an email shortly with further instructions.";
		String actualConfirmationMsg = driver.findElement(loginPage.forgotPwdCnfMsg).getText();
		Assert.assertTrue(actualConfirmationMsg.contains(expectedConfirmationMsg),"Message not found. Failing the login test case");
		
	}

}