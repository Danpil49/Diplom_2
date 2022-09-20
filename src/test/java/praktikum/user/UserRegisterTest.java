package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static json_model.user.UserClient.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class UserRegisterTest {
    Boolean needCleanUp;
    @Before
    public void setUp() {
        setUpAPI();
        needCleanUp = true;
    }

    @Test
    @DisplayName("Проверка создания пользователя (/api/auth/register)")
    @Description("Проверка создания пользователя с полями: email = 'praktikum-student@autotest.ru', password = '12345678',name = 'praktikum-student'")
    public void registerCorrectUserTest() {
        registerNewUser()
                .then()
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка создания пользователя, который уже был создан ранее (/api/auth/register)")
    @Description("Проверка повторного создания пользователя с полями: email = 'praktikum-student@autotest.ru', password = '12345678',name = 'praktikum-student'")
    public void registerSameUserTest() {
        registerNewUser();
        registerNewUser()
                .then()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Проверка создания пользователя без указанного email (/api/auth/register)")
    @Description("Проверка создания пользователя с полями: email = '', password = '12345678',name = 'praktikum-student'")
    public void registerUserWithoutEmailTest() {
        registerUserWithoutEmail()
                .then()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);

        needCleanUp = false;
    }

    @After
    public void cleanUp() {
        if (needCleanUp) {
            delete();
        }
    }
}
