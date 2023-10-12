Feature: Get Boards Validation
  As a Trello API user
  I want to have my board protected
  So that I want to call a single endpoint that will return my board only for me

  Scenario Outline: Check Get Board with Invalid Id
    Given a request with authorization
    And the request has path params:
      | name | value      |
      | id   | <id_value> |
    When the 'GET' request is sent to 'GET_A_BOARD' endpoint
    Then the response status code is <status_code>
    And the response body is equal to '<error_message>'
    Examples:
      | id_value                 | status_code | error_message                         |
      | invalid                  | 400         | invalid id                            |
      | 60d847d9aad2437cb984f8e1 | 404         | The requested resource was not found. |

  Scenario Outline: Check Get Board with Invalid Auth
    Given a request without authorization
    And the request has query params:
      | key   | <key>   |
      | token | <token> |
    And the request has path params:
      | name | value                    |
      | id   | 6288cc5d3ce8fc87542dff31 |
    When the 'GET' request is sent to 'GET_A_BOARD' endpoint
    Then the response status code is 401
    And the response body is equal to '<error_message>'
    Examples:
      | key              | token              | error_message                     |
      | empty_value      | empty_value        | unauthorized permission requested |
      | current_user_key | empty_value        | unauthorized permission requested |
      | empty_value      | current_user_token | invalid app key                   |
      | another_user_key | another_user_token | invalid token                     |