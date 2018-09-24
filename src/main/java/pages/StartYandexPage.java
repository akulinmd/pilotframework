package pages;

import elements.Button;
import elements.TextField;
import factories.PageEntry;
import factories.PageLoadElement;
import org.openqa.selenium.support.FindBy;

@PageEntry(title = "Яндекс")
public class StartYandexPage extends AbstractPage {

    @PageLoadElement
    @FindBy(name = "Строка поиска",
            xpath = "//div[@class = 'search2__input']")
    private TextField textField1;

    @FindBy(name = "Найти",
            xpath = "//div[@class = 'search2__button']")
    private Button button1;
}
