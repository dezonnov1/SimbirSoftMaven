import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
/**
 * Класс хранящий настройки конфигурации тестирования
 */
public class BaseTest {
    @BeforeClass
    void initBeforeClass() {
        Configuration.baseUrl = "https://www.simbirsoft.com";
        Configuration.browserSize = "1920x1080";
        //Configuration.browserPosition = "2560x250"; // для отображения на моем 2-м мониторе
        Configuration.timeout = 30000;
    }
}
