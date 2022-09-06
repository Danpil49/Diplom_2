package json_model.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.groovy.util.SystemUtil;

import java.awt.desktop.SystemSleepEvent;

import static io.restassured.RestAssured.given;
import static json_model.order.OrderGenerator.*;
import static json_model.user.UserClient.getToken;

public class OrderClient {
    public static final Ingredients ingredientsWithBadHash = setUpIngredientsWithBadHash();
    public static final Ingredients defaultIngredientsToCreateOrder = setUpIngredientsForCreateOrder();
    private static final String orderAPI = "/api/orders";

    @Step("Создание заказа")
    public static Response createOrder(Boolean isAuthorized, Ingredients ingredients) {
        String bearerToken = "";
        if (isAuthorized) {
            bearerToken = getToken();
        }
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(bearerToken)
                .body(ingredients)
                .post(orderAPI);
    }

    @Step("Создание заказа без указанных ингредиентов")
    public static Response createOrderWithoutIngredients() {
        String bearerToken = getToken();
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(bearerToken)
                .post(orderAPI);
    }

    @Step("Получение списка заказов пользователя")
    public static Response getUserOrders(Boolean isAuthorized) {
        String bearerToken = "";
        if (isAuthorized) {
            bearerToken = getToken();
        }
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(bearerToken)
                .get(orderAPI);
    }
}
