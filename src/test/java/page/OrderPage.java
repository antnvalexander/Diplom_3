package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By header = By.xpath("//h1[text()='Соберите бургер']");
    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By loginAccountButton = By.xpath("//a[@href='/account']");
    private By createOrderButton = By.xpath("//button[text()='Оформить заказ']");
    private By saucesButton = By.xpath("//span[text()='Соусы']");
    private By toppingsButton = By.xpath("//span[text()='Начинки']");
    private By bunButton = By.xpath("//span[text()='Булки']");
    private By bunNameSectionField = By.xpath("//h2[text()='Булки']");
    private By toppingsNameSectionField = By.xpath("//h2[text()='Начинки']");
    private By saucesNameSectionField = By.xpath("//h2[text()='Соусы']");


    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    @Step("Проверили наличие заголовка Булки")
    public void checkBunNameSectionField() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(bunNameSectionField)).isDisplayed();
    }

    @Step("Проверили наличие заголовка Начинки")
    public void checkToppingsNameSectionField() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(toppingsNameSectionField)).isDisplayed();
    }

    @Step("Проверили наличие заголовка Соусы")
    public void checkSaucesNameSectionField() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(saucesNameSectionField)).isDisplayed();
    }

    @Step("Выполнили нажатие кнопки Булки")
    public void clickBunButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(bunButton)).click();
    }

    @Step("Выполнили нажатие кнопки Начинки")
    public void clickToppingsButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(toppingsButton)).click();
    }

    @Step("Выполнили нажатие кнопки Соусы")
    public void clickSaucesButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(saucesButton)).click();
    }

    @Step("Получили текст заголовка")
    public String getHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(header)).getText();
    }

    @Step("Выполнили нажатие кнопки Войти в аккаунт")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).click();
    }

    @Step("Выполнили нажатие кнопки Личный Кабинет")
    public void clickLoginAccountButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginAccountButton)).click();
    }

    @Step("Проверили наличие кнопки Оформить заказ")
    public void createOrderButtonIsVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createOrderButton));
    }

}
