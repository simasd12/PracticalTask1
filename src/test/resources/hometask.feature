Feature: Checking some site functionality
  As a user
  I want to test some site functionality
  So that I can be sure that this functionality works correctly

  Scenario Outline: Purchase of products
    Given User opens '<homePage>' page
    When User enters the '<value>' in the search field
    And User clicks on the <number>th product on the page
    And User checks the presence of the '<value>' in the product name
    Then User clicks the buy product button
    And User checks that the shopping cart window has appeared

    Examples:
      | homePage                | value                         | number |
      | https://rozetka.com.ua/ | Apple iPhone 12 Pro Max 256GB | 1      |

  Scenario Outline: Invalid registration
    Given User opens '<homePage>' page
    And User clicks profile button
    When User clicks registration button
    And User enters the '<name>' in the name field
    And User enters the '<surname>' in the surname field
    And User enters the '<phone>' in the phone field
    And And User enters random invalid data in the email field
    And User enters the '<password>' in the password field
    Then User clicks green registration button
    And User checks error message about invalid email

    Examples:
      | homePage                | name      | surname      | phone      | password      |
      | https://rozetka.com.ua/ | Володимир | Порошенкович | 0930000000 | TestSuite1872 |

  Scenario Outline: Empty search
    Given User opens '<homePage>' page
    When User enters the '<value>' in the search field
    Then User checks that the product catalog is empty

    Examples:
      | homePage                | value             |
      | https://rozetka.com.ua/ | синхрофазотрон    |
      | https://rozetka.com.ua/ | зілля невидимості |
      | https://rozetka.com.ua/ | індульгенція      |

  Scenario Outline: Sorting by price
    Given User opens '<homePage>' page
    When User enters the '<value>' in the search field
    And User choose sort in descending order
    And User checks that '<DescSortType>' sorting works correctly
    Then User choose sort in ascending order
    And User checks that '<AscSortType>' sorting works correctly

    Examples:
      | homePage                | value                         | DescSortType | AscSortType |
      | https://rozetka.com.ua/ | Apple iPhone 12 Pro Max 256GB | desc         | asc         |