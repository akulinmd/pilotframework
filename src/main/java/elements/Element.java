package elements;

public interface Element {

    boolean isDisplayed();

    boolean isEnabled();

    default void setValue(String value) {};

    String getText();

    void click();
}
