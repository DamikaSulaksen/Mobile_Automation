import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SwagHomePage;
import pages.SwagLoginPage;

import java.io.File;
import java.net.URL;

public class SwagTestNGTest {

    static AndroidDriver driver;
    static SwagLoginPage SwagLoginPage;
    static SwagHomePage SwagHomePage;

    @BeforeClass
    public static void setup(){
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
//            capabilities.setCapability("app", app.getAbsolutePath());

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            SwagLoginPage = new SwagLoginPage(driver);
            SwagHomePage = new SwagHomePage(driver);

        } catch (Exception e) {
            System.out.println("Cause is: "+e.getCause());
            System.out.println("Message is: "+e.getMessage());
            e.printStackTrace();
        }

    }

    @Test
    public void verifyLogin() throws InterruptedException {


        SwagLoginPage.logIn("standard_user", "secret_sauce");
        System.out.println("Login done");

        Assert.assertTrue(SwagHomePage.verifyCartIcon());
        Assert.assertEquals(SwagHomePage.verifyProductDisplay(), "PRODUCTS");

    }

    @AfterClass
    public static void teardown()
    {
        driver.quit();
    }
}
