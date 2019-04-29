package com.klm.web.driver.selenium;

public class WebDriverConfig {

    private final WebDriverBrowser browser;
    private final int implicitWait;
    private final boolean isWindowMaximize;
    private final String chromeDriverPath;
    private final String ieDriverPath;
    private final String edgeDriverPath;


    public WebDriverConfig( WebDriverBrowser browser, int implicitWait, boolean windowMaximize,
                            String chromeDriverPath, String ieDriverPath, String edgeDriverPath ) {

        if( browser == null ) {
            throw new IllegalArgumentException( "Browser argument cannot be null" );
        }

        if( implicitWait < 0 ) {
            throw new IllegalArgumentException("Implicit Wait cannot be negative");
        }

        this.browser = browser;
        this.implicitWait = implicitWait;
        this.isWindowMaximize = windowMaximize;
        this.chromeDriverPath = chromeDriverPath;
        this.ieDriverPath = ieDriverPath;
        this.edgeDriverPath = edgeDriverPath;
    }

    public WebDriverBrowser getWebDriverBrowser() {
        return this.browser;
    }

    public int getImplicitWaitSeconds() {
        return this.implicitWait;
    }

    public boolean getWindowsMaximize() {
        return this.isWindowMaximize;
    }

    public String getChromeDriverPath() {
        return this.chromeDriverPath;
    }

    public String getIeDriverPath() {
        return this.ieDriverPath;
    }

    public String getEdgeDriverPath() {
        return this.edgeDriverPath;
    }

}
