package org.matheuscordeiro.socialapi.rest;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.matheuscordeiro.socialapi.rest.dto.CreateUserRequest;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserResourceTest {
    @TestHTTPResource("/users")
    URL apiURL;


    @Test
    @DisplayName("should create an user successfully")
    @Order(1)
    void shouldCreateUser() {
        var user = new CreateUserRequest("Matheus", 27);
        var response =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                        .when()
                        .post(apiURL)
                        .then()
                        .extract().response();
        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));
    }
}
