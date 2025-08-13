package vivianmagda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.beans.Encoder;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;

public class VerbosTest {

    @Test
    public void deveSalvarUsuario(){

        given()
            .log().all()
            .contentType("application/json")
            .body("{\"name\" : \"Jose\", \"age\" : \"50\"}")
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", is(notNullValue()))            
            .body("name", is("Jose"))            
            .body("age", is("50"))
        ;
    }

    @Test
    public void naoDeveSalvarUsuarioSemNome(){
        RestAssured.config = RestAssured.config()
            .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        given()
            .log().all()
            .contentType("application/json")
            .body("{\"age\" : \"50\"}")
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
             .log().all()
            .statusCode(400)
            .body("id", is(nullValue()))
            .body("error", is("Name é um atributo obrigatório"))
        ;

    }

}


