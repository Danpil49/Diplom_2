package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static json_model.user.UserClient.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTest {

    Boolean emailChanged = false;
    Boolean needCleanUp = true;
    String newUserEmail = "praktikum_tests" +
            ((new Random()).nextInt(100_000) + 10_000) +
            "@yandex.ru";
    String newUserName = "Ivan";

    @Before
    public void setUp() {
        setUpAPI();
        registerNewUser();
    }

    @Test
    @DisplayName("Изменение имени авторизированного пользователя")
    @Description("Попытка изменить имя для авторизированного пользователя на 'Ivan'")
    public void changeAuthorizedUserNameTest() {
        changeName(newUserName, true)
                .then()
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("user.name", equalTo(newUserName))
                .and()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Изменение email'а авторизированного пользователя")
    @Description("Попытка изменить email для авторизированного пользователя на 'praktikum_tests' + случайное число + '@yandex.ru'")
    public void changeAuthorizedUserEmailTest() {
        changeEmail(newUserEmail, true)
                .then()
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("user.email", equalTo(newUserEmail))
                .and()
                .statusCode(SC_OK);
        emailChanged = true;
    }

    @Test
    @DisplayName("Изменение имени не авторизированного пользователя")
    @Description("Попытка изменить имя для не авторизированного пользователя на 'Ivan'")
    public void changeUnauthorizedUserNameTest() {
        changeName(newUserName, false)
                .then()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
        needCleanUp = false;
    }

    @Test
    @DisplayName("Изменение email'а не авторизированного пользователя")
    @Description("Попытка изменить email для не авторизированного пользователя на 'praktikum_tests' + случайное число + '@yandex.ru'")
    public void changeUnauthorizedUserEmailTest() {
        changeEmail(newUserEmail, false)
                .then()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
        needCleanUp = false;
    }

    @After
    public void cleanUp() {
        if (needCleanUp) {
            if (emailChanged) {
                delete(newUserEmail);
            } else {
                delete();
            }
        }
    }
}
