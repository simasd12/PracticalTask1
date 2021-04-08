Feature: Checking some site functionality
  As a user
  I want to test some site functionality
  So that I can be sure that this functionality works correctly

  Scenario Outline: Purchase of products
    Given User opens '<homePage>' page
    When User enters the '<value>' in the search field
    And User clicks on the <number>th product on the page
    And User checks the presence of the '<value>' in the product name
    Then User click the buy product button
    And User checks that the shopping cart window has appeared

    Examples:
      | homePage                | value                         | number |
      | https://rozetka.com.ua/ | Apple iPhone 12 Pro Max 256GB | 1      |

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