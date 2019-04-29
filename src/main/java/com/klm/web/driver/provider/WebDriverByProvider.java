package com.klm.web.driver.provider;

import javax.inject.Provider;
import com.klm.web.driver.condition.WebConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;


public final class WebDriverByProvider implements Provider<WebElement> {

    private final WebDriver webDriver;
    private final By by;


    public WebDriverByProvider( WebDriver webDriver, By by ) {
        this.webDriver = webDriver;
        this.by = by;
    }


    @Override
    public WebElement get() {
        if( !WebConditions.pageIsLoaded( webDriver ).evaluate() ) {
            return null;
        }

        try {
            List<WebElement> webElementList = webDriver.findElements( by );
            for( WebElement webElement : webElementList ) {
                if( webElement.isDisplayed() ) {
                    return webElement;
                }
            }
            return null;
        } catch ( WebDriverException webEx ) {
            return null;
        }
    }


    @Override
    public String toString() {
        return by.toString();
    }

}
