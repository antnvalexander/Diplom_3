package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By nameField = By.xpath("//input[@name='Name']");
    private By loginField = By.xpath("//input[@name='name']");
    private By profileField = By.xpath("//a[@href='/account/profile']");
    private By constructorField = By.xpath("//p[text()='Конструктор']");
    private By logoffButton = By.xpath("//button[text()='Выход']");


    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Проверили текст кнопки Профиль")
    public String getProfileFieldText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(profileField)).getText();
    }

    @Step("Получили текст поля Имя")
    public String getNameFieldValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).getDomAttribute("value");
    }

    @Step("Получили текст поля Логин")
    public String getLoginFieldValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginField)).getDomAttribute("value");
    }

    @Step("Выполнен переход в Конструктор")
    public void clickToConstructor() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(constructorField)).click();
    }

    @Step("Выполнили выход пользователя")
    public void clickLogoffButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoffButton)).click();
    }
}
