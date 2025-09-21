package vivianmagda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

public class FileTest{
    
    @Test
    public void deveObrigarEnvioDoArquivo() {
        given()
            .log().all()
        .when()
            .post("http://restapi.wcaquino.me/upload")
        .then()
            .log().all()
            .statusCode(404) //deveria ser 400
            .body("error", is("Arquivo não enviado"))
        ;
    }

    @Test
    public void deveFazerUploadDoArquivo(){
         given()
            .log().all()
            .multiPart("arquivo", new File("src/main/resources/USER TEXT FILE TEST.txt"))
        .when()
            .post("http://restapi.wcaquino.me/upload")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("USER TEXT FILE TEST.txt"))
        ;
    }

    @Test
    public void naoDeveFazerUploadDeArquivoGrande(){
         given()
            .log().all()
            .multiPart("arquivo", new File("src/main/resources/elden.png"))
        .when()
            .post("http://restapi.wcaquino.me/upload")
        .then()
            .log().all()
            .time(lessThan(5000L))
            .statusCode(413)
        ;
    }

     @Test
    public void deveBaicarArquivo() throws IOException{
         byte[] image = given()
            .log().all()
        .when()
            .get("http://restapi.wcaquino.me/download")
        .then()
            .log().all()
            .statusCode(200)
            .extract().asByteArray();
        ;
        File imagem = new File("src/main/resources/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();

        System.out.println(imagem.length());
        assertThat(imagem.length(), lessThan(100000L));
    }


}
