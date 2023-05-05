@logout
Feature: logout

  Scenario: Admin can logout
    Given that admin is not loged out
    When the admin enter the logout command
    Then the user will log out from the system successfully
