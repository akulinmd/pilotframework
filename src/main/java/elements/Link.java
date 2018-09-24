package elements;

import com.codeborne.selenide.SelenideElement;

public class Link extends AbstractElement {

    public Link(SelenideElement element, String name) {
        super(element, ".//a", name);
    }
}
