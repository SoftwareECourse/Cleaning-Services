@delete-worker
Feature: delete a worker from the system

  Scenario: Admin can delete a worker
    Given I have a worker with exist id and id equal to 5
    When the admin enter the worker delete command  
    Then the worker with id 5 should be deleted from the system