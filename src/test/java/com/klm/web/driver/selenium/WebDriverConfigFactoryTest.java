package com.klm.web.driver.selenium;

import com.thoughtworks.selenium.SeleniumException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class WebDriverConfigFactoryTest {

    private final WebDriverBrowser defaultBrowser = WebDriverBrowser.valueOf( WebDriverConfigFactory.WEBDRIVER_BROWSER_DEFAULT );
    private final int defaultImplicitWaitSeconds = Integer.parseInt( WebDriverConfigFactory.WEBDRIVER_IMPLICITY_WAIT_DEFAULT );
    private final boolean defaultWindowMaximize = Boolean.parseBoolean( WebDriverConfigFactory.WEBDRIVER_WINDOW_MAXIMIZE_DEFAULT );

    private static WebDriverBrowser alternateBrowser = WebDriverBrowser.ie64;
    private static int alternateImplicitWait = Integer.parseInt( WebDriverConfigFactory.WEBDRIVER_IMPLICITY_WAIT_DEFAULT ) + 5;
    private static boolean alternateWindowMaximize = !Boolean.parseBoolean( WebDriverConfigFactory.WEBDRIVER_WINDOW_MAXIMIZE_DEFAULT );

    private static File tempDir;
    private static File alterChromeBinary;
    private static File alterIE32Binary;
    private static File alterIE64Binary;
    private static File alterEdgeBinary;


    @Test
    public void testDefaultWebDriverConfig() throws Exception {
        assertWebDriverConfig( defaultBrowser, defaultImplicitWaitSeconds, defaultWindowMaximize,
                null, null, null );
    }

    @Test
    public void testSystemPropertyBrowser() {
        System.setProperty( WebDriverConfigFactory.WEBDRIVER_BROWSER_PROPERTTY, alternateBrowser.toString() );

        Assert.assertFalse( alternateBrowser.toString().equals( WebDriverConfigFactory.WEBDRIVER_BROWSER_DEFAULT ) );

        assertWebDriverConfig( alternateBrowser, defaultImplicitWaitSeconds, defaultWindowMaximize,
                null, null, null);
    }


    @Test
    public void testSystemPropertyImplicitWaitSeconds() throws Exception {
        System.setProperty( WebDriverConfigFactory.WEBDRIVER_IMPLICIT_WAIT_PROPERTY,
                Integer.toString( alternateImplicitWait ) );

        assertWebDriverConfig( defaultBrowser, defaultImplicitWaitSeconds,
                defaultWindowMaximize, null, null, null);
    }


    @Test
    public void testSystemPropertyWindowMaximize() throws Exception {
        System.setProperty( WebDriverConfigFactory.WEBDRIVER_WINDOW_MAXIMIZE_PROPERTY,
                Boolean.toString( alternateWindowMaximize ) );

        assertWebDriverConfig( defaultBrowser, defaultImplicitWaitSeconds,
                defaultWindowMaximize, null, null, null);
    }


    @Test
    public void testSystemPropertyChromeBinary() throws Exception {
        System.setProperty( WebDriverConfigFactory.WEBDRIVER_CHROME_DRIVER_PROPERTY,
                alterChromeBinary.getAbsolutePath() );

        assertWebDriverConfig( defaultBrowser, defaultImplicitWaitSeconds,
                defaultWindowMaximize, null, null, null);
    }

    @Test
    public void testSystemPropertyIE32Binary() throws Exception {
        System.setProperty( WebDriverConfigFactory.WEBDRIVER_IE_DRIVER_PROPERTY,
                alterIE32Binary.getAbsolutePath() );

        assertWebDriverConfig( defaultBrowser, defaultImplicitWaitSeconds,
                defaultWindowMaximize, null, null, null);
    }

    @Test
    public void testSystemPropertyIE64Binary() throws Exception {
        System.setProperty( WebDriverConfigFactory.WEBDRIVER_IE_DRIVER_PROPERTY,
                alterIE64Binary.getAbsolutePath() );

        assertWebDriverConfig( defaultBrowser, defaultImplicitWaitSeconds,
                defaultWindowMaximize, null, null, null);
    }

    @Test
    public void testSystemPropertyEdgeBinary() throws Exception {
        System.setProperty( WebDriverConfigFactory.WEBDRIVER_EDGE_DRIVER_PROPERTY,
                alterChromeBinary.getAbsolutePath() );

        assertWebDriverConfig( defaultBrowser, defaultImplicitWaitSeconds,
                defaultWindowMaximize, null, null, null);
    }


    @Before
    public void setUp() throws Exception {
        System.clearProperty( WebDriverConfigFactory.WEBDRIVER_CONFIG_PROPERTIES_PROPERTY );
        System.clearProperty( WebDriverConfigFactory.WEBDRIVER_BROWSER_PROPERTTY );
        System.clearProperty( WebDriverConfigFactory.WEBDRIVER_IMPLICIT_WAIT_PROPERTY );
        System.clearProperty( WebDriverConfigFactory.WEBDRIVER_WINDOW_MAXIMIZE_PROPERTY );
        System.clearProperty( WebDriverConfigFactory.WEBDRIVER_CHROME_DRIVER_PROPERTY );
        System.clearProperty( WebDriverConfigFactory.WEBDRIVER_IE_DRIVER_PROPERTY );
        System.clearProperty( WebDriverConfigFactory.WEBDRIVER_EDGE_DRIVER_PROPERTY );

        WebDriverConfigFactory.clearWebDriverConfig();
    }

    @BeforeClass
    public static void preSetUp() throws Exception {
        tempDir = new File( FileUtils.getTempDirectory(), UUID.randomUUID().toString() );

        alterChromeBinary = new File( tempDir, WebDriverBrowser.chrome.defaultDriverPath );
        alterIE32Binary = new File( tempDir, WebDriverBrowser.ie32.defaultDriverPath );
        alterIE64Binary = new File( tempDir, WebDriverBrowser.ie64.defaultDriverPath );
        alterEdgeBinary = new File( tempDir, WebDriverBrowser.edge.defaultDriverPath );

        try {
            FileUtils.copyURLToFile( WebDriverBrowser.class.getResource( WebDriverBrowser.chrome.defaultDriverPath ), alterChromeBinary );
            FileUtils.copyURLToFile( WebDriverBrowser.class.getResource( WebDriverBrowser.ie32.defaultDriverPath ), alterIE32Binary );
            FileUtils.copyURLToFile( WebDriverBrowser.class.getResource( WebDriverBrowser.ie64.defaultDriverPath ), alterIE64Binary );
            FileUtils.copyURLToFile( WebDriverBrowser.class.getResource( WebDriverBrowser.edge.defaultDriverPath ), alterEdgeBinary );
        } catch ( IOException ioEx ) {
            throw new SeleniumException( "Could not extract driver binaries to temp location" );
        } finally {
            tempDir.deleteOnExit();
        }

    }

    private void assertWebDriverConfig( WebDriverBrowser browser, int implicitWaitSeconds,
                                        boolean windowMaximize, String chromeDriverPath,
                                        String ieDriverPath, String edgeDriverPath ) {

        WebDriverConfig webDriverConfig = WebDriverConfigFactory.getInstance();

        Assert.assertEquals( browser, webDriverConfig.getWebDriverBrowser() );
        Assert.assertEquals( implicitWaitSeconds, webDriverConfig.getImplicitWaitSeconds() );
        Assert.assertEquals( windowMaximize, webDriverConfig.getWindowsMaximize() );

        Assert.assertEquals( chromeDriverPath, webDriverConfig.getChromeDriverPath() );
        Assert.assertEquals( ieDriverPath, webDriverConfig.getIeDriverPath() );
        Assert.assertEquals( edgeDriverPath, webDriverConfig.getEdgeDriverPath() );

    }
}
