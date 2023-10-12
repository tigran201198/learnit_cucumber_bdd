Feature: Two numbers addition
  As a user of calculator
  I want to add and see the results of two numbers addition
  So that I want to make sure that the calculations are correct

  Scenario: Check add two numbers
    Given user has number one as 10
    And user has number two as 20
    When user adds number one and number two
    Then the result is 30
