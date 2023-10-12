Feature: Delete Board Validation
  As a Trello API user
  I want to delete my board safely
  So that I want to delete board endpoint to allow delete only with valid request

  Scenario Outline: Check Delete Board With Invalid Id
    Given a request with authorization
    And the request has path params:
      | name | value |
      | id   | <id>  |
    When the 'DELETE' request is sent to 'DELETE_A_BOARD' endpoint
    Then the response status code is <status_code>
    And the response body is equal to '<error_message>'
    Examples:
      | id                       | status_code | error_message                         |
      | invalid                  | 400         | invalid id                            |
      | 60d847d9aad2437cb984f8e1 | 404         | The requested resource was not found. |

  Scenario Outline: Check Delete Board with Invalid Auth
    Given a request without authorization
    And the request has query params:
      | key   | <key>   |
      | token | <token> |
    And the request has path params:
      | name | value                    |
      | id   | 6288cc5d3ce8fc87542dff31 |
    When the 'DELETE' request is sent to 'DELETE_A_BOARD' endpoint
    Then the response status code is 401
    And the response body is equal to '<error_message>'
    Examples:
      | key                              | token                                                            | error_message                     |
      |                                  |                                                                  | invalid key                       |
      | fb04999a731923c2e3137153b1ad5de0 |                                                                  | unauthorized permission requested |
      |                                  | b73120fb537fceb444050a2a4c08e2f96f47389931bd80253d2440708f2a57e1 | invalid key                       |
      | 8b32218e6887516d17c84253faf967b6 | 492343b8106e7df3ebb7f01e219cbf32827c852a5f9e2b8f9ca296b1cc604955 | invalid token                     |
