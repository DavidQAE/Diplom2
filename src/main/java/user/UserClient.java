package user;
import url.ApiKeys;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class UserClient extends ApiKeys{

public Response create (CreateUser createUser) {
    return given()
            .header("Content-type", "application/json")
            .body(createUser)
            .post(registerKey);
}

public Response delete(String accessToken) {
    return given()
            .header("Content-type", "application/json")
            .auth().oauth2(accessToken)
            .delete(userKey);
}


public Response login(LoginUser loginUser) {
    return given()
            .header("Content-type", "application/json")
            .body(loginUser)
            .post(loginKey);
}

    public Response loginCreds(CreateUser createUser) {
        return given()
                .header("Content-type", "application/json")
                .body(UserCreds.credsFromUser(createUser))
                .post(loginKey);
    }


    public Response change(String accessToken, ChangeUserInfo changeUserInfo) {
        return given()
                .header("Content-type", "application/json")
                .body(changeUserInfo)
                .auth().oauth2(accessToken)
                .patch(userKey);
    }

    public Response change401( ChangeUserInfo changeUserInfo) {
        return given()
                .header("Content-type", "application/json")
                .body(changeUserInfo)
                .patch(userKey);
    }
}
