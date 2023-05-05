# Note : This feature should be executed after add_product.feature
@complete-invoice
Feature: complete invoice

  Scenario: 
    When the admin run the complete invoice command for the last added invoice
    Then the invoice will not be complete and no email sent to the customer

  Scenario: 
    Given that the last invoice products all are complete
    When the admin run the complete invoice command for the last added invoice
    Then the invoice will be complete and email sent to the customer
