package factories;

import org.reflections.Reflections;
import pages.AbstractPage;
import java.util.HashMap;
import java.util.Map;

public final class PageFactory {

    private static AbstractPage currentPage;
    private static String nameCurrentPage;
    private static final Map<String, Class<? extends AbstractPage>> PAGES_CACHE = new HashMap<>();

    static {
        Reflections reflections = new Reflections("pages");
        for (Class<? extends AbstractPage> clazz : reflections.getSubTypesOf(AbstractPage.class)) {
            PageEntry entry = clazz.getAnnotation(PageEntry.class);
            if (entry == null) {
                continue;
            }
            PAGES_CACHE.put(entry.title(), clazz);
        }
    }

    private <T> T getInstancePage(Class<T> clazz) {
        final T page = construct(clazz);
        currentPage = ((AbstractPage) page);
        return page;
    }

    public AbstractPage getPageByName(String name) {
        return get(name);
    }

    public static String getNameCurrentPage() {
        return nameCurrentPage;
    }

    public AbstractPage getCurrentPage() {
        return currentPage;
    }

    private AbstractPage get(String name) {
        final Class<? extends AbstractPage> clazz = PAGES_CACHE.get(name);
        if (clazz == null) {
            throw new NullPointerException("Страница \"" + name + "\" не найдена.");
        }
        nameCurrentPage = name;
        return getInstancePage(clazz);
    }

    private <T> T construct(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new Error("При создании страницы произошла ошибка.", e);
        }
    }

}
