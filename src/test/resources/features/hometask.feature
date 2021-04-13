Feature: Checking some site functionality
  As a user
  I want to test some site functionality
  So that I can be sure that this functionality works correctly

  Background:
    Given open 'https://rozetka.com.ua/' page

  ######################################################################################################################
  Scenario: Purchase of products
    When enter 'Apple iPhone 12 Pro Max 256GB' in search field
    And click '1'st product on page
    Then 'Apple iPhone 12 Pro Max 256GB' is presence in product name
    When click buy product button
    Then cart window has appeared

  ######################################################################################################################
  Scenario: Invalid registration
    When click profile button
    And click registration button
    And enter 'Володимир' in name field
    And enter 'Порошенкович' in surname field
    And enter '0930000000' in phone field
    And enter random invalid data in email field
    And enter 'TestSuite1872' in password field
    And click green registration button
    Then error message about invalid email

  ######################################################################################################################
  Scenario Outline: Empty search
    When enter '<value>' in search field
    Then product catalog is empty

    Examples:
      | value             |
      | синхрофазотрон    |
      | зілля невидимості |
      | індульгенція      |

  ######################################################################################################################
  Scenario: Sorting by price
    When enter 'Apple iPhone 12 Pro Max 256GB' in search field
    And choose sort in descending order
    Then 'desc' sorting works correctly
    When choose sort in ascending order
    Then 'asc' sorting works correctly

  ######################################################################################################################
  Scenario: Add/Remove products comparison
    When enter 'Apple iPhone 12 Pro Max 256GB' in search field
    And click '1'st product on page
    And click add to compare button
    Then libra icon appeared in right corner
    And number near libra icon is equal to '1'
    When click compare button
    And remove product from list
    Then list is empty