package factories;

import com.codeborne.selenide.SelenideElement;
import elements.AbstractElement;
import elements.Element;
import org.openqa.selenium.support.FindBy;
import org.reflections.Reflections;
import pages.AbstractPage;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Predicate;
import static com.codeborne.selenide.Selenide.$x;

public final class FieldFactory {

    final static private Map<String, Class<? extends Element>> ELEMENT_CACHE = new HashMap<>();

    static {
        Reflections reflections = new Reflections("elements");
        for (Class<? extends AbstractElement> clazz : reflections.getSubTypesOf(AbstractElement.class)) {
            ELEMENT_CACHE.put(clazz.getName().split("^.*\\.")[1], clazz);
        }
    }

    public static String getPageLoadElement(Class<? extends AbstractPage> page) {
        return getField(f -> f.getAnnotation(PageLoadElement.class) != null, page)
                .get()
                .getAnnotation(FindBy.class)
                .xpath();
    }

    public static Element getElement(String name, Class<? extends AbstractPage> page) {
        Optional<Field> field = getFields(f -> f.getAnnotation(FindBy.class) != null, page).stream()
                .filter(f -> f.getAnnotation(FindBy.class).name().equals(name)).findFirst();
        return getAbstractField(field.get());

    }

    private static Element getAbstractField(Field field) {
        SelenideElement element = $x(field.getAnnotation(FindBy.class).xpath());
        return construct(field, element);
    }

    private static Element construct(Field field, SelenideElement element) {
        try {
            return (Element) field.getType().getConstructor(SelenideElement.class, String.class)
                    .newInstance(element, field.getAnnotation(FindBy.class).name());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new Error("!!!!");
        }
    }

    private static List<Field> getFields(Predicate<Field> filter, Class<? extends AbstractPage> page) {
        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, page.getDeclaredFields());
        Arrays.stream(page.getAnnotation(PageEntry.class).containsPages())
                .flatMap(aClass -> Arrays.stream(aClass.getDeclaredFields()))
                .forEach(fields::add);

        return fields;
    }

    private static Optional<Field> getField(Predicate<Field> filter, Class<? extends AbstractPage> page) {
        return getFields(filter, page).stream().filter(filter).findFirst();
    }
}
