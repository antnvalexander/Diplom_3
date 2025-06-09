package test;

import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page.LoginPage;
import page.OrderPage;
import page.RecoveryPasswordPage;
import page.RegisterPage;
import utils.DataGenerator;

import static org.junit.Assert.assertEquals;
import static steps.CreateUserSteps.createUser;
import static steps.CreateUserSteps.getClearAccessToken;
import static steps.DeleteUserSteps.deleteUser;
import static utils.BaseHelper.BASE_URL;
import static utils.BaseHelper.REGISTER_URL;

@RunWith(JUnit4.class)
public class UserLoginTest {
    private WebDriver driver;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private OrderPage orderPage;
    private RecoveryPasswordPage recoveryPasswordPage;

    String locale;
    String name;
    String password;
    String email;
    String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

        // Инициализация WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);

        locale = "ru";
        DataGenerator.FakeUser fakeUser = DataGenerator.generateUser(locale);
        name = fakeUser.getName();
        password = fakeUser.getPassword();
        email = fakeUser.getEmail();

        Response createUserResponse = createUser(new UserDto(name, password, email));
        createUserResponse.then().statusCode(200);
        accessToken = getClearAccessToken(createUserResponse);
    }

    @After
    public void tearDown() {
        driver.quit();
        deleteUser(accessToken).then().statusCode(202);
    }

    @DisplayName("Проверка авторизации пользователя через главную страницу")
    @Test
    public void testSuccessfulUserLogin() {
        orderPage = new OrderPage(driver);
        orderPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();

        orderPage = new OrderPage(driver);
        assertEquals("Соберите бургер", orderPage.getHeaderText());
    }

    @DisplayName("Проверка авторизации пользователя по кнопке 'Личный кабинет'")
    @Test
    public void testUserLoginLK() {
        orderPage = new OrderPage(driver);
        orderPage.clickLoginAccountButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();

        orderPage = new OrderPage(driver);
        assertEquals("Соберите бургер", orderPage.getHeaderText());
    }

    @DisplayName("Проверка авторизации пользователя через форму регистрации")
    @Test
    public void testUserLoginFromRegisterForm() {
        driver.get(REGISTER_URL);
        registerPage = new RegisterPage(driver);
        registerPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();

        orderPage = new OrderPage(driver);
        assertEquals("Соберите бургер", orderPage.getHeaderText());
    }

    @DisplayName("Проверка авторизации пользователя через форму восстановления пароля")
    @Test
    public void testUserLoginFromRecoverPasswordForm() {
        driver.get(REGISTER_URL);
        recoveryPasswordPage = new RecoveryPasswordPage(driver);
        recoveryPasswordPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();

        orderPage = new OrderPage(driver);
        assertEquals("Соберите бургер", orderPage.getHeaderText());
    }
}
