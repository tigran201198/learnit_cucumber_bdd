Feature: Get Boards
  As a Trello API user
  I want to access all my boards
  So that I want to call a single endpoint that will return all my boards

  Scenario: Check Get Boards
    Given a request with authorization
    And the request has fields query param
    And the request has member path param
    When the request is sent to getBoards endpoint
    Then the response status code is 200
    And the getBoards response matches get_boards.json schema

  Scenario: Check Get Board
    Given a request with authorization
    And the request has fields query param
    And the request has ID path param
    When the request is sent to getBoard endpoint
    Then the response status code is 200
    And body value by path name is equal to New Board
    And the getBoard response matches get_board.json schema
