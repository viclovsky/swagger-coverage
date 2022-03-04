Feature: Petstore v2

Background:
    * url "http://petstore.swagger.io/v2"
    * eval scOptions.setDestUrl("https://petstore.swagger.io")

Scenario: Basic Test
    * eval scOptions.setPathPattern("/v2/pet/{id}")
    Given path "pet", 1
    When method GET
    Then status 200