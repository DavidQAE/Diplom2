package order;
import url.ApiKeys;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class OrderClient extends ApiKeys{

    public Response createNoAutho(CreateOrder createOrder) {
        return given()
                .header("Content-type", "application/json")
                .body(createOrder)
                .post(orderKey);
    }
    public Response createWithAutho(CreateOrder createOrder, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .body(createOrder)
                .post(orderKey);
    }
    public Response createWithoutIngredients() {
        return given()
                .header("Content-type", "application/json")
                .post(orderKey);
    }
    public Response getIngredients() {
        return given()
                .header("Content-type", "application/json")
                .get(ingredientsKey);
    }

    public Response getOrdersAutho(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .get(orderKey);
    }

    public Response getOrdersNoAutho() {
        return given()
                .header("Content-type", "application/json")
                .get(orderKey);
    }
}
