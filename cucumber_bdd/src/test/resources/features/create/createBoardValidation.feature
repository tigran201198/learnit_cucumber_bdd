@sad
Feature: Create Board Validation
  As a Trello API user
  I want to create my board safely
  So that I want to create board endpoint to allow create only with valid request

  Scenario: Check Create Board With Invalid Id
    Given a request with authorization
    And the request has body params:
      | name |  |
    When the 'POST' request is sent to 'CREATE_A_BOARD' endpoint
    Then the response status code is 400
    And body value has the following values by paths:
      | path    | expected_value         |
      | message | invalid value for name |

  Scenario Outline: Check Create Board with Invalid Auth
    Given a request without authorization
    And the request has query params:
      | key   | <key>   |
      | token | <token> |
    And the request has body params:
      | name | new board |
    And the request has headers:
      | Content-Type | application/json |
    When the 'POST' request is sent to 'CREATE_A_BOARD' endpoint
    Then the response status code is 401
    And the response body is equal to '<error_message>'
    Examples:
      | key              | token              | error_message                     |
      | empty_value      | empty_value        | invalid key                       |
      | current_user_key | empty_value        | unauthorized permission requested |
      | empty_value      | current_user_token | invalid key                       |
