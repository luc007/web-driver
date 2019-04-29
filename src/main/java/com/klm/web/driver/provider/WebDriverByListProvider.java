package com.klm.web.driver.provider;

import com.klm.web.driver.condition.WebConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class WebDriverByListProvider implements Provider<List<WebElement>> {

    private final WebDriver webDriver;
    private final By by;

    public WebDriverByListProvider( WebDriver webDriver, By by ) {

        this.webDriver = webDriver;
        this.by = by;
    }

    @Override
    public List<WebElement> get() {
        if( !WebConditions.pageIsLoaded( webDriver ).evaluate() ) {
            return null;
        }

        try {
            List<WebElement> webElements = webDriver.findElements( by );
            List<WebElement> visibleElements = new ArrayList<WebElement>();

            for( WebElement webElement : webElements ) {
                visibleElements.add( webElement );
            }
            return visibleElements;
        }catch ( WebDriverException webEx ) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "list of " + by.toString();
    }
}
