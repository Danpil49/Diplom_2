package json_model.user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static json_model.user.UserGenerator.getDefaultUser;
import static json_model.user.UserGenerator.getUserWithoutEmail;

public class UserClient {
    private static final User defaultUser = getDefaultUser();
    private static final User userWithoutEmail = getUserWithoutEmail();

    private static final String userRegisterAPI = "/api/auth/register";
    private static final String userLoginAPI = "/api/auth/login";
    private static final String userDeleteAPI = "/api/auth/user";
    private static final String userChangeInfoAPI = "/api/auth/user";

    public static void setUpAPI() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Step("Регистрация нового пользователя с полями: email = 'praktikum-student@autotest.ru', password = '12345678',name = 'praktikum-student'")
    public static Response registerNewUser() {
        return given()
                .header("Content-type", "application/json")
                .body(defaultUser)
                .post(userRegisterAPI);
    }

    @Step("Регистрация нового пользователя с полями: email = '', password = '12345678',name = 'praktikum-student'")
    public static Response registerUserWithoutEmail() {
        return given()
                .header("Content-type", "application/json")
                .body(userWithoutEmail)
                .post(userRegisterAPI);
    }

    @Step("Логин пользователем с полями: email = 'praktikum-student@autotest.ru', password = '12345678'")
    public static Response login() {
        return given()
                .header("Content-type", "application/json")
                .body(defaultUser)
                .post(userLoginAPI);
    }
    @Step("Логин пользователем с полями: email = '', password = '12345678'")
    public static Response wrongUserLogin() {
        return given()
                .header("Content-type", "application/json")
                .body(userWithoutEmail)
                .post(userLoginAPI);
    }

    @Step("Получение актуального токена пользователя с полями: email = 'praktikum-student@autotest.ru', password = '12345678'")
    public static String getToken() {
        return login()
                .then()
                .extract()
                .body()
                .path("accessToken")
                .toString().replaceAll("Bearer ", "");
    }

    @Step("Удаление пользователя с полями: email = 'praktikum-student@autotest.ru', password = '12345678'")
    public static void delete() {
        String bearerToken = getToken();
        given()
                .header("Content-type", "application/json")
                .auth().oauth2(bearerToken)
                .delete(userDeleteAPI);
    }

    @Step("Изменение имени пользователя на указанное")
    public static Response changeName(String name, Boolean isAuthorized) {
        String bearerToken = "";

        if (isAuthorized){
            bearerToken = getToken();
        }
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(bearerToken)
                .body("{\"name\": \"" + name + "\"}")
                .patch(userChangeInfoAPI);
    }

    @Step("Изменение email пользователя на указанное")
    public static Response changeEmail(String email, Boolean isAuthorized) {
        String bearerToken = "";

        if (isAuthorized){
            bearerToken = getToken();
        }
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(bearerToken)
                .body("{\"email\": \"" + email + "\"}")
                .patch(userChangeInfoAPI);
    }

    @Step("Удаление пользователя с измененным полем email и password = '12345678'")
    public static void delete(String newEmail) {
        String bearerToken = getToken(newEmail);
        given()
                .header("Content-type", "application/json")
                .auth().oauth2(bearerToken)
                .delete(userDeleteAPI);
    }

    @Step("Получение актуального токена пользователя с измененным полем email и password = '12345678'")
    public static String getToken(String newEmail) {
        return login(newEmail)
                .then()
                .extract()
                .body()
                .path("accessToken")
                .toString().replaceAll("Bearer ", "");
    }

    @Step("Логин пользователем с измененным полем email и password = '12345678'")
    public static Response login(String newEmail) {
        defaultUser.setEmail(newEmail);
        return given()
                .header("Content-type", "application/json")
                .body(defaultUser)
                .post(userLoginAPI);
    }

}
