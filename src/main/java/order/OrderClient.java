package order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class OrderClient {

    public Response createNoAutho(CreateOrder createOrder) {
        return given()
                .header("Content-type", "application/json")
                .body(createOrder)
                .post("/api/orders");
    }
    public Response createWithAutho(CreateOrder createOrder, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .body(createOrder)
                .post("/api/orders");
    }
    public Response createWithoutIngredients() {
        return given()
                .header("Content-type", "application/json")
                .post("/api/orders");
    }
    public Response getIngredients() {
        return given()
                .header("Content-type", "application/json")
                .get("/api/ingredients");
    }

    public Response getOrdersAutho(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .get("/api/orders");
    }

    public Response getOrdersNoAutho() {
        return given()
                .header("Content-type", "application/json")
                .get("/api/orders");
    }
}
