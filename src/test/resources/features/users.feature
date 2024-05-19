@Users
  Feature: Users API scenarios

    Scenario Outline: add a new user
      Given I set the API URI to "<uri>"
      When I send a POST request to "<path>" to create a new user "src/test/resources/data/newUser.json"
      Then I get the response code "<code>"

      Examples:
        |uri                    |path         |code |
        |http://localhost:8080  |/api/v3/user | 200 |

    Scenario Outline: logs user into the system
      Given I set the API URI to "<uri>"
      When I send a GET request to "<path>"
      Then I should receive the user session

      Examples:
        |uri                    |path               |code |
        |http://localhost:8080  |/api/v3/user/login | 200 |

    Scenario Outline: get user by user name
      Given I set the API URI to "<uri>"
      When I send a GET request to "<path>" with the user "username"
      And I get the response code "<code>"

      Examples:
        |uri                    |path          |code |
        |http://localhost:8080  |/api/v3/user/ | 200 |

    Scenario Outline: update user info and retrieve it
      Given I set the API URI to "<uri>"
      When I send a PUT request to "<path>" with updated data from "src/test/resources/data/updateUser.json"
      Then I get the response code "<code>"
      And be able to get the user updated record from "<path>"

      Examples:
        |uri                    |path           |code |
        |http://localhost:8080  |/api/v3/user/  |200  |

    Scenario Outline: delete an existing user by username
      Given I set the API URI to "<uri>"
      When I send a DELETE request to "<path>" with the username "username"
      Then I get the response code "<code>"
      And I should not be able to retrieve the deleted user record from "<path>"

      Examples:
        |uri                    |path           |code |
        |http://localhost:8080  |/api/v3/user/  |200  |
