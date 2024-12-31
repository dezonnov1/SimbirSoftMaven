package org.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Класс главной страницы.
 */
public class MainPage {

    /**
     * Кнопка обратной связи.
     */
    @FindBy(xpath = "//a[@data-gclick='showModalFeedback']")
    private SelenideElement feedbackButton;
    /**
     * Кнопка О нас
     */
    @FindBy(xpath = "//*[contains(@class,'gh-nav-item--about')]")
    private SelenideElement navItemAbout;
    /**
     * Кнопка Получить консультацию в Экспресс-консультация в середине страницы
     */
    @FindBy(xpath = "//*[@data-gclick='showModalFeedbackExpress']") // повторил паттерн за "обратной связи" на 20-й строке
    private SelenideElement feedbackExpressButton;
    /**
     * Кнопка SDET (Разработка в тестировании) в списке из Услуги
     */
    @FindBy(xpath = "//*[contains(@class,'gh-nav-item--services')]")
    private SelenideElement navItemSDET;

    /**
     * Проверяет, что кнопка Написать нам присутствует и кликабельна.
     * @return текущйи экземпляр класса
     */
    @Step("Проверить, что кнопка Написать нам присутствует и кликабельна")
    public FeedbackModal showFeedbackModal() {
        feedbackButton
                .should(exist)
                .shouldBe(visible)
                .shouldHave(text("Написать нам"))
                .click();
        return page(FeedbackModal.class);
    }

    /**
     * Проверяет, что кнопка Контакты нам присутствует и кликабельна, потом переходим по ссылке.
     * @return текущий экземпляр класса
     */
    @Step("Проверить, что при наведении на элемент меню О нас появляется ссылка Контакты и нажать на Контакты")
    public ContactsPage goToContacts() {
        navItemAbout
                .should(exist)
                .shouldBe(visible)
                .shouldHave(text("О нас"))
                .hover();
        $(By.linkText("Контакты"))
                .should(exist)
                .shouldBe(visible)
                .shouldHave(text("Контакты"))
                .click();
        return page(ContactsPage.class);
    }


    /**
     * Проверяет, что кнопка Получить консультацию присутствует и кликабельна
     * @return текущий экземпляр класса
     */
    @Step("Проверить, что кнопка Получить консультацию присутствует и кликабельна")
    public FeedbackExpressModal showFeedbackExpressModal() {
        feedbackExpressButton
                .scrollIntoView("{block: \"center\"}")
                .should(exist)
                .shouldBe(visible)
                .shouldHave(text("Получить консультацию"))
                .click();
        return page(FeedbackExpressModal.class);
    }

    /**
     * Проверяет, что в списке Услуги, кнопка SDET присутствует и кликабельна
     * @return текущий экземпляр класса
     */
    @Step("Проверить, что в списке Услуги, кнопка SDET присутствует и кликабельна")
    public SDETPage goToSDET(){
        navItemSDET
                .should(exist)
                .shouldBe(visible)
                .shouldHave(text("Услуги"))
                .click();
        $(By.linkText("SDET (Разработка в тестировании)"))
                .should(exist)
                .shouldBe(visible)
                .shouldHave(text("SDET (Разработка в тестировании)"))
                .click();
        return page(SDETPage.class);
    }
}
