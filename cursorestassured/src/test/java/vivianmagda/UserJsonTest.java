package vivianmagda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJsonTest {

    @Test
    public void deveVerificarPrimeiroNivel() {
        // Add test logic here
        given()
            //Pré condições
        .when()
            .get("https://restapi.wcaquino.me/users/1")
        .then()
            .statusCode(200)
            .body("id", is(1))
            .body("name", containsString("Silva"))
            .body("age", greaterThan(18))
        ;
    }

    @Test
    public void deveVerificarPrimeiroNivelOutrasFormas(){
        Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1");
        
        //path
        Assert.assertEquals(Integer.valueOf(1), response.path("id"));

        //jsonpath
        JsonPath jpath = new JsonPath(response.asString());
        Assert.assertEquals(1, jpath.getInt("id"));

        //from
        int id = JsonPath.from(response.asString()).getInt("id");
        Assert.assertEquals(1, id);
        
    }

    @Test
    public void deveVerificarSegundoNivel(){

        given()
            //Pré condições
        .when()
            .get("https://restapi.wcaquino.me/users/2")
        .then()
            .statusCode(200)
            .body("id", is(2))
            .body("name", containsString("Joaquina"))
            .body("endereco.rua", is("Rua dos bobos"))
        ;
        
    }


    @Test
    public void deveVerificarLista(){

        given()
            //Pré condições
        .when()
            .get("https://restapi.wcaquino.me/users/3")
        .then()
            .statusCode(200)
            .body("id", is(3))
            .body("name", containsString("Ana"))
            .body("filhos", hasSize(2))
            .body("filhos[0].name", is("Zezinho"))
            .body("filhos[1].name", is("Luizinho"))

        ;
        
    }
    

    @Test
    public void deveRetornarErroUsuarioInexistente() {
        
        given()
            //Pré condições
        .when()
            .get("https://restapi.wcaquino.me/users/4")
        .then()
            .statusCode(404)
            .body("error", is("Usuário inexistente"))
        ;

    }

    @Test
    public void deveVerificarListaNaRaiz() {
        
        given()
            //Pré condições
        .when()
            .get("https://restapi.wcaquino.me/users")
        .then()
            .statusCode(200)
            .body("", hasSize(3))
            .body("filhos.name", hasItem(Arrays.asList("Zezinho", "Luizinho")))
            .body("salary", hasItem(2500))
        ;
    }

     @Test
    public void deveFazerVerificacoesAvancadas() {
        
        given()
            //Pré condições
        .when()
            .get("https://restapi.wcaquino.me/users")
        .then()
            .statusCode(200)
            .body("", hasSize(3))
            .body("age.findAll{it <= 25}.size()", is(2))            
            .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))            
            .body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
            .body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)))

        ;
    }

}
