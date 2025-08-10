package vivianmagda;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItem;
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

            .rootPath("user")
                .body("name", is("Ana Julia"))
                .body("@id", is("3"))

            .rootPath("user.filhos")
                .body("name.size()", is(2))

            .detachRootPath("filhos")
                .body("filhos.name[0]", is("Zezinho"))

            .appendRootPath("filhos")
                .body("name",   hasItem("Luizinho"))
        ;
    }

}
