package stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.ru.И;
import factories.PageFactory;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.Page;
import utils.Init;
import java.util.List;

import static utils.Evaluator.getVariable;
import static utils.Evaluator.setVariable;

public class CommonStepDefs {

    private static final Logger lOG = LoggerFactory.getLogger(CommonStepDefs.class);
    private static WebDriver driver = Init.getDriver();

    private Page getCurrentPage() {
        return Init.getPageFactory().getCurrentPage();
    }

    @И("^пользователь устанавливает переменные:$")
    public void пользовательУстанавливаетПеременные(DataTable dataTable) {
        List<List<String>> lists = dataTable.asLists();
        lists.forEach(line -> setVariable(line.get(0), line.get(1)));
    }

    @И("^пользователь переходит на стартовую страницу$")
    public void пользовательПереходитНаСтартовуюСтраницу() {
        driver.navigate().to(System.getProperty("url"));
    }

    @И("^открывается страница \"([^\"]*)\"$")
    public void открывается(String namePage) {
        Init.getPageFactory().getPageByName(namePage);
    }

    @И("^пользователь заполняет поля значениями:$")
    public void пользовательЗаполняетПоляЗначениями(DataTable dataTable) {
        List<List<String>> lines = dataTable.asLists();
        Page page = getCurrentPage();
        lines.forEach(line -> {
            page.getElement(line.get(0)).setValue(getVariable(line.get(1)));
        });
    }

    @И("^пользователь (?:нажимает кнопку|выбирает переключатель|станавливает чекбокс|нажимает на ссылку) \"([^\"]*)\"$")
    public void пользовательНажимаетКнопку(String nameEl) {
        getCurrentPage().getElement(nameEl).click();
    }
}
