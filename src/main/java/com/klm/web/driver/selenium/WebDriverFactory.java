package com.klm.web.driver.selenium;

import com.thoughtworks.selenium.SeleniumException;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

    private static final WebDriverConfig webDriverConfig = WebDriverConfigFactory.getInstance();

    private WebDriverFactory() {
        // Do not instantiate
    }

    public static WebDriver getWebDriver() throws SeleniumException {
        return getWebDriver( webDriverConfig.getWebDriverBrowser() );
    }


    public  static WebDriver getWebDriver( WebDriverBrowser browser ) throws SeleniumException {

        WebDriver webDriver = browser.getWebDriver();

        webDriver.manage().timeouts().implicitlyWait( webDriverConfig.getImplicitWaitSeconds(), TimeUnit.SECONDS );

        if( webDriverConfig.getWindowsMaximize() ) {
            webDriver.manage().window().maximize();
        }

        return webDriver;
    }
}
