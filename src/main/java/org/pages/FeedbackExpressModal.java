package org.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.*;

/**
 * Класс страницы формы экспресс-консультации.
 */
public class FeedbackExpressModal {
    /**
     * Кнопка Получить консультацию
     */
    final String FEEDBACK_EXPRESS_MODAL = "//*[@class='fmo-parent']//*[@class='fb-modal-default']";

    @FindBy(xpath = FEEDBACK_EXPRESS_MODAL)
    private SelenideElement feedbackExpressModalWindow;
    /**
     * Чекбокс в форме
     */
    @FindBy(xpath = FEEDBACK_EXPRESS_MODAL + "//input[@class='multi-checkbox']/../..")
    private SelenideElement checkBox;

    /**
     * Проверяет, что модальное окно Экспресс-консультации отобразилось на странице
     * @return текущий экземпляр класса
     */
    @Step("Проверить, что модальное окно Экспресс-консультации отобразилось на странице")
    public FeedbackExpressModal checkModalWindowVisible() {
        feedbackExpressModalWindow.shouldBe(visible);
        return this;
    }

    /**
     * Проверяет, что чекбокс присутствует
     * @return текущий экземпляр класса
     */
    @Step("Проверить, что чекбокс присутствует")
    public FeedbackExpressModal checkCheckBox() {
        checkBox.shouldBe(visible)
                .shouldHave(text("1C, ERP"));
        return this;
    }
}
