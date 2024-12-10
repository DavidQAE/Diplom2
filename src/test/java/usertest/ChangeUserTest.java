package usertest;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import url.BaseUrl;
import user.*;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserTest extends BaseUrl {

String accessToken;
UserClient userClient = new UserClient();


@DisplayName("Change all User Fields and check body")
@Test
    public void changeAllFieldsOfAuthorizedUser() {
     Response loginResponse = createUserAndLogin();
     Response changeResponse = changeAuthorizedUser(loginResponse);
     checkChangedUser(changeResponse);
     deleteChangedUser();
     System.out.println(changeResponse.body().asString());
}
@Step("Create and login user")
    public Response createUserAndLogin() {
    CreateUser createUser = new CreateUser()
            .withName(random(10)).withEmail("TestingBurgersDavid07@mail.ru").withPassword(random(14));

    userClient.create(createUser);
   Response loginResponse = userClient.loginCreds(createUser);
    return  loginResponse;
}
@Step("Change user info")
    public Response changeAuthorizedUser(Response loginResponse) {
    ChangeUserInfo changeUserInfo = new ChangeUserInfo()
            .withEmail("qadavidtest1723@mail.ru").withName(random(12)).withPassword(random(15));
    accessToken = loginResponse.as(UserBody.class).getAccessToken().substring(7);
   Response changeResponse = userClient.change(accessToken, changeUserInfo);
    return changeResponse;
}

@Step("Verify changes")
    public void checkChangedUser(Response changeResponse) {
    changeResponse.then().statusCode(200).and().assertThat().body("user.email", equalTo("qadavidtest1723@mail.ru"));
}

@Step("Delete user")
    public void deleteChangedUser() {
    userClient.delete(accessToken);
}

@DisplayName("Change all  Fields of unauthorized user and check body")
@Test
    public void changeUserWithoutAuthorization() {
    Response changeResponse = changeUnauthorizedUser();
    checkUnauthorizedUser(changeResponse);
    System.out.println(changeResponse.body().asString());
}

@Step("Change unauthorized User info")
    public Response changeUnauthorizedUser() {
    ChangeUserInfo changeUserInfo = new ChangeUserInfo()
            .withName(random(11)).withPassword(random(14)).withEmail("testing123123123@Mail.ru");

    Response changeResponse = userClient.change401(changeUserInfo);
    return changeResponse;
}

@Step("Check status code and body message of the response")
    public void checkUnauthorizedUser(Response changeResponse) {
    changeResponse.then().statusCode(401).
            and().assertThat().body("message", equalTo("You should be authorised"));
}
}
