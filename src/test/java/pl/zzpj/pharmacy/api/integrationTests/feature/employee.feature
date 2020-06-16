Feature: Employee

  Scenario: Get All Employees
    Given url 'https://pharmacytul.herokuapp.com/employees'
    When method GET
    Then status 200

#  Scenario: Create employee
#    Given url 'http://localhost:8080/employees'
#    And request { id: 1, firstName: 'name', lastName: 'lastName', login: 'login', password: "password"}
#    When method POST
#    Then status 200
