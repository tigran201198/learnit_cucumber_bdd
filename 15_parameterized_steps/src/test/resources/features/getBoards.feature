Feature: Get Boards
  As a Trello API user
  I want to access all my boards
  So that I want to call a single endpoint that will return all my boards

  Scenario: Check Get Boards
    Given a request with authorization
    And the request has 'fields' query param with value 'id,name'
    And the request has 'member' path param with value 'learnpostman'
    When the request is sent to getBoards endpoint
    Then the response status code is 200
    And the response matches 'get_boards.json' schema

  Scenario: Check Get Board
    Given a request with authorization
    And the request has 'fields' query param with value 'id,name'
    And the request has 'id' path param with value '6288cc5d3ce8fc87542dff31'
    When the request is sent to getBoard endpoint
    Then the response status code is 200
    And body value by path 'name' is equal to 'New Board'
    And the response matches 'get_board.json' schema
