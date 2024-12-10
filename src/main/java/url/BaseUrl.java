package url;
import io.restassured.RestAssured;
import org.junit.Before;

public class BaseUrl {

@Before
    public void setUp() {
    RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
}

}
