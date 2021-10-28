Feature: Customers


    Background: below are the common steps for each scenario 
    Given User Launch Chrome Browser
    When User opens URL "http://admin-demo.nopcommerce.com/login"
    And User enters Email as "admin@yourstore.com" and Password as "admin"
    And Click on Login
    Then User can view Dashboard
    
@sanity
Scenario: Add a new Customer
    When User click on Customers Menu
    And Click on customers Menu Item
    And Click on Add new button
    Then User can view add new cutomer page
    When User enter customer info
    And click on Save button
    Then User can view Confirmation message "The new customer has been added successfully"
    And  close browser

@regression    
Scenario: Search Customer by EMailID
    When User click on Customers Menu
    And Click on customers Menu Item
    And Enter Customer Email
    When Click on Search Button
    Then User should found Email in the Search table
    And close browser
 
@regression 
Scenario: Search Customer by Name
    When User click on Customers Menu
    And Click on customers Menu Item
    And Enter Customer FirstName
    And Enrer Customer LastName
    When Click on Search Button
    Then User should found Name in the Search table
    And close browser   
 
       