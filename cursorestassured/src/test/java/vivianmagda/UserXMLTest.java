package vivianmagda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import io.restassured.path.xml.element.Node;

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

     @Test
    public void devoFazerPesquisasAvancadascomXML(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML/")
        .then()
            .statusCode(200)
            .rootPath("users.user")
                .body("size()", is(3))
                .body("findAll{it.age.toInteger() <= 25}.size()", is(2))
                .body("salary.find{it != null}.toDouble()", is(1234.5678d))
                .body("name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"))
        ;
    }

    @Test
    public void devoFazerPesquisasAvancadascomXMLeJAVA(){
        ArrayList<Node> nomes = given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML/")
        .then()
            .statusCode(200)
            .extract().path("users.user.name.findAll{it.toString().contains('n')}")
        ;
        assertEquals(2, nomes.size());
        assertTrue("Maria Joaquina".equalsIgnoreCase(nomes.get(0).toString()));
        assertTrue("Ana Julia".equalsIgnoreCase(nomes.get(1).toString()));

    }

}
