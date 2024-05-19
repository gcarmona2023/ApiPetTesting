package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class petsSteps {

    // Variable declaration
    private static RequestSpecification request;
    private Response response;
    private ValidatableResponse json;
    private int newPetId;
    private static int updatedPetId;

    /**
     * Configure the API URI for testing
     * @param uri
     */

    @Given("I set the API URI to {string}")
    public void i_set_the_api_uri_to(String uri) {
        request = given()
                .baseUri(uri)
                .contentType(ContentType.JSON);
    }

    /**
     * Send a GET request to find Pets according to different variables
     * @param path
     * @param status available, pending or sold
     */
    @When("I send a GET request to {string} with the status {string}")
    public void i_send_a_get_request_to_with_the_status(String path, String status) {
        response = request
                .basePath(path)
                .queryParam("status", status)
                .when()
                .get();
    }

    @Then("I should receive a list of pets with status {string}")
    public void i_should_receive_a_list_of_pets_with_status(String status) {
        json = response
                .then()
                .assertThat()
                .body("status",everyItem(equalTo(status)));
    }

    @Then("the response code should be {string}")
    public void the_response_code_should_be(String code) {
        response.then()
                .statusCode(Integer.parseInt(code));
    }

    @When("I send a GET request to {string} with the ID {string}")
    public void i_send_a_get_request_to_with_the_id(String path, String id) {
        response = request
                .basePath(path+id)
                .when()
                .get();
    }

    @Then("I should receive a pet with their ID {string}")
    public void i_should_receive_a_pet_with_their_id(String id) {
        String responseBody = response.getBody().asString();
        String contentType = response.getHeader("Content-type");

        if(contentType != null && contentType.contains("application/json")){
            try {
                JsonPath jsonPath = new JsonPath(responseBody);
                Integer actualId = jsonPath.getInt("id");
                Integer expectedId = Integer.parseInt(id);
                assertEquals(expectedId,actualId);

            } catch (Exception e){
                System.err.println("Error: "  + e.getMessage());
            }
        } else {
            System.err.println("Respuesta de Json: " + contentType);
            throw new AssertionError("La respuesta es: " + contentType);
        }
    }

    @When("I send a POST request to {string} with data from {string}")
    public void i_send_a_post_request_to_with_data_from(String path, String filePath) throws IOException {
        String jsonNewPet = new String(Files.readAllBytes(Paths.get(filePath)));

        response = request
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(jsonNewPet)
                .when()
                .post();

        newPetId = response.jsonPath().getInt("id");

        //Print response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response JSON: " + response.getBody().asString());
    }

    @Then("be able to get the new pet record from {string}")
    public void be_able_to_get_the_new_pet_record(String path) {
        response = request
                .basePath(path + newPetId)
                .when()
                .get();

        response.then().statusCode(200);
        System.out.println("Retrieved new Pet JSON: " + response.getBody().asString());
        response.then().assertThat().body("id", equalTo(newPetId));
    }

    @When("I send a PUT request to {string} with data from {string}")
    public void i_send_a_put_request_to_with_data_from(String path, String filePath) throws IOException {
        String jsonBody = new String(Files.readAllBytes(Paths.get(filePath)));

        response = request
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .put();

        updatedPetId = response.jsonPath().getInt("id");
    }

    @Then("be able to get the pet updated record from {string}")
    public void be_able_to_get_the_pet_updated_record(String path) {
        response = request
                .basePath(path + updatedPetId)
                .when()
                .get();

        response.then().statusCode(200);
        System.out.println("Retrieved updated Pet JSON: " + response.getBody().asString());
        response.then().assertThat().body("id", equalTo(updatedPetId));
    }

    @When("I send a DELETE request to {string} with the ID {string}")
    public void i_send_a_delete_request_to_with_the_id(String path, String id) throws IOException {
        id = String.valueOf(updatedPetId);

        response = request
                .basePath(path+id)
                .when()
                .delete();
    }

    @Then("I should receive a response with status code {string}")
    public void i_should_receive_a_response_with_status_code(String code) {
        int actualStatusCode = response.getStatusCode();
        int expected = Integer.parseInt(code);
        assertEquals(expected, actualStatusCode);
    }

    @Then("I should not be able to retrieve the deleted pet record from {string}")
    public void i_should_not_be_able_to_retrieve_the_deleted_pet_record(String path) {
        int id = updatedPetId;
        response = request
                .basePath(path + id)
                .when()
                .get();

        response.then().statusCode(404);
        System.out.println("Retrieved deleted Pet JSON: " + response.getBody().asString());
    }
}
