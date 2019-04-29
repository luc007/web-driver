package com.klm.web.driver.condition;


import com.klm.web.driver.standard.condition.Condition;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class PageIsLoadedCondition implements Condition {

    private final WebDriver driver;

    public PageIsLoadedCondition( WebDriver driver ) {
        this.driver = driver;
    }


    @Override
    public boolean evaluate() {
        try {
            Object result = ( (JavascriptExecutor) driver ).executeScript( "return document.readyState;" );

            return "complete".equalsIgnoreCase( result.toString() ) || "loaded".equalsIgnoreCase( result.toString() );
        } catch( Exception ex ) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "page is loaded";
    }
}
