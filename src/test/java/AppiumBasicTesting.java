import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AppiumBasicTesting {
    static AndroidDriver driver;
    public static void main(String[] args){

        try {

            File appDir = new File("D:\\Selenium\\Mobile_Automation\\Apps");
            File app = new File(appDir, "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("platformVersion", "11.0");
            capabilities.setCapability("deviceName", "emulator-5554");
            capabilities.setCapability("appPackage", "com.swaglabsmobileapp");
            capabilities.setCapability("appActivity", "com.swaglabsmobileapp.MainActivity");
            capabilities.setCapability("automationName", "UiAutomator2");
            capabilities.setCapability("app", "UiAutomator2");
            capabilities.setCapability("app", app.getAbsolutePath());

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

            Thread.sleep(3000);

            By username = By.xpath("//android.widget.EditText[@content-desc=\"test-Username\"]");
            By pwd = By.xpath("//android.widget.EditText[@content-desc=\"test-Password\"]");
            By submit = By.xpath("//android.widget.EditText[@content-desc=\"test-Login\"]");

            By cart = By.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView");
            By product_display = By.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");


            driver.findElement(username).sendKeys("standard_user");
            driver.findElement(pwd).sendKeys("secret_sauce");
            driver.findElement(submit).click();

            Thread.sleep(3000);

            if(driver.findElement(cart).isDisplayed()){
                System.out.println("Cart icon is visible");
            }
            if(driver.findElement(product_display).getText().equals("PRODUCTS")){
                System.out.println("The name product is displayed correctly");
            }


        } catch (Exception e) {
            System.out.println("Cause is: "+e.getCause());
            System.out.println("Message is: "+e.getMessage());
            e.printStackTrace();
        }

    }
}
