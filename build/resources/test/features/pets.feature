@Pets
Feature: Pets API scenarios

  Scenario Outline: retrieve pets according to their status
     Given I set the API URI to "<uri>"
     When I send a GET request to "<path>" with the status "<status>"
     Then I should receive a list of pets with status "<status>"
     And the response code should be "<code>"

     Examples:
        |uri                    |path                     |status     |code|
        |http://localhost:8080  |/api/v3/pet/findByStatus |available  |200 |
        |http://localhost:8080  |/api/v3/pet/findByStatus |pending    |200 |
        |http://localhost:8080  |/api/v3/pet/findByStatus |sold       |200 |

  Scenario Outline: retrieve pets according to their ID
    Given I set the API URI to "<uri>"
    When I send a GET request to "<path>" with the ID "<id>"
    Then I should receive a pet with their ID "<id>"
    And the response code should be "<code>"

    Examples:
      |uri                    |path         |id   |code|
      |http://localhost:8080  |/api/v3/pet/ |7    |200 |
      |http://localhost:8080  |/api/v3/pet/ |4    |200 |
      |http://localhost:8080  |/api/v3/pet/ |236  |404 |

  Scenario Outline: add a new pet to the store and retrieve it
    Given I set the API URI to "<uri>"
    When I send a POST request to "<path>" with data from "src/test/resources/data/newPet.json"
    Then the response code should be "<code>"
    And be able to get the new pet record from "<path>"

    Examples:
      |uri                    |path         |code |
      |http://localhost:8080  |/api/v3/pet/ | 200 |

  Scenario Outline: update pet info and retrieve it
    Given I set the API URI to "<uri>"
    When I send a PUT request to "<path>" with data from "src/test/resources/data/petUpdate.json"
    Then the response code should be "<code>"
    And be able to get the pet updated record from "<path>"

    Examples:
    |uri                    |path         |code |
    |http://localhost:8080  |/api/v3/pet/ |200  |

  Scenario Outline: delete an existing pet by ID
    Given I set the API URI to "<uri>"
    When I send a DELETE request to "<path>" with the ID "id"
    Then I should receive a response with status code "<code>"
    And I should not be able to retrieve the deleted pet record from "<path>"

    Examples:
    |uri                    |path         |code |
    |http://localhost:8080  |/api/v3/pet/ |200  |