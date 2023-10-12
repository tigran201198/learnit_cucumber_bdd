Feature: Update Board
  As a Trello API user
  I want to update my board
  So that I want to call a single endpoint that will update my board

  Scenario: Check Update Board
    #Update board
    Given a request with authorization
    And the request has 'name' body param with value 'Updated Name'
    And the request has 'Content-Type' header with value 'application/json'
    And the request has 'id' path param with value '60d84769c4ce7a09f9140220'
    When the 'PUT' request is sent to '/1/boards/{id}' endpoint
    Then the response status code is 200
    And body value by path 'name' is equal to 'Updated Name'
    #Check board name updated
    Given a request with authorization
    And the request has 'fields' query param with value 'id,name'
    And the request has 'id' path param with value '60d84769c4ce7a09f9140220'
    When the 'GET' request is sent to '/1/boards/{id}' endpoint
    Then the response status code is 200
    And body value by path 'name' is equal to 'Updated Name'
