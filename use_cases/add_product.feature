@add-product
Feature: Adding product

  Scenario: create a new customer and then add product for this customer
    Given I have a product with name "Nylon" and size 12 special treatment "false"
    And I have a customer with name "John",address "Nablus" , phone 597167504 and email "ibra444hem@gmail.com"
    When the admin add the customer to the database
    And the admin add the product to the database for the last added customer
    Then the customer should be saved in the database with the correct information
    And the product should be saved in the database with the correct information with cost 120.0
    And if there is an available worker, the product state should be "In Treatment" and the worker should take the product to work on
    But if no, the state of the product should be "Waiting"
    And a new invoice will be created for the new customer without any discount

  Scenario Outline: add a product for exist customer and exist invoice
    Given I have a product with name <type> and size <size> special treatment "true"
    When the admin add the product to the database for the last added customer
    Then the product should be saved in the database with the correct information with cost <productCost>
    And if there is an available worker, the product state should be "In Treatment" and the worker should take the product to work on
    But if no, the state of the product should be "Waiting"
    And the old invoice should has a cost equal to <invoiceCost> and discounted cost equal to <discountedCost> and discount rate <discountRate>

    Examples: 
      | size | type      | productCost | invoiceCost | discountedCost | discountRate |
      |   19 | "Wool"    |       190.0 |       310.0 |          294.5 |         0.05 |
      |   12 | "Nylon"   |       120.0 |       430.0 |          387.0 |          0.1 |
      |   18 | "Cotton"  |       180.0 |       610.0 |          549.0 |          0.1 |
      |   18 | "Leather" |       180.0 |       790.0 |          711.0 |          0.1 |
      |    6 | "Nylon"   |        60.0 |       850.0 |          680.0 |          0.2 |
