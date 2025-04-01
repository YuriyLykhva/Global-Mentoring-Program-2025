Feature: Dashboard Management

  @API @BDD
  Scenario Outline: Delete a new dashboard via API
    Given User has API access with a valid token
    When User creates a new dashboard with a random name
    And User deletes the dashboard
    Then the dashboard is deleted successfully <runNo> times

    Examples:
      | runNo |
      | 1     |
      | 2     |
      | 3     |