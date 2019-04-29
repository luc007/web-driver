package com.klm.web.driver.condition;

import org.openqa.selenium.WebDriver;

public final class WebConditions {

    public static PageIsLoadedCondition pageIsLoaded( WebDriver driver) {
        return new PageIsLoadedCondition( driver );
    }
}
