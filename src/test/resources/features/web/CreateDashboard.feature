Feature: Dashboard Management

  @WEB @BDD
  Scenario: Create a new dashboard via WEB
    Given User opens the login page
    When User enters valid credentials
    And Clicks the login button
    Then User should see the dashboard page
    When User creates a new dashboard with a random name on Dashboard page
    Then the dashboard is created successfully on Dashboard page
