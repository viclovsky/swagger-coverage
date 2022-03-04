Feature: Petstore v3

Background:
    * url "http://petstore3.swagger.io/api/v3"
    * eval scOptions.setDestUrl("https://petstore3.swagger.io")

Scenario: Basic Test
    * eval scOptions.setPathPattern("/api/v3/pet/{petId}")
    Given path "pet", 2
    When method GET
    Then status 200 
