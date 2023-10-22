# language en
# enconding utf-8
@Mancala
Feature: Test de feature

  @Mancala
  Scenario: Game creation success
    When Make a POST request to create a new game
    Then the status code should be 201

  @Mancala
  Scenario: Game created with id
    When Make a POST request to create a new game
    Then the game id should be returned

  @Mancala
  Scenario: Game created with 6 seeds in small pits
    When Make a POST request to create a new game
    Then all players should have 6 seeds each small pit
    And all players should have 0 seeds in big pit

  @Mancala
  Scenario: Player plays again
    Given Make a POST request to create a new game
    When the PLAYER_1 sow his small pit number 3
    And the PLAYER_2 sow his small pit number 4
    And the PLAYER_1 sow his small pit number 4
    And the PLAYER_2 sow his small pit number 1
    And the PLAYER_1 sow his small pit number 3
    Then PLAYER_1 score 11 seeds in his big pit

  @Mancala
  Scenario: Player plays again
    Given Make a POST request to create a new game
    When the PLAYER_1 sow his small pit number 1
    And PLAYER_1 score 1 seeds in his big pit
    And PLAYER_1 play again

  @Mancala
  Scenario: Player 1 score twice
    Given Make a POST request to create a new game
    When the PLAYER_1 sow his small pit number 1
    And the PLAYER_1 sow his small pit number 2
    Then PLAYER_1 score 2 seeds in his big pit

  @Mancala
  Scenario: Player 2 cannot start
    Given Make a POST request to create a new game
    When the PLAYER_2 sow his small pit number 1
    Then a client error should occur

  @Mancala
  Scenario: Player 1 cannot play twice without score
    Given Make a POST request to create a new game
    When the PLAYER_1 sow his small pit number 2
    And the PLAYER_1 sow his small pit number 3
    Then a client error should occur

  @Mancala
  Scenario: Player 2 cannot play twice without score
    Given Make a POST request to create a new game
    When the PLAYER_1 sow his small pit number 2
    And the PLAYER_2 sow his small pit number 3
    And the PLAYER_2 sow his small pit number 4
    Then a client error should occur

  @Mancala
  Scenario: Player 1 cannot sow a empty pit
    Given Make a POST request to create a new game
    When the PLAYER_1 sow his small pit number 2
    And the PLAYER_2 sow his small pit number 1
    And the PLAYER_1 sow his small pit number 2
    Then a client error should occur

  @Mancala
  Scenario: Player 2 cannot sow a empty pit
    Given Make a POST request to create a new game
    When the PLAYER_1 sow his small pit number 2
    And the PLAYER_2 sow his small pit number 6
    And the PLAYER_1 sow his small pit number 3
    And the PLAYER_2 sow his small pit number 6
    Then a client error should occur

  @Mancala
  Scenario: Player 2 collect 2 seeds in his big pit
    Given Make a POST request to create a new game
    When the PLAYER_1 sow his small pit number 3
    And the PLAYER_2 sow his small pit number 6
    And the PLAYER_1 sow his small pit number 4
    And the PLAYER_2 sow his small pit number 2
    Then PLAYER_2 score 2 seeds in his big pit
