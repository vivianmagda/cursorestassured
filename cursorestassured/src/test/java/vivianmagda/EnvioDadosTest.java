package vivianmagda;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.http.ContentType;

public class EnvioDadosTest {

    @Test
    public void deveEnviarValorViaQuery(){
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/v2/users?format=xml")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.XML)
        ;
    }

    @Test
    public void deveEnviarValorViaQueryViaParam(){
        given()
            .log().all()
            .queryParam("format", "xml")
        .when()
            .get("https://restapi.wcaquino.me/v2/users")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.XML)
        ;
    }

    @Test
    public void deveEnviarValorViaQueryViaHeader(){
        given()
            .log().all()
            .accept(ContentType.XML)
        .when()
            .get("https://restapi.wcaquino.me/v2/users")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.XML)
        ;
    }

}
