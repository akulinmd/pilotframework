package elements;

import com.codeborne.selenide.SelenideElement;
import java.util.List;

public abstract class AbstractElement implements Element {

    protected final SelenideElement element;
    private final String name;

    AbstractElement(SelenideElement element, String postEl, String name) {
        List<SelenideElement> listEls = element.$$x(postEl);
        this.element = listEls.stream()
                .filter(el -> el.isDisplayed() && el.isEnabled())
                .findFirst()
                .get();
        this.name = name;
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public boolean isEnabled() {
        return element.isEnabled();
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public void click() {
        element.click();
    }

    public String getName() {
        return name;
    }
}
