import com.codeborne.selenide.Selenide;
import org.slf4j.helpers.Slf4jEnvUtil;
import org.testng.Assert;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class ExampleSelenide {
    @Test
    public void simbirsoftFeedBack(){
        Selenide.open("https://www.simbirsoft.com/");
        Selenide.$(By.cssSelector("a[class=\"gh-tools-feedback write-to-us\"]"))
                .shouldBe(Condition.visible)
                .click();
    }

    /**
     * Заходим на сайт https://www.planetminecraft.com
     * наводимся на выпадающее меню Minecraft
     * нажимаем на кнопку Servers, которая добавляет к текущей ссылке /servers/
     * проверяем соответствие ссылки с https://www.planetminecraft.com/servers/
     */
    @Test
    public void planetMCMenu(){
        Selenide.open("https://www.planetminecraft.com");
        Selenide.$(By.xpath("//a [@class='nav_dropdown' and @id='menu_resources']"))
                .hover();
        Selenide.$(By.xpath("//a[@data-id = 'servers/' and @title='Minecraft Servers']"))
                .shouldBe(Condition.visible)
                .click();
        Assert.assertEquals(Selenide.webdriver().driver().url(),"https://www.planetminecraft.com/servers/"
                ,"открылся не https://www.planetminecraft.com/servers/");
    }
}
