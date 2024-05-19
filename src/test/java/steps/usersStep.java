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
import static org.hamcrest.Matchers.equalTo;

public class usersStep {

    // Variable declaration
    private Response response;
    private static RequestSpecification request;
    private static String userName;
    private static String userPass;
    private static String updatedUser;


    @When("I send a POST request to {string} to create a new user {string}")
    public void i_send_a_post_request_to_to_create_a_new_user(String path, String filePath) throws IOException {
        request = given();

        //
        String jsonBody = new String(Files.readAllBytes(Paths.get(filePath)));

        response = request
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post();

        userName = response.jsonPath().getString("username");
        userPass = response.jsonPath().getString("password");
    }
    @Then("I get the response code {string}")
    public void i_get_the_response_code(String code) {
        response.then()
                .statusCode(Integer.parseInt(code));

        //Print response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response JSON: " + response.getBody().asString());
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String path) {
        response = request
                .basePath(path)
                .queryParam("login", userName, userPass)
                .when()
                .get();

    }
    @Then("I should receive the user session")
    public void i_should_receive_the_user_session() {
        //Print response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response user session: " + response.getBody().asString());
    }

    @When("I send a GET request to {string} with the user {string}")
    public void i_send_a_get_request_to_with_the_user(String path, String getUser) {
        getUser = userName;
        response = request
                .basePath(path+getUser)
                .when()
                .get();
    }

    @When("I send a PUT request to {string} with updated data from {string}")
    public void i_send_a_put_request_to_with_updated_data_from(String path, String filePath) throws IOException {
        String jsonBody = new String(Files.readAllBytes(Paths.get(filePath)));

        response = request
                .basePath(path+userName)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .put();

        updatedUser = response.jsonPath().getString("username");
    }
    @Then("be able to get the user updated record from {string}")
    public void be_able_to_get_the_user_updated_record_from(String path) {

        response = request
                .basePath(path + updatedUser)
                .when()
                .get();

        response.then().statusCode(200);
        System.out.println("Retrieved updated User JSON: " + response.getBody().asString());
        response.then().assertThat().body("username", equalTo(updatedUser));

    }

    @When("I send a DELETE request to {string} with the username {string}")
    public void i_send_a_delete_request_to_with_the_username(String path, String userName) {
        userName = String.valueOf(updatedUser);

        response = request
                .basePath(path+userName)
                .when()
                .delete();
    }
    @Then("I should not be able to retrieve the deleted user record from {string}")
    public void i_should_not_be_able_to_retrieve_the_deleted_user_record_from(String path) {
       response = request
                .basePath(path + updatedUser)
                .when()
                .get();

        response.then().statusCode(404);
        System.out.println("Retrieved deleted User JSON: " + response.getBody().asString());
    }

}
