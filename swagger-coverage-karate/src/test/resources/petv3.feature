Feature: Petstore v3

Background:
    * url "http://petstore3.swagger.io/api/v3"

Scenario: Basic Test
    Given path "pet", 2
    When method GET
    Then status 200 
