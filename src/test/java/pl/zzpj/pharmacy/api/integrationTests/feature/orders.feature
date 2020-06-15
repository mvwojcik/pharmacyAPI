Feature: Orders

  Scenario: Get all orders
    Given url 'https://pharmacytul.herokuapp.com/orders'
    When method GET
    Then status 200

  Scenario: Add order
    Given url 'https://pharmacytul.herokuapp.com/orders'
    And request { id: 1, client: { id: 1 }, medicineOrders: null }
    When method POST
    Then status 201

  Scenario: Get order by id
    Given url 'https://pharmacytul.herokuapp.com/orders/1'
    When method GET
    Then status 200


