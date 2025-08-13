package vivianmagda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;

public class SerializacaoTest {

    @Test
    public void deveSalvarUsuarioUsandoMAP(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "Usuario via map");
        params.put("age", 25);

        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .pathParam("entidade", "users")
        .when()
            .post("https://restapi.wcaquino.me/{entidade}")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("name", is("Usuario via map"))
            .body("age", is(25 ))
        ;
    }

}
