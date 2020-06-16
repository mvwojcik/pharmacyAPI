Feature: Client

  Scenario: Get all clients
    Given url 'https://pharmacytul.herokuapp.com/clients'
    When method GET
    Then status 200

  Scenario: Add client
    Given url 'https://pharmacytul.herokuapp.com/clients'
    And request { id: 1, firstName: 'name', lastName: 'lastName', address: 'address', prescriptions: null }
    When method POST
    Then status 201

  Scenario: Get one client using id
    Given url 'https://pharmacytul.herokuapp.com/clients/1'
    When method GET
    Then status 200

  Scenario: Delete client using id
    Given url 'https://pharmacytul.herokuapp.com/clients/1'
    When method DELETE
    Then status 200
