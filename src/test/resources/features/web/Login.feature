Feature: User Login

  @WEB @BDD
  Scenario: User logs in to the ReportRortal
    Given User opens the login page
    When User enters valid credentials
    And Clicks the login button
    Then User should see the dashboard page
