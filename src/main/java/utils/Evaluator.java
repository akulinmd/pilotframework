package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Evaluator {

    private static final Logger LOG = LoggerFactory.getLogger(Evaluator.class);
    private static final Map<String, String> STASH = new HashMap<>();
    private static final String VAR_PATTERN = "\\#\\{(?<var>[^{^}]*)\\}";
    private static final Pattern VAR_PATTERN_COMPILED = Pattern.compile(VAR_PATTERN);

    static {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        setVariable("Текущая дата", currentDate);
    }

    public static void setVariable(final String name, final String value) {
        STASH.put(name, value);
        LOG.info(String.format("Установлена переменная \"%s\" со значением \"%s\".", name, value));
    }

    public static String getVariable(final String param) {
        if (param.trim().matches(".*" + VAR_PATTERN + ".*")) {
            Matcher varMatcher = VAR_PATTERN_COMPILED.matcher(param);
            StringBuffer varSB = new StringBuffer();
            while (varMatcher.find()) {
                String name = varMatcher.group("var");
                String var = STASH.get(name);
                if (var == null) {
                    var = System.getProperty(name);
                    if (var != null) STASH.put(name, var);
                }
                String value = String.valueOf(var);
                varMatcher.appendReplacement(varSB, value);
            }
            varMatcher.appendTail(varSB);
            return getVariable(varSB.toString());
        }
        return param;
    }

    public static String removeVariable(final String param) {
        String value = STASH.get(param);
        if (STASH.entrySet().removeIf(entry -> entry.getKey().equals(param)))
            LOG.info(String.format("Удалена переменная \"%s\".", param));
        return value;
    }
}
