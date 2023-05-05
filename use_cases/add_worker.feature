@add-worker
Feature: Adding worker

  Scenario: add a new worker
    Given I have a worker with name "John",address "Nablus" , phone 597167504
    When the admin add the worker to the database
    Then the worker should be saved in the database with the correct information