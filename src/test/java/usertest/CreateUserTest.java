package usertest;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import url.BaseUrl;
import user.CreateUser;
import user.UserBody;
import user.UserClient;


import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseUrl {


UserClient userClient = new UserClient();
private String accessToken;



@DisplayName("Create user and check creation")
@Test
    public void uniqueUser() {
      Response response = createUniqueUser();
      checkCreatedUser(response);
      deleteUniqueUser(response);

}

@Step("Create user")
    public Response createUniqueUser() {
    CreateUser createUser = new CreateUser()
            .withEmail("DavidTest999@mail.ru").withName(random(10)).withPassword(random(14));

    Response response = userClient.create(createUser);


    return response;

}
@Step("Check the status code and body of the created user")
    public void checkCreatedUser(Response response) {


        response.then().statusCode(200).
                and().assertThat().body("accessToken", notNullValue());
    }

@Step("Delete user")
public void deleteUniqueUser(Response response) {
    accessToken = response.as(UserBody.class).getAccessToken().substring(7);
    userClient.delete(accessToken);
}

@DisplayName("Create the same user and check status code and body")
@Test
    public void theSameUser() {
        Response response = createIdenticalUser();
        checkIdenticalUser(response);

}
@Step("Create the previously created user")
    public Response createIdenticalUser() {
        CreateUser createUser = new CreateUser()
                .withEmail("test-data@yandex.ru").withPassword(random(10)).withName(random(14));

        Response response = userClient.create(createUser);
        return response;
}

@Step("Check status code and body of the identical user")
    public void checkIdenticalUser(Response response) {
        response.then().statusCode(403).and().assertThat().body("message", equalTo("User already exists"));
}

@DisplayName("Create user without Name and Check status code and body")
@Test
public void userWithoutName() {
   Response response = createUser();
   checkUserWithoutName(response);
}

@Step("create user without name")
    public Response createUser() {
    CreateUser createUser = new CreateUser()
            .withPassword(random(2323)).withEmail("ArsDava3232@mail.ru");
    Response response = userClient.create(createUser);
    return response;
}

@Step("Check user without name status code")
    public void checkUserWithoutName(Response response) {
    response.then().statusCode(403).and().
            assertThat().body("message", equalTo("Email, password and name are required fields"));
}

}
