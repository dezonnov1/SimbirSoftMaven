package org.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Класс страницы "Контакты"
 */
public class ContactsPage {
    /**
     * Проверяет что страница "Контакты" открылась
     * @return ContactsPage
     */
    @Step("Проверить, что страница контакты открылась")
    public ContactsPage contactsPageIsOpen() {
        $(By.xpath("//h1"))
                .shouldHave(text("Контакты"))
                .shouldBe(visible);
        return this;
    }
}
