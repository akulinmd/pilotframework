package utils;

import com.codeborne.selenide.Configuration;
import factories.PageFactory;
import org.openqa.selenium.WebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public final class Init {

    public final static Long ELEMENT_TIMEOUT;
    public final static Long PAGE_LOAD_TIMEOUT;
    private static WebDriver driver;
    private static PageFactory pageFactory;

    static {
        setProperty();
        Configuration.timeout = 0;
        Configuration.collectionsTimeout = 0;
        ELEMENT_TIMEOUT = Long.parseLong(System.getProperty("element.timeout")) * 1000L;
        PAGE_LOAD_TIMEOUT = Long.parseLong(System.getProperty("page.load.timeout")) * 1000L;
        pageFactory = new PageFactory();
        driver = getConfiguredWebDriver();
    }

    public static PageFactory getPageFactory() {
        return pageFactory;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private static WebDriver getConfiguredWebDriver() {
        WebDriver temp = getWebDriver();
        boolean maximize = Boolean.parseBoolean(System.getProperty("browser.maximize"));
        if (maximize)
            temp.manage().window().maximize();
        return temp;
    }

    private static void setProperty() {
        try (FileReader fr = new FileReader("./src/test/resources/config.properties")) {
            Properties props = new Properties();
            props.load(fr);
            props.stringPropertyNames().forEach(key -> {
                String value = props.getProperty(key);
                switch (key) {
                    case "browser.autoclose":
                        key = "selenide.holdBrowserOpen";
                        boolean temp = !Boolean.parseBoolean(value);
                        value = String.valueOf(temp);
                        break;
                    default:
                        break;
                }
                System.setProperty(key, value);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
