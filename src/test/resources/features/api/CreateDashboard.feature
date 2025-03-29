Feature: Dashboard Management

  @API @BDD
  Scenario: Create a new dashboard via API
    Given User has API access with a valid token
    When User creates a new dashboard with a random name
    Then the dashboard is created successfully
