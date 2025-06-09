package test;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page.LoginPage;
import page.OrderPage;
import page.RegisterPage;
import utils.DataGenerator;

import static org.junit.Assert.assertEquals;
import static utils.BaseHelper.REGISTER_URL;

@RunWith(JUnit4.class)
public class UserRegistrationTest {
    private WebDriver driver;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private OrderPage orderPage;

    String locale;
    String name;
    String password;
    String email;

    @Before
    public void setUp() {
        // Инициализация WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(REGISTER_URL);
        registerPage = new RegisterPage(driver);

        locale = "ru";
        DataGenerator.FakeUser fakeUser = DataGenerator.generateUser(locale);
        name = fakeUser.getName();
        password = fakeUser.getPassword();
        email = fakeUser.getEmail();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @DisplayName("Проверка создания пользователя с корректными данными")
    @Test
    public void testSuccessfulUserRegistration() {
        registerPage.fillRegisterForm(name, email, password);
        registerPage.clickRegisterButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();

        orderPage = new OrderPage(driver);
        assertEquals("Соберите бургер", orderPage.getHeaderText());
    }

    @DisplayName("Проверка создания пользователя с некорректным паролем")
    @Test
    public void testUserRegistrationWithIncorrectPass() {
        registerPage.fillRegisterForm(name, email, "123");
        registerPage.clickRegisterButton();

        assertEquals("Некорректный пароль", registerPage.getPasswordErrorText());
    }
}
