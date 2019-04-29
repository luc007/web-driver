package com.klm.web.driver.selenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebDriverConfigFactory {

    public static final String WEBDRIVER_CONFIG_PROPERTIES_PROPERTY = "webdriver.config.properties";
    public static final String WEBDRIVER_BROWSER_PROPERTTY = "webdriver.browser";
    public static final String WEBDRIVER_BROWSER_DEFAULT = WebDriverBrowser.chrome.toString();
    public static final String WEBDRIVER_IMPLICIT_WAIT_PROPERTY = "webdriver.implicit.wait.seconds";
    public static final String WEBDRIVER_IMPLICITY_WAIT_DEFAULT = "10";
    public static final String WEBDRIVER_WINDOW_MAXIMIZE_PROPERTY = "webdriver.window.maximize";
    public static final String WEBDRIVER_WINDOW_MAXIMIZE_DEFAULT = "true";

    public static final String WEBDRIVER_CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    public static final String WEBDRIVER_IE_DRIVER_PROPERTY = "webdriver.ie.driver";
    public static final String WEBDRIVER_EDGE_DRIVER_PROPERTY = "webdriver.edge.driver";

    private static WebDriverConfig webDriverConfig = null;

    private WebDriverConfigFactory() {
        // Do not instantiate
    }

    public static synchronized WebDriverConfig getInstance() {

        if( webDriverConfig == null ) {
            String webDriverConfigProperties = System.getProperty( WEBDRIVER_CONFIG_PROPERTIES_PROPERTY );

            if( webDriverConfigProperties != null ) {

                Properties properties = new Properties();
                try (
                        InputStream inputStream = new FileInputStream(webDriverConfigProperties)) {
                    properties.load(inputStream);
                }catch ( IOException ioEx ) {
                    throw new IllegalArgumentException( String.format( "'%s' system property contained an invalid value [%s]",
                            WEBDRIVER_CONFIG_PROPERTIES_PROPERTY, webDriverConfigProperties ), ioEx );
                }

                webDriverConfig = getInstanceFromProperties( properties );
            } else {
                webDriverConfig = getInstanceFromProperties( System.getProperties() );
            }
        }
        return webDriverConfig;
    }


    protected  static synchronized WebDriverConfig getInstanceFromProperties( Properties properties ) {

        WebDriverBrowser browser = WebDriverBrowser.valueOf( properties.getProperty( WEBDRIVER_BROWSER_PROPERTTY, WEBDRIVER_BROWSER_DEFAULT ) );

        int implicityWaitSeconds = Integer.parseInt( properties.getProperty( WEBDRIVER_IMPLICIT_WAIT_PROPERTY, WEBDRIVER_IMPLICITY_WAIT_DEFAULT ) );

        boolean isWindowMaxmize = Boolean.parseBoolean( properties.getProperty( WEBDRIVER_WINDOW_MAXIMIZE_PROPERTY, WEBDRIVER_WINDOW_MAXIMIZE_DEFAULT ) );

        return new WebDriverConfig( browser, implicityWaitSeconds, isWindowMaxmize,
                properties.getProperty( WEBDRIVER_CHROME_DRIVER_PROPERTY ),
                properties.getProperty( WEBDRIVER_IE_DRIVER_PROPERTY ),
                properties.getProperty( WEBDRIVER_EDGE_DRIVER_PROPERTY) );
    }

    protected static synchronized void clearWebDriverConfig() {
        webDriverConfig = null;
    }
}
