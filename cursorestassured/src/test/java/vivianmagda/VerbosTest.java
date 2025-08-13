package vivianmagda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.beans.Encoder;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;

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
        // RestAssured.config = RestAssured.config()
        //     .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
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

    @Test
    public void deveAlterarUsuario(){
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body("{\"name\" : \"Usuário Alterado\", \"age\" : 80}")
        .when()
            .put("https://restapi.wcaquino.me/users/1")
        .then()
             .log().all()
            .statusCode(200)
            .body("id", is(1))
            .body("name", is("Usuário Alterado"))
            .body("age", is(80))
            .body("salary", is(1234.5678f))
        ;

    }

    @Test
    public void devoCustomizarURL(){
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body("{\"name\" : \"Usuário Alterado 2\", \"age\" : 80}")
            .pathParam("entidade", "users")
            .pathParam("userId", 2)
        .when()
            .put("https://restapi.wcaquino.me/{entidade}/{userId}")
        .then()
             .log().all()
            .statusCode(200)
            .body("id", is(2))
            .body("name", is("Usuário Alterado 2"))
            .body("age", is(80))
            .body("salary", is(2500))
        ;
    }

    @Test
    public void deveRemoverUsuario(){
        given()
            .log().all()
            .pathParam("entidade", "users")
            .pathParam("userId", 1)
        .when()
            .delete("https://restapi.wcaquino.me/{entidade}/{userId}")
        .then()
            .log().all()
            .statusCode(204)
        ;
    }

    @Test
    public void naoDeveRemoverUsuarioInexistente(){
        given()
            .log().all()
            .pathParam("entidade", "users")
            .pathParam("userId", 4)
        .when()
            .delete("https://restapi.wcaquino.me/{entidade}/{userId}")
        .then()
            .log().all()
            .statusCode(400)
            .body("error", is("Registro inexistente"))
        ;
    }



}


