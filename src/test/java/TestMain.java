package tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SwagCart;
import pages.SwagHomePage;
import pages.SwagLoginPage;
import pages.SwagNavigatorPage;

import java.io.File;
import java.net.URL;

public class TestMain {
    static AndroidDriver<WebElement> driver;
    static SwagLoginPage swagLoginPage;
    static SwagHomePage swagHomePage;
    static SwagCart swagCart;
    static SwagNavigatorPage swagNavigatorPage;

    @BeforeClass
    public static void Setup(){
        try {
            File appDir = new File("D:\\Selenium\\Mobile_Automation\\Apps");
            File app = new File(appDir, "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("platformVersion","9.0");
            capabilities.setCapability("appium:deviceName", "emulator-5554");
            capabilities.setCapability("appium:appPackage", "com.swaglabsmobileapp");
            capabilities.setCapability("appium:appActivity", "com.swaglabsmobileapp.MainActivity");
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("app", app.getAbsolutePath());

            driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

            swagHomePage = new SwagHomePage(driver);
            swagLoginPage = new SwagLoginPage(driver);
            swagCart = new SwagCart(driver);
            swagNavigatorPage = new SwagNavigatorPage(driver);

        } catch (Exception e) {
            System.out.println("Cause is: "+e.getCause());
            System.out.println("Message is: "+e.getMessage());
            e.printStackTrace();
        }
    }

    // Test Cases for SwagCart

    // Test Case 01: Verify that the item is displayed in the cart
    @Test
    public void testCase01() {
        swagHomePage.login("standard_user", "secret_sauce");
        swagHomePage.addToCart();
        swagHomePage.openCart();
        Assert.assertTrue(swagCart.isItemDisplayedInCart(), "Item is not displayed in the cart.");
    }

    // Test Case 02: Verify proceeding to checkout with empty zip code
    @Test
    public void testCase02() {
        swagHomePage.login("standard_user", "secret_sauce");
        swagHomePage.addToCart();
        swagHomePage.openCart();
        swagCart.proceedWithEmptyZipCode("Damika", "DeSilva", "");
        Assert.assertEquals(swagCart.checkEmptyZipCodeError(), "Postal Code is required", "Empty zip code error message is not displayed.");
    }

    // Test Case 03: Verify proceeding to checkout successfully
    @Test
    public void testCase03() {
        swagHomePage.login("standard_user", "secret_sauce");
        swagHomePage.addToCart();
        swagHomePage.openCart();
        swagCart.proceedToCheckout("Damika", "DeSilva", "10300");
        Assert.assertTrue(swagCart.isThankYouMessageDisplayed(), "Thank you message is not displayed after checkout.");
    }

    // Test Case 04: Verify clicking on hamburger icon and logout
    @Test
    public void testCase04() {
        swagHomePage.login("standard_user", "secret_sauce");
        swagHomePage.clickHamburgerIcon();
        swagNavigatorPage.logout();
        Assert.assertTrue(swagLoginPage.isLoginPageDisplayed(), "Login page is not displayed after logout.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
