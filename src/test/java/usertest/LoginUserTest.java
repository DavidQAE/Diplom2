package usertest;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import url.BaseUrl;
import user.CreateUser;
import user.LoginUser;
import user.UserBody;
import user.UserClient;


import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;


public class LoginUserTest extends BaseUrl {

    UserClient userClient = new UserClient();
    String accessToken;



    @DisplayName("Login with existing user and check status code and body")
    @Test
    public void LoginExistingUser() {
        Response loginResponse = createAndLoginUser();
        checkUserLogin(loginResponse);
        deleteUser(loginResponse);
    }

    @Step("Create user and login")
    public Response createAndLoginUser() {
        CreateUser createUser = new CreateUser()
                .withEmail("ArsDava222123@mail.ru").withPassword(random(10)).withName(random(14));

        userClient.create(createUser);
        Response loginResponse = userClient.loginCreds(createUser);
        return loginResponse;
    }

    @Step("Check status code and body of the existing user")
    public void checkUserLogin(Response loginResponse) {
        loginResponse.then().statusCode(200).and().
                assertThat().body("accessToken", notNullValue());
    }


    @Step("Delete user")
    public void deleteUser(Response loginResponse) {
        accessToken = loginResponse.as(UserBody.class).getAccessToken().substring(7);
        userClient.delete(accessToken);
    }


    @DisplayName("Create user with incorrect password and email")
    @Test
    public void userWithIncorrectLoginAndPassword() {
        Response loginResponse = createAndLoginIncorrectUser();
        checkIncorrectUser(loginResponse);

    }

    @Step("Create user and login with incorrect email and password")
    public Response createAndLoginIncorrectUser() {
        CreateUser createUser = new CreateUser()
                .withEmail("ArsDava321@mail.ru").withPassword(random(10)).withName(random(14));

        userClient.create(createUser);
        LoginUser loginUser = new LoginUser()
                .withEmail("Arsdav313@mail.ru").withPassword(random(10));
        Response loginResponse = userClient.login(loginUser);
        return loginResponse;
    }
    @Step("Check incorrect user status code")
    public void checkIncorrectUser(Response loginResponse) {
        loginResponse.then().statusCode(401).and().assertThat().body("message", equalTo("email or password are incorrect"));
    }


}