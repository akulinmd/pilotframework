package pages;

import elements.Element;
import factories.FieldFactory;
import factories.PageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$x;
import static utils.Init.PAGE_LOAD_TIMEOUT;

public abstract class AbstractPage implements Page {

    public AbstractPage() {
        try {
            new FluentWait<>(FieldFactory.getPageLoadElement(this.getClass()))
                    .withTimeout(Duration.ofMillis(PAGE_LOAD_TIMEOUT))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class)
                    .until(xpath -> $x(xpath).isDisplayed());
        } catch (TimeoutException ex) {
            throw new Error(String.format("Превышено время ожидания загрузки страницы \"%s\".", PageFactory.getNameCurrentPage()));
        }
    }

    @Override
    public Element getElement(String nameEl) {
        return FieldFactory.getElement(nameEl, this.getClass());
    }
}
