Feature: Create Board Validation
  As a Trello API user
  I want to create my board safely
  So that I want to create board endpoint to allow create only with valid request

  Scenario: Check Create Board With Invalid Name
    Given a request with authorization
    And the request has body params:
      | name |  |
    When the 'POST' request is sent to '/1/boards' endpoint
    Then the response status code is 400
    And body value has the following values by paths:
      | path    | expected_value         |
      | message | invalid value for name |
      | error   | ERROR                  |

  Scenario Outline: Check Create Board with Invalid Auth
    Given a request without authorization
    And the request has body params:
      | name | New Board |
    And the request has headers:
      | Content-Type | application/json |
    And the request has query params:
      | key   | <key>   |
      | token | <token> |
    When the 'POST' request is sent to '/1/boards' endpoint
    Then the response status code is 401
    And the response body is equal to '<error_message>'
    Examples:
      | key                              | token                                                            | error_message                     |
      |                                  |                                                                  | invalid key                       |
      | fb04999a731923c2e3137153b1ad5de0 |                                                                  | unauthorized permission requested |
      |                                  | b73120fb537fceb444050a2a4c08e2f96f47389931bd80253d2440708f2a57e1 | invalid key                       |
