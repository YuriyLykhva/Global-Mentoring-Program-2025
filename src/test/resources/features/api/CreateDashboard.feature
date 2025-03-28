Feature: Dashboard Management

  @API
  Scenario Outline: Create a new dashboard
    Given User has API access with a valid token
    When User creates a new dashboard with a random name
    Then the dashboard is created successfully <runNo> times

    Examples:
      | runNo |
      | 1     |
      | 2     |
      | 3     |