package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SwagLoginPage {

    AndroidDriver driver;
    By un = By.xpath("//android.widget.EditText[@content-desc=\"test-Username\"]");
    By pwd = By.xpath("//android.widget.EditText[@content-desc=\"test-Password\"]");
    By submit = By.xpath("//android.widget.EditText[@content-desc=\"test-Login\"]");

    public SwagLoginPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void logIn(String username, String password) throws InterruptedException {

        Thread.sleep(4000);

        driver.findElement(un).sendKeys(username);
        driver.findElement(pwd).sendKeys(password);
        driver.findElement(submit).click();
        Thread.sleep(5000);
    }
}
