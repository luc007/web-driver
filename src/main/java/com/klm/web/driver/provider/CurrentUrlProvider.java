package com.klm.web.driver.provider;

import org.openqa.selenium.WebDriver;
import javax.inject.Provider;

public final class CurrentUrlProvider implements Provider<String>{

    private final WebDriver driver;

    public CurrentUrlProvider( WebDriver driver ) {
        this.driver = driver;
    }

    @Override
    public String get() {
        return driver.getCurrentUrl();
    }

    @Override
    public String toString() {
        return "current url";
    }
}
