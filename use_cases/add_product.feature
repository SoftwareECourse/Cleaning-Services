@add-product
Feature: Adding product

  #Scenario: add a new product with incorrect customer id
    #Given I have a product with name "John" special treatment "true" for a customer with id 123
    #When the admin add the product to the database
    #Then the product should not be saved in the database

  Scenario: add a new product with correct customer id
    Given I have a product with name "John" special treatment "true" for a customer with id 2002
    When the admin add the product to the database
    Then the product should be saved in the database with the correct information
