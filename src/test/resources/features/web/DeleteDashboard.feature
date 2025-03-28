Feature: Dashboard Management

  @WEB @BDD @DeleteDashboard
  Scenario Outline: Delete a dashboard via WEB
    Given User opens the login page
    When User enters valid credentials
    And Clicks the login button
    Then User should see the dashboard page
    When User creates new dashboard with "<dashboardNamePrefix>" on Dashboard page
    Then the dashboard is created successfully on Dashboard page
    When User deletes the dashboard on Dashboard page
    Then the dashboard is deleted successfully on Dashboard page

    Examples:
      | dashboardNamePrefix |
      | 1A                  |
      | 2B                  |
      | 3C                  |