Feature: Petstore v3

Background:
    * def baseUrl = karate.properties["baseUrl"]
    * url baseUrl
    * eval scOptions.setDestUrl(baseUrl)

Scenario: Basic Test
    * eval scOptions.setPathPattern("/pet/{petId}")
    Given path "pet", 2
    When method GET
    Then status 200 
