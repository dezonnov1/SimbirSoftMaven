package org.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Странца SDET
 */
public class SDETPage {
    /**
     * Проверяет, что страница SDET открылась
     * @return текущий экземпляр класса
     */
    @Step("Проверить, что страница SDET открылась")
    public SDETPage sdetPageIsOpen() {
        $(By.xpath("//div[@class='hl-info']//p[@class='hl-request__footnote']"))
                .shouldHave(text("*SDET — Software Development Engineer in Test (разработчик ПО для автоматизация тестирования)"))
                .shouldBe(visible);
        return this;
    }
}
