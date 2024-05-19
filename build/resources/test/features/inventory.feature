@Inventory
Feature: Inventory API scenarios

  Scenario Outline: retrieve all the inventory
    Given I set the API URI to "<uri>"
    When I send a GET request to "<path>" to obtain the inventory
    Then the response code must be "<code>"

    Examples:
      |uri                    |path                     |code|
      |http://localhost:8080  |/api/v3/store/inventory  |200 |

  Scenario Outline: find purchase order by ID
    Given I set the API URI to "<uri>"
    When I send a GET request to "<path>" to obtain the purchase order by "<id>"
    Then the response code must be "<code>"

    Examples:
      |uri                    |path                 |id |code|
      |http://localhost:8080  |api/v3/store/order/  |1 |200 |
      |http://localhost:8080  |api/v3/store/order/  |2  |200 |
      |http://localhost:8080  |api/v3/store/order/  |5  |404 |

  Scenario Outline: place a new order in the store
    Given I set the API URI to "<uri>"
    When I send a POST request to "<path>" with a new order in "src/test/resources/data/newOrder.json"
    Then the response code is "<code>"

    Examples:
      |uri                    |path                 |code |
      |http://localhost:8080  |/api/v3/store/order  | 200 |

  Scenario Outline: delete purchase order by ID
    Given I set the API URI to "<uri>"
    When I send a DELETE request to "<path>" to remove a purchase order by "<id>"
    Then the response code must be "<code>"

    Examples:
      |uri                    |path                 |id |code|
      |http://localhost:8080  |api/v3/store/order/  |10 |200 |
      |http://localhost:8080  |api/v3/store/order/  |5  |200 |