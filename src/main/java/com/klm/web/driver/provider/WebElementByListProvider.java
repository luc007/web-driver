package com.klm.web.driver.provider;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class WebElementByListProvider implements Provider {

    private final Provider<WebElement> webElementProvider;
    private final By by;

    public WebElementByListProvider( Provider<WebElement> webElementProvider, By by ){
        this.webElementProvider = webElementProvider;
        this.by = by;
    }

    @Override
    public List<WebElement> get() {
        WebElement rootWebElement = webElementProvider.get();

        if( rootWebElement != null ) {
            try {
                List<WebElement> webElementList = rootWebElement.findElements(by);
                List<WebElement> visibleElementList = new ArrayList<WebElement>();

                for (WebElement webElement : webElementList) {
                    if (webElement.isDisplayed()) {
                        visibleElementList.add(webElement);
                    }
                }
                return visibleElementList;
            }catch ( WebDriverException webEx ) {
                return null;
            }
        } else  {
            return null;
        }
    }

    @Override
    public String toString() {
        return "list of " + by.toString() + " on " + webElementProvider.toString();
    }
}
