package vivianmagda;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.xml.element.Node;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserXMLTest { 
    
    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;
    

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://restapi.wcaquino.me";
        //RestAssured.port = 80;
        //RestAssured.basePath = "/v2";

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.log(LogDetail.ALL);
        reqSpec = reqBuilder.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectStatusCode(200);
        resSpec = resBuilder.build();

        RestAssured.requestSpecification = reqSpec;
        RestAssured.responseSpecification = resSpec;
        

    }

    @Test
    public void devoTrabalharComXML(){ 

        
        
        given()
            .log().all()
        .when()
            .get("/users")
        .then()
            //.statusCode(200)

            // .rootPath("user")
            //     .body("name", is("Ana Julia"))
            //     .body("@id", is("3"))

            // .rootPath("user.filhos")
            //     .body("name.size()", is(2))

            // .detachRootPath("filhos")
            //     .body("filhos.name[0]", is("Zezinho"))
       ;
    }

     @Test
    public void devoFazerPesquisasAvancadascomXML(){
        
        given()
        .when()
            .get("/usersXML")
        .then()
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
            .get("/usersXML")
        .then()
            .statusCode(200)
            .extract().path("users.user.name.findAll{it.toString().contains('n')}")
        ;
        assertEquals(2, nomes.size());
     

    }


    @Test
    public void devoFazerPesquisasAvancadascomXPath(){
        given()
        .when()
            .get("/usersXML")
        .then()
            .body(hasXPath("count(/users/user)", is("3")))
            .body(hasXPath("/users/user[@id = '1']"))
            .body(hasXPath("//user[@id = '2']"))            
            .body(hasXPath("//name[text() = 'Luizinho']/../../name", is("Ana Julia")))
            .body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf(containsStringIgnoringCase("Zezinho"), containsStringIgnoringCase("Luizinho"))))            
            .body(hasXPath("count(/users/user/name[contains(., 'n')])", is("2")))   
            .body(hasXPath("//user[age < 24]/name", is("Ana Julia")))     
            .body(hasXPath("//user[age > 20 and age < 30]/name", is("Maria Joaquina")))    
        ;

    }
    

}
