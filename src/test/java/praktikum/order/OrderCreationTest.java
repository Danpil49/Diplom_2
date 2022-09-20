package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static json_model.order.OrderClient.*;
import static json_model.order.OrderClient.createOrder;
import static json_model.user.UserClient.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCreationTest {
    @Before
    public void setUp(){
        setUpAPI();
        registerNewUser();
    }

    @Test
    @DisplayName("Создание заказа (/api/orders)")
    @Description("Создание корректного заказа с указанным ингредиентом 'Флюоресцентная булка R2-D3'")
    public void createCorrectOrderTest() {
        createOrder(true, defaultIngredientsToCreateOrder)
                .then()
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("order._id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Создание заказа не аутентифицированным пользователем (/api/orders)")
    @Description("Создание заказа не аутентифицированным пользователем с указанным ингридиентом 'Флюоресцентная булка R2-D3'")
    public void createOrderByUnauthorizedUserTest() {
        createOrder(false, defaultIngredientsToCreateOrder)
                .then()
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("order._id", nullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Создание заказа с не указанными ингредиентами (/api/orders)")
    @Description("Создание заказа с пустым телом запроса")
    public void createOrderWithoutIngredientsTest() {
        createOrderWithoutIngredients()
                .then()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание заказа с неправильным id ингредиента (/api/orders)")
    @Description("Создание заказа с неправильным id ингредиента = '228133754'")
    public void createOrderWithBadHashIngredientsTest() {
        createOrder(true, ingredientsWithBadHash)
                .then()
                .assertThat().body("html.head.title", equalTo("Error"))
                .and()
                .assertThat().body("html.body.pre", equalTo("Internal Server Error"))
                .and()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Получение списка заказов у аутентифицированного пользователя (/api/orders)")
    @Description("Получение списка заказов у пользователя: email = 'praktikum-student@autotest.ru', password = '12345678',name = 'praktikum-student'")
    public void getAuthorizedUserOrder() {
        getUserOrders(true)
                .then()
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Получение списка заказов у не аутентифицированного пользователя (/api/orders)")
    @Description("Получение списка заказов у пользователя с пустым токеном")
    public void getUnauthorizedUserOrder() {
        getUserOrders(false)
                .then()
                .assertThat().body("success", equalTo(false))
                .and()
                .assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void cleanUp(){
        delete();
    }
}
