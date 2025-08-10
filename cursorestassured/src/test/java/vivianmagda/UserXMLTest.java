package vivianmagda;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class UserXMLTest {

    @Test
    public void devoTrabalharComXML(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML/3")
        .then()
            .statusCode(200)
            .body("user.name", is("Ana Julia"))
            .body("user.@id", is("3"))
        ;
    }

}
