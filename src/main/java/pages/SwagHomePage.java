package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SwagHomePage {

    AndroidDriver driver;
    By ct = By.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView");
    By pd = By.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");

    public SwagHomePage(AndroidDriver driver) {
        this.driver = driver;
    }

    public boolean verifyCartIcon(){
        return driver.findElement(ct).isDisplayed();
    }

    public String verifyProductDisplay(){
        return driver.findElement(pd).getText();
    }

}
