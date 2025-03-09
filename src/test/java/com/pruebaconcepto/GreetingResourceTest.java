package com.pruebaconcepto;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
class GreetingResourceTest {
    

    @Test
    public void testProcesarEntrada_BadRequest() {
        String requestBody = """
                {
                  "canalA": "ID",
                  "canalB": "TRANSACCION",
                  "canalC": "CAMPO3",
                  "canalD": "CAMPO4",
                  "canalE": "CAMPO5",
                  "convenio": "templateX"
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/procesar")
        .then()
            .statusCode(400)
            .body("error", equalTo("Convenio no válido"));
    }

}