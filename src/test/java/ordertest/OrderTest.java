package ordertest;


import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.CreateOrder;
import order.Data;
import order.OrderClient;
import order.User;
import org.junit.Test;
import url.BaseUrl;
import user.CreateUser;
import user.UserBody;
import user.UserClient;


import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;

public class OrderTest extends BaseUrl {

    OrderClient orderClient = new OrderClient();
    List<Data> ingredientHash;
    UserClient userClient = new UserClient();
    String accessToken;


@DisplayName("Order without authorization and with ingredients and check status code and body")
    @Test
        public void orderWithoutAuthorization() {
        Response orderResponse = getIngredientsAndOrderWithoutAuthorization();
        checkOrderWithoutAuthorization(orderResponse);
    }



    @Step("Order without authorization")
    public Response getIngredientsAndOrderWithoutAuthorization() {
        Response response = orderClient.getIngredients();
        ingredientHash = response.as(User.class).getData();
        CreateOrder createOrder = new CreateOrder().withIngredients(ingredientHash);
        Response orderResponse = orderClient.createNoAutho(createOrder);
        return orderResponse;
    }

   @Step("Check status code and body")
    public void checkOrderWithoutAuthorization(Response orderResponse) {
        orderResponse.then().statusCode(200).
                and().assertThat().body("order.number", notNullValue());
   }

   @DisplayName("Order with authorization and check status code and body")
   @Test
   public void orderWithAuthorization() {
          registerAndLogin();
          Response orderResponse = getIngredientsAndOrderWithAuthorization();
          checkOrderWithAuthorization(orderResponse);
          deleteUser();
   }


   @Step("Register, login and get accessToken")
   public void registerAndLogin() {
       CreateUser createUser = new CreateUser()
               .withEmail("ArsDava222123@mail.ru").withPassword(random(10)).withName(random(14));

       userClient.create(createUser);
       Response loginResponse = userClient.loginCreds(createUser);
       accessToken = loginResponse.as(UserBody.class).getAccessToken().substring(7);
   }
   @Step("Order with authorization")
   public Response getIngredientsAndOrderWithAuthorization() {
       Response response = orderClient.getIngredients();
       ingredientHash = response.as(User.class).getData();
       CreateOrder createOrder = new CreateOrder().withIngredients(ingredientHash);
       Response orderResponse = orderClient.createWithAutho(createOrder, accessToken);
       return orderResponse;
   }

   @Step("Check order with authorization")
   public void checkOrderWithAuthorization(Response orderResponse) {
       orderResponse.then().statusCode(200).
               and().assertThat().body("order.number", notNullValue());
   }

   @Step("Delete user")
    public void deleteUser() {
    userClient.delete(accessToken);
   }
   @DisplayName("Order without ingredients and check status code")
    @Test
    public void orderWithoutAuthorizationAndIngredients() {
        Response orderResponse = orderWithoutIngredients();
        checkOrderWithoutIngredients(orderResponse);
    }



    @Step("Order without authorization")
    public Response orderWithoutIngredients() {
        Response orderResponse = orderClient.createWithoutIngredients();
        return orderResponse;
    }

    @Step("Check status code and body")
    public void checkOrderWithoutIngredients(Response orderResponse) {
        orderResponse.then().statusCode(400).
                and().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }
    @DisplayName("Order with wrong ingredient hash and check status code")
    @Test
    public void orderWithWrongHash() {
        Response orderResponse = getIngredientsAndOrderWithWrongHash();
        checkOrderWithWrongHash(orderResponse);
    }



    @Step("Order without authorization")
    public Response getIngredientsAndOrderWithWrongHash() {
        List<Data> wrongHash = new ArrayList<>();
        Data data = new Data();
        wrongHash.add(data);
        CreateOrder createOrder = new CreateOrder().withIngredients(wrongHash);
        Response orderResponse = orderClient.createNoAutho(createOrder);
        return orderResponse;
    }

    @Step("Check status code and body")
    public void checkOrderWithWrongHash(Response orderResponse) {
        orderResponse.then().statusCode(500);

    }
}


