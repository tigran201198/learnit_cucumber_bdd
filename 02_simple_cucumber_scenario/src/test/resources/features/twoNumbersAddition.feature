Feature: Two numbers addition

  Scenario: Check add two numbers
    Given user has number one as 10
    And user has number two as 20
    When user adds number one and number two
    Then the result is 30
