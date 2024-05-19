package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class inventorySteps {

    // Variable declaration
    private Response response;
    private static RequestSpecification request;

    @When("I send a GET request to {string} to obtain the inventory")
    public void i_send_a_get_request_to_to_obtain_the_inventory(String path) {
        request = given();

        //
        response = request
                .basePath(path)
                .when()
                .get();
        System.out.println(request);

        System.out.println("Retrieved Inventory JSON: " + response.getBody().asString());
    }

    @Then("the response code must be {string}")
    public void the_response_code_must_be(String code) {
        response.then()
                .statusCode(Integer.parseInt(code));
    }

    @When("I send a POST request to {string} with a new order in {string}")
    public void i_send_a_post_request_to_with_a_new_order_in(String path, String filePath) throws IOException {
        request = given();
        String jsonBody = new String(Files.readAllBytes(Paths.get(filePath)));

        response = request
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post();

        //Print response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response JSON: " + response.getBody().asString());
    }

    @When("I send a GET request to {string} to obtain the purchase order by {string}")
    public void i_send_a_get_request_to_to_obtain_the_purchase_order_by(String path, String id) {
        request = given();

        response = request
                .basePath(path+id)
                .when()
                .get();
    }

    @Then("the response code is {string}")
    public void the_response_code_is(String code) {
        response.then()
                .statusCode(Integer.parseInt(code));
    }

    @When("I send a DELETE request to {string} to remove a purchase order by {string}")
    public void i_send_a_delete_request_to_to_remove_a_purchase_order_by(String path, String id) {
        request = given();

        response = request
                .basePath(path+id)
                .when()
                .delete();
    }

}
