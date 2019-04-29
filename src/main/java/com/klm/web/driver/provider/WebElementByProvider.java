package com.klm.web.driver.provider;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import javax.inject.Provider;
import java.util.List;

public class WebElementByProvider implements Provider {

    private final Provider<WebElement> webElementProvider;
    private final By by;

    public WebElementByProvider( Provider<WebElement> webElementProvider, By by ) {
        this.webElementProvider = webElementProvider;
        this.by = by;
    }

    @Override
    public WebElement get() {
        WebElement rootWebElement = webElementProvider.get();

        if( rootWebElement != null ){
            try {
                List<WebElement> webElementList = rootWebElement.findElements(by);

                for (WebElement webElement : webElementList) {

                    if (webElement.isDisplayed()) {
                        return webElement;
                    }
                }
                return null;
            }catch ( WebDriverException webEx ) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return by.toString() + " on " + webElementProvider.toString();
    }
}
