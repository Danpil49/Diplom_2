package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static json_model.user.UserClient.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest {
    @Before
    public void setUp(){
        setUpAPI();
        registerNewUser();
    }

    @Test
    @DisplayName("Логин пользователем с корректными данными (/api/auth/login)")
    @Description("Данные пользователя для логина:  email = 'praktikum-student@autotest.ru', password = '12345678'")
    public void correctUserLoginTest() {
        login()
                .then()
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Логин пользователем с не указанным полем email (/api/auth/login)")
    @Description("Данные пользователя для логина: email = '', password = '12345678'")
    public void wrongUserLoginTest() {
        wrongUserLogin()
                .then()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void cleanUp() {
        delete();
    }
}
