package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SwagCart {
    private final AndroidDriver<WebElement> driver;

    private final By itemInCart = By.xpath("//android.view.ViewGroup[@content-desc=\"test-Item\"]");
    private final By itemName = By.xpath("//android.widget.TextView[@text=\"Sauce Labs Backpack\"]");
    private final By checkoutButton = By.xpath("//android.view.ViewGroup[@content-desc=\"test-CHECKOUT\"]");
    private final By firstNameField = By.xpath("//android.widget.EditText[@content-desc=\"test-First Name\"]");
    private final By lastNameField = By.xpath("//android.widget.EditText[@content-desc=\"test-Last Name\"]");
    private final By zipCodeField = By.xpath("//android.widget.EditText[@content-desc=\"test-Zip/Postal Code\"]");
    private final By emptyZipCodeError = By.xpath("//android.widget.TextView[@text=\"Postal Code is required\"]");
    private final By continueButton = By.xpath("//android.view.ViewGroup[@content-desc=\"test-CONTINUE\"]");
    private final By totalText = By.xpath("//android.widget.TextView[@text=\"Total: $32.39\"]");
    private final By finishButton = By.xpath("//android.view.ViewGroup[@content-desc=\"test-FINISH\"]");
    private final By thankYouMessage = By.xpath("//android.widget.TextView[@text=\"THANK YOU FOR YOUR ORDER\"]");
    private final By hamburgerIcon = By.xpath("//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView");

    public SwagCart(AndroidDriver<WebElement> driver) {
        this.driver = driver;
    }

    public boolean isItemDisplayedInCart() {
        return isElementPresent(itemInCart);
    }

    public void proceedToCheckout(String firstName, String lastName, String zipCode) {
        if (isItemDisplayedInCart()) {
            waitForElementToBeClickable(checkoutButton).click();
            fillTextField(firstNameField, firstName);
            fillTextField(lastNameField, lastName);
            fillTextField(zipCodeField, zipCode);
            waitForElementToBeClickable(continueButton).click();
            scrollToElement(driver.findElement(finishButton));
            waitForElementToBeClickable(finishButton).click();
            waitForThankYouMessage();
        } else {
            System.out.println("Cart is Empty");
        }
    }

    private void scrollToElement(WebElement element) {
        int elementHeight = element.getSize().getHeight();
        int startScroll = element.getLocation().getY() + (int) (elementHeight * 0.8);
        int endScroll = element.getLocation().getY() + (int) (elementHeight * 0.2);
        int screenWidth = driver.manage().window().getSize().getWidth();

        new TouchAction<>(driver)
                .press(PointOption.point(screenWidth / 2, startScroll))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(screenWidth / 2, endScroll))
                .release()
                .perform();
    }

    public void proceedWithEmptyZipCode(String firstName, String lastName, String zipCode) {
        waitForElementToBeClickable(checkoutButton).click();
        fillTextField(firstNameField, firstName);
        fillTextField(lastNameField, lastName);
        fillTextField(zipCodeField, zipCode);
        waitForElementToBeClickable(continueButton).click();
    }

    public void clickHamburgerIcon() {
        waitForElementToBeClickable(hamburgerIcon).click();
    }

    public String checkItemName() {
        return waitForElementToBeVisible(itemName).getText();
    }

    public String checkEmptyZipCodeError() {
        return waitForElementToBeVisible(emptyZipCodeError).getText();
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement waitForElementToBeClickable(By locator) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitForElementToBeVisible(By locator) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void fillTextField(By locator, String text) {
        WebElement element = waitForElementToBeClickable(locator);
        element.clear();
        element.sendKeys(text);
    }

    private void waitForThankYouMessage() {
        waitForElementToBeVisible(thankYouMessage);
    }
}
