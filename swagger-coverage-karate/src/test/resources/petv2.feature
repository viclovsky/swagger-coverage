Feature: Petstore v2

Background:
    * url "http://petstore.swagger.io/v2"

Scenario: Basic Test
    Given path "pet", 1
    When method GET
    Then status 200 
