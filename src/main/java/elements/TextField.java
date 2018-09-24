package elements;

import com.codeborne.selenide.SelenideElement;

public class TextField extends AbstractElement {

    public TextField(SelenideElement element, String name) {
        super(element, ".//input", name);
    }

    @Override
    public void setValue(String value) {
        element.setValue(value);
    }
}
