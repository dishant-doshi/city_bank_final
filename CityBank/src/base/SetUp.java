package base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import page.GetSearchDetail;
import page.LoginPage;

public class SetUp {
	private String DEPENDENCIES_FOLDER = "./dependencies" + File.separator;
	public WebDriver driver;
	protected String testURL = "https://192.168.249.10/partner/faces/login.jspx";
	private int MAX_WAIT_TIME_IN_SEC = 120;
	public String userName = "citybank20_city";
	public String password = "123456";
	public LoginPage loginPage;
	public GetSearchDetail getSearchDetail;

	public void setUp() {
		driver = initChromeDriver();
		driver.get(testURL);
		loginPage = new LoginPage(driver);
		getSearchDetail = new GetSearchDetail(driver);
		loginPage.login(userName, password);
	}

	public void closeBrowser() {
		driver.quit();
	}

	private WebDriver initChromeDriver() {
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", DEPENDENCIES_FOLDER + "chromedriver.exe");
		ChromeOptions option = setChromeOptions();
		return new ChromeDriver(option);
	}

	private ChromeOptions setChromeOptions() {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", Integer.valueOf(0));
		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] { "headless" });
		options.addArguments(new String[] { "window-size=1200x600" });
		return options;
	}

	public void clickOnElement(By locator) {
		WebElement element = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
		}
		try {
			if (element == null)
				throw new Exception();
			else
				element.click();
		} catch (Exception e) {
			e.printStackTrace();
			exceptionOnFailure(false, e.toString());
		}
	}

	public void exceptionOnFailure(boolean success, String message) {
		if (!success) {
			Assert.assertTrue(false, message);
		}
	}

	private Map<WebElement, String> waitForElementState(By locator) {
		WebElement element;
		Map<WebElement, String> map = new HashMap<WebElement, String>();
		element = getElement(locator);
		String message = "";
		if (element == null) {
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put(element, message);
		return map;

	}

	public WebElement getElement(By by) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIME_IN_SEC);
		try {
			element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if (!isVisibleInViewport(element))
				scrollToElement(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
		if (!isVisibleInViewport(element)) {
			((JavascriptExecutor) driver).executeScript(
					"window.scrollTo(" + element.getLocation().x + "," + (element.getLocation().y - 80) + ");");
		}
	}

	public String getAttribute(By locator, String attributeName) {
		String attributeValue = null;
		WebElement element = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
		}
		try {
			if (element == null)
				throw new Exception();
			else
				attributeValue = element.getAttribute(attributeName);
		} catch (Exception e) {
			e.printStackTrace();
			exceptionOnFailure(false, e.toString());
		}
		return attributeValue;
	}

	private boolean isVisibleInViewport(WebElement element) {
		return ((Boolean) ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(
				"var elem = arguments[0],                   box = elem.getBoundingClientRect(),      cx = box.left + box.width / 2,           cy = box.top + box.height / 2,           e = document.elementFromPoint(cx, cy); for (; e; e = e.parentElement) {           if (e === elem)                            return true;                         }                                        return false;                            ",
				new Object[] { element })).booleanValue();
	}

	public void sendKeys(By locator, String data) {
		WebElement element = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				element.clear();
				element.sendKeys(data);
			}
		} catch (Exception e) {
			exceptionOnFailure(false, e.toString());
		}
	}

	public boolean verifyVisible(By locator) {
		WebElement element = isDisplayed(locator);
		return element != null ? element.isDisplayed() : false;
	}

	public WebElement isDisplayed(By locator) {
		WebElement element = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
		}
		try {
			if (element == null)
				throw new Exception();
		} catch (Exception e) {
			exceptionOnFailure(false, e.toString());
		}
		return element;
	}

	public void captureScreenShot(By locator, String nid) {
		WebElement ele = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			ele = entry.getKey();
		}
		try {
			if (ele == null)
				throw new Exception();
			else {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				String currentDate = dateFormat.format(date);
				String currentTime = timeFormat.format(date);
				String imageName = nid + "_" + currentDate.replaceAll("/", "_") + "_" + currentTime.replace(":", "_")
						+ ".png";
				File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				BufferedImage fullImg = ImageIO.read(screenshot);
				Point point = ele.getLocation();
				int eleWidth = ele.getSize().getWidth();
				int eleHeight = ele.getSize().getHeight();
				BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
				ImageIO.write(eleScreenshot, "png", screenshot);
				File screenshotLocation = new File("./Images/" + imageName);
				FileUtils.copyFile(screenshot, screenshotLocation);
			}
		} catch (Exception e) {
			exceptionOnFailure(false, e.toString());
		}
	}

	public String makeScreenshot(String testMethod) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		String currentTime = timeFormat.format(date);
		String currentDir = System.getProperty("user.dir");
		String folderPath = currentDir + File.separator + "Screenshots" + File.separator + userName;
		folderPath = folderPath.trim();
		String screenshotName = currentDate.replaceAll("/", "_") + "_" + currentTime.replace(":", "_") + ".jpg";
		String filePath = folderPath + File.separator + testMethod + "_" + screenshotName;
		filePath = filePath.trim();
		File screenshotLocation = new File(folderPath);
		if (!screenshotLocation.getAbsoluteFile().exists())
			screenshotLocation.mkdir();
		File screenshot;
		WebDriver augmentedDriver = null;
		augmentedDriver = new Augmenter().augment(driver);
		screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		try {
			File f = new File(filePath);
			FileUtils.copyFile(screenshot, f);
			return filePath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Failed to capture a sccreenshot";
	}
}
