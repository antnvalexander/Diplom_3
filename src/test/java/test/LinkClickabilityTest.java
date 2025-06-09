package test;

import dto.UserDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page.AccountPage;
import page.LoginPage;
import page.OrderPage;
import utils.DataGenerator;

import static org.junit.Assert.assertEquals;
import static steps.CreateUserSteps.createUser;
import static steps.CreateUserSteps.getClearAccessToken;
import static steps.DeleteUserSteps.deleteUser;
import static utils.BaseHelper.BASE_URL;

public class LinkClickabilityTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private OrderPage orderPage;

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

    @DisplayName("Проверка перехода в личный кабинет")
    @Test
    public void testSwitchToPersonalAccount() {
        orderPage = new OrderPage(driver);
        orderPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();
        orderPage.clickLoginAccountButton();

        accountPage = new AccountPage(driver);

        assertEquals("Профиль", accountPage.getProfileFieldText());
        assertEquals(email, accountPage.getLoginFieldValue());
        assertEquals(name, accountPage.getNameFieldValue());
    }

    @DisplayName("Проверка перехода из ЛК в конструктор")
    @Test
    public void testSwitchToConstructor() {
        orderPage = new OrderPage(driver);
        orderPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();
        orderPage.clickLoginAccountButton();

        accountPage = new AccountPage(driver);
        accountPage.clickToConstructor();

        orderPage.createOrderButtonIsVisible();
        assertEquals("Соберите бургер", orderPage.getHeaderText());
    }

    @DisplayName("Проверка выхода из аккаунт по кнопке Выйти")
    @Test
    public void testSuccessfulUserLogoff() {
        orderPage = new OrderPage(driver);
        orderPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();
        orderPage.clickLoginAccountButton();

        accountPage = new AccountPage(driver);
        accountPage.clickLogoffButton();

        assertEquals("Вход", loginPage.getHeaderText());
        loginPage.checkLoginButton();
    }

    @DisplayName("Проверка перехода в раздел Соусы")
    @Test
    public void testSuccessfulSwitchToSauces() {
        orderPage = new OrderPage(driver);
        orderPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();

        orderPage.clickSaucesButton();
        orderPage.checkSaucesNameSectionField();
    }

    @DisplayName("Проверка переходов в раздел Начинки")
    @Test
    public void testSuccessfulSwitchToToppings() {
        orderPage = new OrderPage(driver);
        orderPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();

        orderPage.clickToppingsButton();
        orderPage.checkToppingsNameSectionField();
    }

    @DisplayName("Проверка переходов в раздел Булки")
    @Test
    public void testSuccessfulSwitch() {
        orderPage = new OrderPage(driver);
        orderPage.clickLoginButton();

        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        loginPage.clickLoginButton();

        //Сначала открываем раздел Соусы, далее переходим в раздел Булки.
        //Т.к. Булки открывается по умолчанию, нужно сначала открыть другой раздел
        orderPage.clickSaucesButton();

        orderPage.clickBunButton();
        orderPage.checkBunNameSectionField();
    }
}
