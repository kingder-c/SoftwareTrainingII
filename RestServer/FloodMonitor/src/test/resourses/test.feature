Feature: Mernagria

  Scenario Outline: A floormonitor
    Given  A FloodMonitor
    When the number <id> is id
    Then I should be told the correct answer is "<result>"

  Examples:
  | id     | result   |
  | 1      | 11        |
