@add-customer
Feature: Adding customer

  Scenario: add a new customer
    Given I have a customer with name "John",address "Nablus" , phone 597167504 and email "john.doe@example.com"
    When the admin add the customer to the database
    Then the customer should be saved in the database with the correct information