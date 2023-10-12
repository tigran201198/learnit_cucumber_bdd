@happy
Feature: Create Boards
  As a Trello API user
  I want to create my board
  So that I want to call a single endpoint that will create my board

  @deleteBoard
  Scenario: Check Create Board
    #Create a board
    Given a request with authorization
    And the request has body params:
      | name | Created New Board |
    And the request has headers:
      | Content-Type | application/json |
    When the 'POST' request is sent to 'CREATE_A_BOARD' endpoint
    Then the response status code is 200
    And body value has the following values by paths:
      | path | expected_value    |
      | name | Created New Board |
    And the board ID from the response is remembered
    #Check board created
    Given a request with authorization
    And the request has query params:
      | fields | id,name |
    And the request has path params:
      | name   | value        |
      | member | learnpostman |
    When the 'GET' request is sent to 'GET_ALL_BOARDS' endpoint
    Then the response status code is 200
    And the response body has any item by path:
      | id | created_board_id |