import org.dao.Person;
import org.pages.MainPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

/**
 * Основной класс выполняющий тесты
 */
public class SimbirSoftTests extends BaseTest {

    @BeforeMethod
    void initBeforeMethod() {
        open("/");
    }

    /**
     * Проверяет вызов и отображение формы "Написать нам"
     */
    @Test(description = "Show feedback modal window test")
    public void feedBackModalOpenTest() {
        page(MainPage.class)
                .showFeedbackModal()
                .checkModalWindowVisible()
                .checkTextInput();
        sleep(5000);
    }

    /**
     * Проверяет переход и нажатие кнопки Контакты
     */
    @Test(description = "Open Contacts page")
    public void openContactsPageTest() {
        page(MainPage.class)
                .goToContacts()
                .contactsPageIsOpen();
        sleep(7000);
    }

    /**
     * Проверка класса Person
     */
    @Test(description = "Create Person test")
    public void createPersonTest() {
        Person person = new Person.Builder()
                .withName("Testoviy Test Testovich")
                .withAge(45)
                .withWeight(80)
                .build();
    }

    /**
     * Проверяет вызов и отображение формы "Экспресс-консультация"
     */
    @Test(description = "Show feedback express modal window test")
    public void showFeedbackExpressModalOpenTest(){
        page(MainPage.class)
                .showFeedbackExpressModal()
                .checkModalWindowVisible()
                .checkCheckBox();
        sleep(3000);
    }

    /**
     * Проверяет вызов и отображение страницы "SDET"
     */
    @Test(description = "Open SDET page")
    public void openSDETPageTest() {
        page(MainPage.class)
                .goToSDET()
                .sdetPageIsOpen();
        sleep(3000);
    }
}
