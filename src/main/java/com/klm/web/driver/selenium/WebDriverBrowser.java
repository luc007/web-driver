package com.klm.web.driver.selenium;

import com.thoughtworks.selenium.SeleniumException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public enum WebDriverBrowser {

    chrome( "/bin/chrome/chromedriver.exe" ) {

        @Override
        public ChromeDriver getWebDriver() {
            String binaryPath = WebDriverConfigFactory.getInstance().getChromeDriverPath();
            System.setProperty( "webdriver.chrome.driver", ( binaryPath != null ) ? binaryPath : getDefaultBinaryPath() );

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments( "--test-type" );

            return new ChromeDriver( chromeOptions );
        }
    },

    firefox( null ) {

        @Override
        public FirefoxDriver getWebDriver() {
            return new FirefoxDriver();
        }
    },

    ie32( "/bin/win32/IEDriverServer.exe" ) {

        @Override
        public InternetExplorerDriver getWebDriver() {
            String binaryPath = WebDriverConfigFactory.getInstance().getIeDriverPath();
            System.setProperty( "webdriver.ie.driver", ( binaryPath != null ) ? binaryPath : getDefaultBinaryPath() );

            return new InternetExplorerDriver();
        }
    },

    ie64( "/bin/win64/IEDriverServer.exe" ) {

        @Override
        public InternetExplorerDriver getWebDriver() {
            String binaryPath = WebDriverConfigFactory.getInstance().getIeDriverPath();
            System.setProperty( "webdriver.ie.driver", ( binaryPath != null ) ? binaryPath : getDefaultBinaryPath() );

            return new InternetExplorerDriver();
        }
    },

    edge( "/bin/edge/msedgedriver.exe" ) {

        @Override
        public InternetExplorerDriver getWebDriver() {
            String binaryPath = WebDriverConfigFactory.getInstance().getIeDriverPath();
            System.setProperty( "webdriver.edge.driver", ( binaryPath != null ) ? binaryPath : getDefaultBinaryPath() );

            return new InternetExplorerDriver();
        }
    };


    public final String defaultDriverPath;

    private File defaultBinary = null;

    private WebDriverBrowser( String defaultDriverPath ) {
        this.defaultDriverPath = defaultDriverPath;
    }

    public abstract WebDriver getWebDriver();

    public String getDefaultBinaryPath() throws SeleniumException {

        if( defaultDriverPath != null ) {
            if( ( defaultBinary == null ) || !defaultBinary.isFile() ) {
                defaultBinary = getUniqueTempDir( defaultDriverPath );

                if( !defaultBinary.isFile() ) {
                    try {
                        URL binaryURL = WebDriverBrowser.class.getResource( defaultDriverPath );
                        FileUtils.copyURLToFile( binaryURL, defaultBinary );
                    }catch ( IOException e ) {
                        throw new SeleniumException( String.format( "Could not extract default binary [%s]", defaultDriverPath ) );
                    }
                }
            }
        }
        return  defaultBinary.getAbsolutePath();
    }


    protected static File getUniqueTempDir( String defaultDriverPath ) {
        try( InputStream fileContent = WebDriverBrowser.class.getResourceAsStream( defaultDriverPath ) ) {
            File uniqueDir = new File( FileUtils.getTempDirectory(), DigestUtils.md5Hex( fileContent ) );

            return new File( uniqueDir, defaultDriverPath );
        }catch( IOException ioEx ) {
            throw new SeleniumException( String.format( "Could not create temp folder for default binary [%s]", defaultDriverPath ), ioEx );
        }


    }
}
