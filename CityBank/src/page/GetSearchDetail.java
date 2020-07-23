package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import base.SetUp;

public class GetSearchDetail extends SetUp {
	By txtNID = By.id("pt1:it1::content");
	By txtDOB = By.id("pt1:id1::content");
	By btnVerify = By.xpath("//*[text()='Verify']");
	By verifySuccess = By.xpath("//*[text()='Verification is Successful']");
	By userProfilePhoto;
	By userProfileDetailPhoto = By.id("pt1:i5");
	By btnLogout = By.xpath("//*[text()='Logout']");

	public GetSearchDetail(WebDriver driver) {
		this.driver = driver;
	}

	public void enterNID(String data) {
		sendKeys(txtNID, data);
	}

	public void enterDOB(String data) {
		sendKeys(txtDOB, data);
	}

	public void clickOnVerifyButton() {
		clickOnElement(btnVerify);
	}

	public boolean verifySuccess() {
		return verifyVisible(verifySuccess);
	}

	public String getUserProfilePhoto() {
		return getAttribute(userProfilePhoto, "src");
	}

	public String getUserProfileDetail(String nid) {
		clickOnElement(verifySuccess);
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.DOWN).perform();
		actions.sendKeys(Keys.DOWN).perform();
		actions.sendKeys(Keys.DOWN).perform();
		captureScreenShot(userProfileDetailPhoto, nid);
		clickOnElement(By.id("pt1:glVoterInfo"));
		((JavascriptExecutor) driver).executeScript("document.body.style.zoom='60%';");
		makeScreenshot(nid);
		return getAttribute(userProfileDetailPhoto, "src");
	}

	public boolean verifyUserDetail(String nid, String dob) {
		enterNID(nid);
		enterDOB(dob);
		clickOnVerifyButton();
		return verifySuccess();
	}

	public void clickOnLogOut() {
		clickOnElement(btnLogout);
	}
}
