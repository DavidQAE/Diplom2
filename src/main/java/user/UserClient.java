package user;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class UserClient {

public Response create(CreateUser createUser) {
    return given()
            .header("Content-type", "application/json")
            .body(createUser)
            .post("/api/auth/register");
}

public Response delete(String accessToken) {
    return given()
            .header("Content-type", "application/json")
            .auth().oauth2(accessToken)
            .delete("/api/auth/user");
}


public Response login(LoginUser loginUser) {
    return given()
            .header("Content-type", "application/json")
            .body(loginUser)
            .post("/api/auth/login");
}

    public Response loginCreds(CreateUser createUser) {
        return given()
                .header("Content-type", "application/json")
                .body(UserCreds.credsFromUser(createUser))
                .post("/api/auth/login");
    }


    public Response change(String accessToken, ChangeUserInfo changeUserInfo) {
        return given()
                .header("Content-type", "application/json")
                .body(changeUserInfo)
                .auth().oauth2(accessToken)
                .patch("/api/auth/user");
    }

    public Response change401( ChangeUserInfo changeUserInfo) {
        return given()
                .header("Content-type", "application/json")
                .body(changeUserInfo)
                .patch("/api/auth/user");
    }
}
