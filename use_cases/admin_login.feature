Feature: login

  Scenario: Admin can login
    Given that Admin is not logged in
    When the enterd password is "admin123"
    Then the admin logged in succssfuly

  Scenario: Admin has the wrong password
    Given that Admin is not logged in
    When the enterd password is "afdfzvxzds"
    Then the admin login fails
