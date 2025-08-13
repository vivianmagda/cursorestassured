package vivianmagda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Assert;
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

    @Test
    public void deveSalvarUsuarioUsandoObjeto(){
        User user = new User("Adicionado com objeto", 35);

        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(user)
            .pathParam("entidade", "users")
        .when()
            .post("https://restapi.wcaquino.me/{entidade}")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("name", is("Adicionado com objeto"))
            .body("age", is(35 ))
        ;
    }

    @Test
    public void deveDeserializarObjetoAoSalvarUsuario(){
        User user = new User("Adicionado com deserializacao", 35);

        User usuarioInserido = given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(user)
            .pathParam("entidade", "users")
        .when()
            .post("https://restapi.wcaquino.me/{entidade}")
        .then()
            .log().all()
            .statusCode(201)
            .extract().body().as(User.class)
        ;

        System.out.println(usuarioInserido);
        assertThat(usuarioInserido.getId(),notNullValue());
        assertEquals("Adicionado com deserializacao", usuarioInserido.getName());
        assertThat(usuarioInserido.getAge(), is(35));

    }


    @Test
    public void deveSalvarUsuarioViaXMLUsandoObjeto(){
        User user = new User("Adicionado com objeto", 35);

        given()
            .log().all()
            .contentType(ContentType.XML)
            .body(user)
            .pathParam("entidade", "usersXML")
        .when()
            .post("https://restapi.wcaquino.me/{entidade}")
        .then()
            .log().all()
            .statusCode(201)
            .body("user.@id", is(notNullValue()))
            .body("user.name", is("Adicionado com objeto"))
            .body("user.age", is("35" ))
        ;
    }

    @Test
    public void deveDeserializarXMLAoSalvarUsuario(){
        User user = new User("Adicionado com deserializacao", 35);

        User usuarioInserido = given()
            .log().all()
            .contentType(ContentType.XML)
            .body(user)
            .pathParam("entidade", "usersXML")
        .when()
            .post("https://restapi.wcaquino.me/{entidade}")
        .then()
            .log().all()
            .statusCode(201)
            .extract().body().as(User.class)
        ;

        
        System.out.println(usuarioInserido);
        assertThat(usuarioInserido.getId(),notNullValue());
        assertEquals("Adicionado com deserializacao", usuarioInserido.getName());
        assertThat(usuarioInserido.getAge(), is(35));
    }

}
