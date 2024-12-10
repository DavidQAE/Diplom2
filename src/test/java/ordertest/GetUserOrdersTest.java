package ordertest;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.OrderClient;
import org.junit.Test;
import url.BaseUrl;
import user.CreateUser;
import user.UserBody;
import user.UserClient;


import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;

public class GetUserOrdersTest extends BaseUrl{
    String accessToken;
    OrderClient orderClient = new OrderClient();
    UserClient userClient = new UserClient();

    @Test
    public void getAuthorizedUserOrdersAndCheck() {
              registerAndLoginUser();
              Response response = getUserOrders();
              checkUserOrders(response);
              deleteUser();

    }

   @Step("Register and login User")
   public void registerAndLoginUser() {
       CreateUser createUser = new CreateUser()
               .withEmail("ArsDava222123@mail.ru").withPassword(random(10)).withName(random(14));

       userClient.create(createUser);
       Response loginResponse = userClient.loginCreds(createUser);
       accessToken = loginResponse.as(UserBody.class).getAccessToken().substring(7);
   }



    @Step("Get user orders")
    public Response getUserOrders() {
       Response response = orderClient.getOrdersAutho(accessToken);
       return response;
   }

   @Step("Check response orders")
    public void checkUserOrders(Response response) {
        response.then().statusCode(200).and().assertThat().body("orders", notNullValue());
   }
   @Step("delete user")
   public void deleteUser() {
       userClient.delete(accessToken);
   }


    @DisplayName("Get orders of unauthorized User and check")
    @Test
    public void getUnauthorizedUserOrdersAndCheck() {
        Response response = getNoUserOrders();
        checkNoUserOrders(response);

    }


    @Step("Get unauthorized User orders")
    public Response getNoUserOrders() {
        Response response = orderClient.getOrdersNoAutho();
        return response;
    }

    @Step("Check response orders of unauthorized User")
    public void checkNoUserOrders(Response response) {
        response.then().statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));
    }


}
