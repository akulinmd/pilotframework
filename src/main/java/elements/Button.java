package elements;

import com.codeborne.selenide.SelenideElement;

public class Button extends AbstractElement {

    public Button(SelenideElement element, String name) {
        super(element, ".//button", name);
    }
}
