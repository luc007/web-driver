package com.klm.web.driver.provider;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.inject.Provider;

public class WebProviders {

    public static Provider<WebElement> find(WebDriver webDriver, By by ) {
        return new WebDriverByProvider( webDriver, by);
    }
}
