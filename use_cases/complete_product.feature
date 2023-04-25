# Note : This feature should be executed after add_product.feature
@complete_product
Feature: setting the status of a product as complete

  Scenario: create a new product and set its status to compelte
    Given I have a product with name "Cotton" and size 6 special treatment "false"
    When the admin add the product to the database for the last added customer
    And The admin run the complete product command
    Then the product should be saved in the database with the correct information with cost 60.0
    And The status of the product will be "Complete"
    But if the product is in the "Waiting" state it should still on it
