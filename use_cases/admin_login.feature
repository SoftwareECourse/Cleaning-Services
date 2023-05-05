@login
Feature: login

  Scenario: Admin can login
    Given that Admin is not logged in
    When the enterd password is "ibraheem123@" and the username is "ibra"
    Then the admin logged in succssfuly

  Scenario: Admin has the wrong password
    Given that Admin is not logged in
    When the enterd password is "afdfzvxzds" and the username is "ibra"
    Then the admin login fails

