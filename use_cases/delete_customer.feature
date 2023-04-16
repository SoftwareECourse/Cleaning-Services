@delete-customer
Feature: delete a customer from the system

  Scenario: Admin can delete a customer
    Given I have a customer with exist id and id equal to 2020
    When the admin enter the delete command  
    Then the customer with id 2020 should be deleted from the system